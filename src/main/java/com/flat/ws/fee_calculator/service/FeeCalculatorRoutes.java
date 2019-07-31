package com.flat.ws.fee_calculator.service;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;


@Controller
public class FeeCalculatorRoutes {
	
	
	FeeCalculatorProcessor processor = new FeeCalculatorProcessor ();
	
	/**
	 * api to get the membership fee for a tenant
	 * @param id
	 * @return
	 */

	
	@GET("/membership/fee/{id}")
	public int getAccount (String id) {
		
		return processor.calculateMembershipFee(id);
	}
	
	

}
