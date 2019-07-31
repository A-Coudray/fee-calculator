package com.flat.ws.fee_calculator.model;

import javax.validation.constraints.NotNull;

public class FeeCalculatorInput {
	
	@NotNull
	private int rent;
	@NotNull
	private String rent_period ;
	@NotNull
	private String organization_unit;
	
	public int getRent() {
		return rent;
	}
	public void setRent(int rent) {
		this.rent = rent;
	}
	public String getRent_period() {
		return rent_period;
	}
	public void setRent_period(String rent_period) {
		this.rent_period = rent_period;
	}
	public String getOrganization_unit() {
		return organization_unit;
	}
	public void setOrganization_unit(String organization_unit) {
		this.organization_unit = organization_unit;
	}

}
