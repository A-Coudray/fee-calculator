package com.flat.ws.fee_calculator.service;

import com.flat.ws.fee_calculator.config.Constants;

public class FeeCalculatorProcessor {
	
	
	
	
	public int calculateMembershipFee(int rent, String rentPeriod, String organizationUnit) {
		
		// avoid case sensitive errors
		switch(organizationUnit.toLowerCase()) {
			
			case Constants.MONTH: if (rent < Constants.MIN_RENT_MONTHLY || rent > Constants.MAX_RENT_MONTHLY) {
				// throw
			};
			case Constants.WEEK: if (rent < Constants.MIN_RENT_WEEKLY || rent > Constants.MAX_RENT_WEEKLY) {
				// throw
				
			};
			
		default: break;
			
		}
		
		
		return 1;
	}

}
