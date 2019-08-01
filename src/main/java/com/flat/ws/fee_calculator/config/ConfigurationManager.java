package com.flat.ws.fee_calculator.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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

	private HashMap<String, Branch> branchesMap;

	private HashMap<String, Area> areasMap;

	private HashMap<String, Division> divisionsMap;

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

	public void loadConfiguration() {

		String confiPathg = "src/main/resources/config/";
		BufferedReader bufferedReaderClient = null;
		BufferedReader bufferedReaderDivision = null;
		BufferedReader bufferedReaderArea = null;
		BufferedReader bufferedReaderBranch = null;
		try {
			bufferedReaderClient = new BufferedReader(new FileReader(confiPathg + CLIENTS_CONFIG));
			bufferedReaderDivision = new BufferedReader(new FileReader(confiPathg + DIVISIONS_CONFIG));
			bufferedReaderArea = new BufferedReader(new FileReader(confiPathg + AREAS_CONFIG));
			bufferedReaderBranch = new BufferedReader(new FileReader(confiPathg + BRANCHES_CONFIG));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();
		Client jsonClient = gson.fromJson(bufferedReaderClient, Client.class);

		Division[] jsonDiv = gson.fromJson(bufferedReaderDivision, Division[].class);
		Area[] jsonArea = gson.fromJson(bufferedReaderArea, Area[].class);
		Branch[] jsonBranch = gson.fromJson(bufferedReaderBranch, Branch[].class);

		System.out.println(jsonClient.toString());
		System.out.println(jsonDiv[0].toString());
		System.out.println(jsonArea[0].toString());
		System.out.println(jsonBranch[0].toString());

		ArrayList<Area> areas = new ArrayList<>();
		ArrayList<Division> divisions = new ArrayList<>();

		for (Area area : jsonArea) {
			for (String br : area.getBranches()) {
				for (Branch curBr : jsonBranch) {
					if (null != br && br.equalsIgnoreCase(curBr.getName())) {
						curBr.setParent(area);
						if (null == branchesMap.putIfAbsent(br, curBr)) {
							// throw
						}
					}
				}
			}
		}

		for (Division div : jsonDiv) {
			for (String ar : div.getAreas()) {
				for (Area curAr : areas) {
					if (null != ar && ar.equalsIgnoreCase(curAr.getName())) {
						curAr.setParent(div);
						if (null == areasMap.putIfAbsent(ar, curAr)) {
							// throw
						}
					}
				}
			}
		}


		for (String div : jsonClient.getDivisions()) {
			for (Division curDiv : divisions) {
				if (null != div && div.equalsIgnoreCase(curDiv.getName())) {
					curDiv.setParent(jsonClient);
					if (null == divisionsMap.putIfAbsent(div, curDiv)) {
						// throw
					}
				}
			}
		}
		this.setClientConfig(jsonClient);

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
