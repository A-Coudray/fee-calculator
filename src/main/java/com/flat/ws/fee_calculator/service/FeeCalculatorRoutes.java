package com.flat.ws.fee_calculator.service;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;

import com.flat.ws.fee_calculator.exception.FeeCalculatorException;
import com.flat.ws.fee_calculator.model.FeeCalculatorInput;

@Controller
public class FeeCalculatorRoutes {

	private static final Logger LOGGER = LogManager.getLogger(FeeCalculatorRoutes.class.getName());

	FeeCalculatorProcessor processor = new FeeCalculatorProcessor();

	/**
	 * api to get the membership fee for a tenant
	 * 
	 * @param id
	 * @return
	 */

	@POST("/membership/fee")
	public Resp calculateMembershipFee(FeeCalculatorInput input, Req req) {

		Resp resp = req.response();

		try {
			int res = processor.calculateMembershipFee(input.getRent(), input.getRent_period(),
					input.getOrganization_unit());
			resp.code(HttpStatus.SC_OK);
			resp.result(res);
		} catch (FeeCalculatorException e) {
			if (e.getStatus() != 0 ) {
				resp.code(e.getStatus());
			}
			else {
				resp.code(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			}
			resp.result(e.getMessage());
			LOGGER.error(e.getMessage(), e);
		}
		return resp;

	}

}
