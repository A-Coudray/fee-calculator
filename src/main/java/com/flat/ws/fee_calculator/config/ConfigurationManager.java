package com.flat.ws.fee_calculator.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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

		Gson gson = new Gson();
		Client jsonClient = gson.fromJson(bufferedReaderClient, Client.class);

		Division[] jsonDiv = gson.fromJson(bufferedReaderDivision, Division[].class);
		Area[] jsonArea = gson.fromJson(bufferedReaderArea, Area[].class);
		Branch[] jsonBranch = gson.fromJson(bufferedReaderBranch, Branch[].class);

		branchesMap = new HashMap<>();
		areasMap = new HashMap<>();
		divisionsMap = new HashMap<>();
		
		ArrayList<String> assignedOrganizationUnit = new ArrayList<>();

		for (Area area : jsonArea) {
			for (String br : area.getBranches()) {
				assignedOrganizationUnit.add(br);
				for (Branch curBr : jsonBranch) {
					if (null != br && br.equalsIgnoreCase(curBr.getName())) {
						curBr.setParent(area);
						if (null != branchesMap.putIfAbsent(br, curBr)) {
							throw new FeeCalculatorException(ErrorMessages.INVALID_AREA_CONFIG_EXCEPTION + curBr);
						}
					}
				}
			}
		}
		validateAssignedOrganizationUnits(assignedOrganizationUnit, branchesMap);

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
		this.setClientConfig(jsonClient);

	}

	/*
	 * This method is to ensure that any configured children actually exists in the
	 * configuration
	 */
	private void validateAssignedOrganizationUnits(ArrayList<String> assignedUnits, HashMap<String, ?> map) throws FeeCalculatorException {
		for (String curUnit : assignedUnits) {
			if (!map.containsKey(curUnit)) {
				throw new FeeCalculatorException(ErrorMessages.UNCONFIGURED_ORGANIZATION_UNIT+curUnit);
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
