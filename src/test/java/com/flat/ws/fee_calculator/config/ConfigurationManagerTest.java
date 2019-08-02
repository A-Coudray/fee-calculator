package com.flat.ws.fee_calculator.config;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.flat.ws.fee_calculator.exception.FeeCalculatorException;


public class ConfigurationManagerTest {
	
	@Test
	public void loadValidConfig () throws FeeCalculatorException {
		
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
		
		
		configMan.loadConfiguration("src/test/resources/config/");
		
		assertEquals("The correct config was loaded", "client_a", configMan.getClientConfig().getName());
		assertTrue("The correct config was loaded", configMan.getDivisionsMap().containsKey("division_a"));
		assertTrue("The correct config was loaded", configMan.getAreasMap().containsKey("area_a"));
		assertTrue("The correct config was loaded", configMan.getAreasMap().containsKey("area_b"));
		assertTrue("The correct config was loaded", configMan.getBranchesMap().containsKey("branch_b"));
		assertTrue("The correct config was loaded", configMan.getBranchesMap().containsKey("branch_a"));
	}
	
	@Test
	public void loadInvalidConfigDuplicatedBranch () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/duplicatedBranch/");
	    });
		
	}
	
	@Test
	public void loadInvalidConfigDuplicatedArea () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/duplicatedArea/");
	    });
		
	}
	
	@Test
	public void loadInvalidConfigDuplicatedDivision () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/duplicatedDivision/");
	    });
		
	}
	
	@Test
	public void loadInvalidConfigUnassigneddDivision () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/unassignedDivision/");
	    });
		
	}
	@Test
	public void loadInvalidConfigUnassignedArea () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/unassignedArea/");
	    });
		
	}
	
	@Test
	public void loadInvalidConfigUnassignedBranch () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/unassignedBranch/");
	    });
		
	}
	
	@Test
	public void fileNotFound () throws FeeCalculatorException {
		
		ConfigurationManager configMan = ConfigurationManager.getInstance();
	    assertThrows(FeeCalculatorException.class, () -> {
	    	configMan.loadConfiguration("src/test/resources/config/123456/");
	    });
		
	}

}
