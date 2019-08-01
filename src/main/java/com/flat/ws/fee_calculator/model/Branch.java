package com.flat.ws.fee_calculator.model;


public class Branch {
	
	
	@Override
	public String toString() {
		return "Branch [name=" + name + ", config=" + config + "]";
	}

	private String name;
	
	private OrganizationUnitConfig config;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OrganizationUnitConfig getConfig() {
		return config;
	}

	public void setConfig(OrganizationUnitConfig config) {
		this.config = config;
	}

	
}
