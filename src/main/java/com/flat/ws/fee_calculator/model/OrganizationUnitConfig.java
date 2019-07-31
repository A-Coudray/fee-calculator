package com.flat.ws.fee_calculator.model;

public class OrganizationUnitConfig {
	
	private boolean hasFixedMembershipFee;
	
	private int fixed_membership_fee_amount;

	public boolean isHasFixedMembershipFee() {
		return hasFixedMembershipFee;
	}

	public void setHasFixedMembershipFee(boolean hasFixedMembershipFee) {
		this.hasFixedMembershipFee = hasFixedMembershipFee;
	}

	public int getFixed_membership_fee_amount() {
		return fixed_membership_fee_amount;
	}

	public void setFixed_membership_fee_amount(int fixed_membership_fee_amount) {
		this.fixed_membership_fee_amount = fixed_membership_fee_amount;
	}

}
