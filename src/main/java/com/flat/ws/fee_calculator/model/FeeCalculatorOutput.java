package com.flat.ws.fee_calculator.model;


public class FeeCalculatorOutput {
	
	@Override
	public String toString() {
		return "FeeCalculatorOutput [rent=" + rent + ", rent_period=" + rent_period + ", organization_unit="
				+ organization_unit + ", membershipFee=" + membershipFee + "]";
	}
	private int rent;
	private String rent_period ;
	private String organization_unit;
	private int membershipFee;
	
	public FeeCalculatorOutput () {
		
	}
	
	public FeeCalculatorOutput (int rent, String rent_period, String organization_unit, int  membershipFee) {
		this.rent = rent;
		this.rent_period = rent_period;
		this.organization_unit = organization_unit;
		this.membershipFee = membershipFee;
	}
	
	
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
	public int getMembershipFee() {
		return membershipFee;
	}
	public void setMembershipFee(int membershipFee) {
		this.membershipFee = membershipFee;
	}

}
