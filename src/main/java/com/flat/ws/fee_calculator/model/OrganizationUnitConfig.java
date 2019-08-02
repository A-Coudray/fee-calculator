package com.flat.ws.fee_calculator.model;

public class OrganizationUnitConfig {
	
	@Override
	public String toString() {
		return "OrganizationUnitConfig [hasFixedMembershipFee=" + has_fixed_membership_fee
				+ ", fixed_membership_fee_amount=" + fixed_membership_fee_amount + "]";
	}

	private boolean has_fixed_membership_fee;
	
	private int fixed_membership_fee_amount;

	public boolean getHas_fixed_membership_fee() {
		return has_fixed_membership_fee;
	}

	public void setHas_fixed_membership_fee(boolean hasFixedMembershipFee) {
		this.has_fixed_membership_fee = hasFixedMembershipFee;
	}

	public int getFixed_membership_fee_amount() {
		return fixed_membership_fee_amount;
	}

	public void setFixed_membership_fee_amount(int fixed_membership_fee_amount) {
		this.fixed_membership_fee_amount = fixed_membership_fee_amount;
	}

}
