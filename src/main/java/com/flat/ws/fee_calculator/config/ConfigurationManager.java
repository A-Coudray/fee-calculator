package com.flat.ws.fee_calculator.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.flat.ws.fee_calculator.exception.FeeCalculatorException;
import com.flat.ws.fee_calculator.model.Area;
import com.flat.ws.fee_calculator.model.Branch;
import com.flat.ws.fee_calculator.model.Client;
import com.flat.ws.fee_calculator.model.Division;
import com.google.gson.Gson;

public class ConfigurationManager {

	private static final String CLIENTS_CONFIG = "clients-config.json";
	private static final String DIVISIONS_CONFIG = "divisions-config.json";
	private static final String AREAS_CONFIG = "areas-config.json";
	private static final String BRANCHES_CONFIG = "branches-config.json";

	private Client clientConfig;

	private HashMap<String, Branch> branchesMap = new HashMap<>();

	private HashMap<String, Area> areasMap = new HashMap<>();;

	private HashMap<String, Division> divisionsMap = new HashMap<>();;

	public Client getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(Client clientConfig) {
		this.clientConfig = clientConfig;
	}

	/**
	 * The Class LoaderConfigurationManager to create a singleton
	 */
	private static class LoaderConfigurationManager {

		/** The instance. */
		private static ConfigurationManager instance = new ConfigurationManager();

		/**
		 * Instantiates a new loader ConfigurationManager.
		 */
		private LoaderConfigurationManager() {
		}
	}

	/**
	 * singleton public method - method to call to create ConfigurationManager.
	 * 
	 * @return single instance of ConfigurationManager
	 */
	public static ConfigurationManager getInstance() {
		return LoaderConfigurationManager.instance;
	}

	public void loadConfiguration(String path) throws FeeCalculatorException {

		BufferedReader bufferedReaderClient = null;
		BufferedReader bufferedReaderDivision = null;
		BufferedReader bufferedReaderArea = null;
		BufferedReader bufferedReaderBranch = null;
		try {
			bufferedReaderClient = new BufferedReader(new FileReader(path + CLIENTS_CONFIG));
			bufferedReaderDivision = new BufferedReader(new FileReader(path + DIVISIONS_CONFIG));
			bufferedReaderArea = new BufferedReader(new FileReader(path + AREAS_CONFIG));
			bufferedReaderBranch = new BufferedReader(new FileReader(path + BRANCHES_CONFIG));
		} catch (FileNotFoundException e) {
			throw new FeeCalculatorException(e.getMessage(), e);
		}
		/*
		 * Parse the json files containing the config
		 */
		
		Gson gson = new Gson();
		Client jsonClient = gson.fromJson(bufferedReaderClient, Client.class);
		Division[] jsonDiv = gson.fromJson(bufferedReaderDivision, Division[].class);
		Area[] jsonArea = gson.fromJson(bufferedReaderArea, Area[].class);
		Branch[] jsonBranch = gson.fromJson(bufferedReaderBranch, Branch[].class);

		/*
		 * Initialize the hashmaps that will contain the config
		 */
		branchesMap = new HashMap<>();
		areasMap = new HashMap<>();
		divisionsMap = new HashMap<>();

		ArrayList<String> assignedOrganizationUnit = new ArrayList<>();

		/*
		 * Populate the hashmaps
		 */
		
		// for each area configured
		for (Area area : jsonArea) {
			// we go through every configured branch
			for (String br : area.getBranches()) {
				// and add them to this assignedOrganizationUnit (that means a parent has them assigned to)
				assignedOrganizationUnit.add(br);
				// then we check the actual configured branches
				for (Branch curBr : jsonBranch) {
					// if we have a match, we add the branch to the map containing all branches
					if (null != br && br.equalsIgnoreCase(curBr.getName())) {
						// we also set the branch's parent to easily find it after
						curBr.setParent(area);
						// we make sure that the branch has not been assigned twice
						if (null != branchesMap.putIfAbsent(br, curBr)) {
							throw new FeeCalculatorException(ErrorMessages.INVALID_AREA_CONFIG_EXCEPTION + curBr);
						}
					}
				}
			}
		}
		
		/*
		 * Validate that the assigned units are actually configured
		 */
		validateAssignedOrganizationUnits(assignedOrganizationUnit, branchesMap);
		/*
		 * Validate that the configured units are actually assigned
		 */
		validateIfBranchesAreAssigned(jsonArea, jsonBranch);

		assignedOrganizationUnit = new ArrayList<>();

		for (Division div : jsonDiv) {
			for (String ar : div.getAreas()) {
				assignedOrganizationUnit.add(ar);
				for (Area curAr : jsonArea) {
					if (null != ar && ar.equalsIgnoreCase(curAr.getName())) {
						curAr.setParent(div);
						if (null != areasMap.putIfAbsent(ar, curAr)) {
							throw new FeeCalculatorException(ErrorMessages.INVALID_DIVISION_CONFIG_EXCEPTION + curAr);
						}
					}
				}
			}
		}

		validateAssignedOrganizationUnits(assignedOrganizationUnit, areasMap);
		validateIfAreasAreAssigned(jsonDiv, jsonArea);

		assignedOrganizationUnit = new ArrayList<>();

		for (String div : jsonClient.getDivisions()) {
			assignedOrganizationUnit.add(div);
			for (Division curDiv : jsonDiv) {
				if (null != div && div.equalsIgnoreCase(curDiv.getName())) {
					curDiv.setParent(jsonClient);
					if (null != divisionsMap.putIfAbsent(div, curDiv)) {
						throw new FeeCalculatorException(ErrorMessages.UNEXPECTED_EXCEPTION_IN_CLIENT_CONFIG);
					}
				}
			}
		}
		validateAssignedOrganizationUnits(assignedOrganizationUnit, divisionsMap);
		validateIfDivisionsAreAssigned(jsonClient.getDivisions(), jsonDiv);
		this.setClientConfig(jsonClient);

	}

	/*
	 * This method is to ensure that any configured children actually exists in the
	 * configuration
	 */
	private void validateAssignedOrganizationUnits(ArrayList<String> assignedUnits, HashMap<String, ?> map)
			throws FeeCalculatorException {
		for (String curUnit : assignedUnits) {
			if (!map.containsKey(curUnit)) {
				throw new FeeCalculatorException(ErrorMessages.UNCONFIGURED_ORGANIZATION_UNIT + curUnit);
			}
		}
	}

	/*
	 * method to validate that every division configured is actually assigned
	 */
	private void validateIfDivisionsAreAssigned(ArrayList<String> assignedUnits, Division[] configuredDiv)
			throws FeeCalculatorException {

		ArrayList<String> configuredUnitsNamesOnly = new ArrayList<>();
		for (Division tmpDiv : configuredDiv) {
			configuredUnitsNamesOnly.add(tmpDiv.getName());
		}

		for (String curUnit : configuredUnitsNamesOnly) {
			if (!assignedUnits.contains(curUnit)) {
				throw new FeeCalculatorException(ErrorMessages.UNASSIGNED_ORGANIZATION_UNIT + curUnit);
			}
		}
	}
	
	
	/*
	 * Method to validate that every area configured is actually assigned
	 */
	
	private void validateIfAreasAreAssigned(Division[] assignedAreas, Area[] configuredArea)
			throws FeeCalculatorException {
		
		
		// Map the configured areas in the json file to an arrayList
		 

		ArrayList<String> configuredUnitsNamesOnly = new ArrayList<>();
		for (Area tmpArea : configuredArea) {
			configuredUnitsNamesOnly.add(tmpArea.getName());
		}
		
		
		// Map the assigned areas from the division json file to arrayList
		
		ArrayList<String> assignedUnitsNamesOnly = new ArrayList<>();
		for (Division tmpDiv : assignedAreas) {
			assignedUnitsNamesOnly.addAll(tmpDiv.getAreas());
		}

		
		// Check if all the configured ones are actually assigned

		for (String curUnit : configuredUnitsNamesOnly) {
			if (!assignedUnitsNamesOnly.contains(curUnit)) {
				throw new FeeCalculatorException(ErrorMessages.UNASSIGNED_ORGANIZATION_UNIT + curUnit);
			}
		}
	}
	
	/*
	 * Method to validate that every branch configured is actually assigned (very similar as upper one)
	 */
	
	private void validateIfBranchesAreAssigned(Area[] assignedBranches, Branch[] configuredBranch)
			throws FeeCalculatorException {

		
		ArrayList<String> configuredUnitsNamesOnly = new ArrayList<>();
		for (Branch tmpBranch : configuredBranch) {
			configuredUnitsNamesOnly.add(tmpBranch.getName());
		}
		
		ArrayList<String> assignedUnitsNamesOnly = new ArrayList<>();
		for (Area tmpDiv : assignedBranches) {
			assignedUnitsNamesOnly.addAll(tmpDiv.getBranches());
		}

		for (String curUnit : configuredUnitsNamesOnly) {
			if (!assignedUnitsNamesOnly.contains(curUnit)) {
				throw new FeeCalculatorException(ErrorMessages.UNASSIGNED_ORGANIZATION_UNIT + curUnit);
			}
		}
	}

	public HashMap<String, Branch> getBranchesMap() {
		return branchesMap;
	}

	public void setBranchesMap(HashMap<String, Branch> branchesMap) {
		this.branchesMap = branchesMap;
	}

	public HashMap<String, Area> getAreasMap() {
		return areasMap;
	}

	public void setAreasMap(HashMap<String, Area> areasMap) {
		this.areasMap = areasMap;
	}

	public HashMap<String, Division> getDivisionsMap() {
		return divisionsMap;
	}

	public void setDivisionsMap(HashMap<String, Division> divisionsMap) {
		this.divisionsMap = divisionsMap;
	}

}
