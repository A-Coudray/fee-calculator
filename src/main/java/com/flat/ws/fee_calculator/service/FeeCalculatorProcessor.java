package com.flat.ws.fee_calculator.service;

import java.util.HashMap;

import org.apache.http.HttpStatus;

import com.flat.ws.fee_calculator.config.ConfigurationManager;
import com.flat.ws.fee_calculator.config.Constants;
import com.flat.ws.fee_calculator.config.ErrorMessages;
import com.flat.ws.fee_calculator.exception.FeeCalculatorException;
import com.flat.ws.fee_calculator.model.Area;
import com.flat.ws.fee_calculator.model.Branch;
import com.flat.ws.fee_calculator.model.Client;
import com.flat.ws.fee_calculator.model.Division;
import com.flat.ws.fee_calculator.model.OrganizationUnitConfig;

public class FeeCalculatorProcessor {

	Client clientConfig = ConfigurationManager.getInstance().getClientConfig();

	HashMap<String, Branch> branches = ConfigurationManager.getInstance().getBranchesMap();
	HashMap<String, Area> areas = ConfigurationManager.getInstance().getAreasMap();
	HashMap<String, Division> divisions = ConfigurationManager.getInstance().getDivisionsMap();

	public int calculateMembershipFee(int rent, String rentPeriod, String organizationUnit) throws FeeCalculatorException {
		
		// avoid case sensitive errors
		rentPeriod = rentPeriod.toLowerCase();
		
		validateRent(rent, rentPeriod);

		// find the branch
		Branch br = branches.get(organizationUnit);

		int res = 0;

		if (null == br) {
			throw new FeeCalculatorException(ErrorMessages.INVALID_ORGANIZATION_UNIT, HttpStatus.SC_BAD_REQUEST);
		} else {
			
			/*
			 * Find the proper config by finding the correct parent
			 */
			
			OrganizationUnitConfig config = br.getConfig();
			if (null == config) {
				Area parentArea = areas.get(br.getParent().getName());
				config = parentArea.getConfig();
				if (null == config) {
					Division parentDivision = divisions.get(parentArea.getParent().getName());
					config = parentDivision.getConfig();
					if (null == config) {
						config = clientConfig.getConfig();
					}
				}
			}

			if (null == config) {
				throw new FeeCalculatorException(ErrorMessages.NO_ORGANIZATION_CONFIG_UNIT_FOUND+organizationUnit, HttpStatus.SC_INTERNAL_SERVER_ERROR);
			} else {

				/*
				 * Check for the fixed membership
				 */
				if (config.getHas_fixed_membership_fee()) {
					
					return config.getFixed_membership_fee_amount();
				} else {

					res = calculation(rent, rentPeriod);

					return validateMembershipFee(res);
				}
			}
		}

	}
	
	/*
	 * Input validation function
	 */

	private void validateRent(int rent, String rentPeriod) throws FeeCalculatorException {

		switch (rentPeriod) {

		// only week and month are accepted + min and max rent checks
		case Constants.MONTH:
			if (rent < Constants.MIN_RENT_MONTHLY) {
				throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_MIN_MONTHLY, HttpStatus.SC_BAD_REQUEST);
			}
			else if (rent > Constants.MAX_RENT_MONTHLY) {
				throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_MAX_MONTHLY, HttpStatus.SC_BAD_REQUEST);
			}
			else {
				break;
			}
			
		case Constants.WEEK:
			if (rent < Constants.MIN_RENT_WEEKLY) {
				throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_MIN_WEEKLY, HttpStatus.SC_BAD_REQUEST);
			}
			else if (rent > Constants.MAX_RENT_WEEKLY) {
				throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_MAX_WEEKLY, HttpStatus.SC_BAD_REQUEST);
			}
			else {
				break;
			}

		default:
			throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_PERIOD, HttpStatus.SC_BAD_REQUEST);

		}

	}
	
	/*
	 * The actual calculation
	 */

	private int calculation(int rent, String rentPeriod) throws FeeCalculatorException {


		switch (rentPeriod) {
		case Constants.MONTH:
			// convert monthly rent to weekly and apply VAT
			return (int) (rent * 52 / 12 * Constants.VAT) + rent;

		case Constants.WEEK:
			return (int) (rent * Constants.VAT) + rent;

			// should not happen
		default: throw new FeeCalculatorException(ErrorMessages.INVALID_RENT_PERIOD, HttpStatus.SC_BAD_REQUEST);

		}
	}

	private int validateMembershipFee(int res) {

		if (res < Constants.MIN_MEMBERSHIP_MONTHLY_PLUS_VAT) {
			return Constants.MIN_MEMBERSHIP_MONTHLY_PLUS_VAT;
		} else {

			return res;
		}

	}

}
