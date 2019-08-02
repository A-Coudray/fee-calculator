package com.flat.ws.fee_calculator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;

import com.flat.ws.fee_calculator.config.Constants;
import com.flat.ws.fee_calculator.model.FeeCalculatorInput;
import com.flat.ws.fee_calculator.model.FeeCalculatorOutput;


public class FeeCalculatorApiTest {

	private static Client client;

	@BeforeAll
	public static void setup() {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		clientConfig.register(JacksonJsonProvider.class);
		client = ClientBuilder.newClient(clientConfig);
		// Start the server

		String[] args = new String[2];
		
		args[0] = "--conf";
		args[1] = "src/test/resources/config/";
		
		FeeCalculatorServer.main(args);

	}
	
	
	@Test
	public void getMemberShipFeeWeekTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_a");
		input.setRent(25000);
		input.setRent_period("week");
		Response resp = sendPostRequest(input);
		
		assertEquals("Successful membership calculation", 200, resp.getStatus());
		
		FeeCalculatorOutput responseAcc = resp.readEntity(FeeCalculatorOutput.class);
		
		int expected = (int) (responseAcc.getRent()*Constants.VAT+responseAcc.getRent());
		
		assertEquals("Successful membership calculation", responseAcc.getMembershipFee(), expected);
		
	}
	
	@Test
	public void getMemberShipFeeMonthTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_a");
		input.setRent(21000);
		input.setRent_period("month");
		Response resp = sendPostRequest(input);
		
		assertEquals("Successful membership calculation", 200, resp.getStatus());
		
		FeeCalculatorOutput responseAcc = resp.readEntity(FeeCalculatorOutput.class);
		
		int expected = (int) (responseAcc.getRent()*52/12*Constants.VAT+responseAcc.getRent());
		
		assertEquals("Successful membership calculation", responseAcc.getMembershipFee(), expected);
		
	}
	
	@Test
	public void getMemberShipFeeMonthFixedAmountTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_b");
		input.setRent(21000);
		input.setRent_period("month");
		Response resp = sendPostRequest(input);
		
		assertEquals("Successful membership calculation", 200, resp.getStatus());
		
		FeeCalculatorOutput responseAcc = resp.readEntity(FeeCalculatorOutput.class);
		
		int expected = 30000;
		
		assertEquals("Successful membership calculation", responseAcc.getMembershipFee(), expected);
		
	}
	
	@Test
	public void getMemberShipFeeMonthFixedAmountFromAreaParentTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_c");
		input.setRent(21000);
		input.setRent_period("month");
		Response resp = sendPostRequest(input);
		
		assertEquals("Successful membership calculation", 200, resp.getStatus());
		
		FeeCalculatorOutput responseAcc = resp.readEntity(FeeCalculatorOutput.class);
		
		int expected = 500000;
		
		assertEquals("Successful membership calculation", responseAcc.getMembershipFee(), expected);
		
	}
	
	@Test
	public void getMemberShipFeeMonthFixedAmountFromDivisionParentTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_d");
		input.setRent(21000);
		input.setRent_period("month");
		Response resp = sendPostRequest(input);
		
		assertEquals("Successful membership calculation", 200, resp.getStatus());
		
		FeeCalculatorOutput responseAcc = resp.readEntity(FeeCalculatorOutput.class);
		
		int expected = 4500000;
		
		assertEquals("Successful membership calculation", responseAcc.getMembershipFee(), expected);
		
	}
	
	
	
    private static Response sendPostRequest(FeeCalculatorInput feeCalculatorInput) {
        return client.target("http://localhost:8080/membership/fee").request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(feeCalculatorInput));
    }

}
