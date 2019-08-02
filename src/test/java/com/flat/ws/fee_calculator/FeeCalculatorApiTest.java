package com.flat.ws.fee_calculator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.junit.jupiter.api.BeforeAll;

public class FeeCalculatorApiTest {

	private static Client client;

	@BeforeAll
	public static void setup() {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);
		clientConfig.register(JacksonJsonProvider.class);
		client = ClientBuilder.newClient(clientConfig);
		// Start the server

		String[] args = new String[0];
		
		FeeCalculatorServer.main(args);

	}

}
