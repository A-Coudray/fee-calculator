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

import com.flat.ws.fee_calculator.model.FeeCalculatorInput;


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
	public void getMemberShipFeeTest () {
		FeeCalculatorInput input = new FeeCalculatorInput();
		
		input.setOrganization_unit("branch_a");
		input.setRent(25000);
		input.setRent_period("week");
		sendPostRequest(input);
		
		
	}
	
    private static Response sendPostRequest(FeeCalculatorInput feeCalculatorInput) {
        return client.target("http://localhost:8080/membership/fee").request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(feeCalculatorInput));
    }

}
