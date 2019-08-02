package com.flat.ws.fee_calculator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rapidoid.setup.App;

import com.flat.ws.fee_calculator.config.ConfigurationManager;
import com.flat.ws.fee_calculator.config.ErrorMessages;
import com.flat.ws.fee_calculator.exception.FeeCalculatorException;
import com.flat.ws.fee_calculator.service.FeeCalculatorRoutes;

/**
 * Main class to start the server
 * 
 * @author acoudray
 *
 */
public class FeeCalculatorServer {

	private static final Logger LOGGER = LogManager.getLogger(FeeCalculatorServer.class.getName());

	public static void main(String[] args) {

		Options options = new Options();
		
		String defaultConfigPath = "conf/";

		Option configPath = Option.builder().longOpt("conf").argName("conf").hasArg()
				.desc("The configuration directory").build();
		options.addOption(configPath);

		CommandLineParser parser = new DefaultParser();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("conf")) {
				defaultConfigPath = line.getOptionValue("conf");
			}
		}

		catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
		}
		ConfigurationManager configMan = ConfigurationManager.getInstance();
		try {
			configMan.loadConfiguration(defaultConfigPath);
		} catch (FeeCalculatorException e) {
			LOGGER.error(ErrorMessages.STARTUP_EXCEPTION, e);
			System.exit(1);
		}
		
		

		App.bootstrap(new String[0]);

	}

	public static void shutdown() {

		App.shutdown();

	}
}
