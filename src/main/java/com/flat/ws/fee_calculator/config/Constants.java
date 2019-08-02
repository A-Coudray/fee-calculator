package com.flat.ws.fee_calculator.config;

public final class Constants {
	
	
	public static final String WEEK = "week";
	
	public static final String MONTH = "month";
	
	public static final float VAT = 0.20f;
	
	public static final int MIN_RENT_WEEKLY = 2500;
	public static final int MAX_RENT_WEEKLY = 200000;
	
	public static final int MIN_RENT_MONTHLY = 11000;
	public static final int MAX_RENT_MONTHLY = 866000;
	
	public static final int MIN_MEMBERSHIP = 12000;
	
	public static final int MIN_MEMBERSHIP_MONTHLY_PLUS_VAT = 14400;
	
	
	
	
	  private Constants(){
		    //this prevents even the native class from 
		    //calling this ctor as well :
		    throw new AssertionError();
		  }

}
