package com.flat.ws.fee_calculator;

import org.rapidoid.setup.App;

import com.flat.ws.fee_calculator.config.ConfigurationManager;

/**
 * Main class to start the server
 * @author acoudray
 *
 */
public class FeeCaclculatorServer 
{		


    public static void main(String[] args) {
    	
    	ConfigurationManager configMan = ConfigurationManager.getInstance();
    	configMan.loadConfiguration();
    	
    	App.bootstrap(args);
    	
    	
    }
    
    public static void shutdown () {
    	
    	App.shutdown();
    	
    }
}
