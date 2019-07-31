package com.flat.ws.fee_calculator.service;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;

import com.flat.ws.fee_calculator.model.FeeCalculatorInput;




@Controller
public class FeeCalculatorRoutes {
	
	 private static final Logger LOGGER = LogManager.getLogger(FeeCalculatorRoutes.class.getName());
	
	FeeCalculatorProcessor processor = new FeeCalculatorProcessor ();
	
	/**
	 * api to get the membership fee for a tenant
	 * @param id
	 * @return
	 */

		
	@POST("/membership/fee")
	public Resp createAccount (FeeCalculatorInput input, Req req) {
		
		Resp resp = req.response();
		
		
		int res = processor.calculateMembershipFee(input.getRent(), input.getRent_period(), input.getOrganization_unit());
		if (res == 0) {
			resp.code(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			resp.result("Error while calculating the membership fee");
		}
		else {
			resp.code(HttpStatus.SC_OK);
			resp.result(res);
		}
		
		return resp;
	}
	

}

