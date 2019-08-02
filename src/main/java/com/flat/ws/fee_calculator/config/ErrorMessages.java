package com.flat.ws.fee_calculator.config;

public final class ErrorMessages {
	
	
	public static final String INVALID_RENT_MIN_WEEKLY = "Error: input rent is lower than the minimum value of "+Constants.MIN_RENT_WEEKLY+" pence";
	
	public static final String INVALID_RENT_MAX_WEEKLY = "Error: input rent is higher than the maximum value of "+Constants.MAX_RENT_WEEKLY+" pence";
	
	public static final String INVALID_RENT_MIN_MONTHLY = "Error: input rent is lower than the minimum value of "+Constants.MIN_RENT_MONTHLY +" pence";
	
	public static final String INVALID_RENT_MAX_MONTHLY = "Error: input rent is higher than the maximum value of "+Constants.MAX_RENT_MONTHLY +" pence";
	
	public static final String INVALID_ORGANIZATION_UNIT = "Error: this Organization Unit does not exist";
	
	public static final String INVALID_RENT_PERIOD = "Error: this rent period is not supported, ony \"weekly\" or \"monthly\" rent periods are supported";
	
	public static final String NO_ORGANIZATION_CONFIG_UNIT_FOUND = "Error: not Organization Unit Config could be found for: ";
	
	public static final String UNCONFIGURED_ORGANIZATION_UNIT = "Error: the following Organization Unit has not been configured ";

	public static final String INVALID_AREA_CONFIG_EXCEPTION = "Error: a branch can only have one assigned area. Duplicated branch declaration: ";
	
	public static final String INVALID_DIVISION_CONFIG_EXCEPTION = "Error: an area can only have one assigned division. Duplicated area declaration: ";

	public static final String UNEXPECTED_EXCEPTION_IN_CLIENT_CONFIG = "Unexpected Exception in the client configuration";
	
	
	public static final String STARTUP_EXCEPTION = "Exception while processing the configuration files, shutting down the fee calculator app";
	
	
	  private ErrorMessages(){
		    //this prevents even the native class from 
		    //calling this ctor as well :
		    throw new AssertionError();
		  }

}
