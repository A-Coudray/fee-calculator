package com.flat.ws.fee_calculator.config;


import org.junit.jupiter.api.Test;

import com.flat.ws.fee_calculator.exception.FeeCalculatorException;


public class ConfigurationManagerTest {
	
	@Test
	public void insertDataTest () throws FeeCalculatorException {
		
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
		
		
		configMan.loadConfiguration("src/test/resources/config/");
		
		
	}

}
