package com.flat.ws.fee_calculator.service;

import java.util.HashMap;

import com.flat.ws.fee_calculator.config.ConfigurationManager;
import com.flat.ws.fee_calculator.config.Constants;
import com.flat.ws.fee_calculator.model.Area;
import com.flat.ws.fee_calculator.model.Branch;
import com.flat.ws.fee_calculator.model.Client;
import com.flat.ws.fee_calculator.model.Division;
import com.flat.ws.fee_calculator.model.OrganizationUnitConfig;

public class FeeCalculatorProcessor {
	
	
	Client clientConfig = ConfigurationManager.getInstance().getClientConfig();
	
	HashMap <String, Branch> branches = ConfigurationManager.getInstance().getBranchesMap();
	HashMap <String, Area> areas = ConfigurationManager.getInstance().getAreasMap();
	HashMap <String, Division> divisions = ConfigurationManager.getInstance().getDivisionsMap();
	
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
		
		//find the branch
		Branch br = branches.get(organizationUnit);
		
		
		int res = 0;
		
		if (null == br) {
		//throw	
		}
		else {
			OrganizationUnitConfig config = br.getConfig();
			if(null == config) {
				Area parentArea = areas.get(br.getParent().getName());
				config = parentArea.getConfig();
				if(null == config) {
					Division parentDivision = divisions.get(parentArea.getParent().getName());
					config = parentDivision.getConfig();
					if (null == config) {
						config = clientConfig.getConfig();
					}
	
				}
				
				
			}
			
			if (null == config) {
				// thow
			}
			else {
				// calculate
			}
			
		}
		
		
		
		
		return res;
	}

}
