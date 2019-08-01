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

	private HashMap<String, Client> clientsConfig = new HashMap<>();;

	public HashMap<String, Client> getClientsConfig() {
		return clientsConfig;
	}

	public void setClientsConfig(HashMap<String, Client> clientsConfig) {
		this.clientsConfig = clientsConfig;
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
			HashMap<String, Branch> associatedBranches = new HashMap<>();
			for (String br : area.getBranches()) {
				for (Branch curBr : jsonBranch) {
					if (null != br && br.equalsIgnoreCase(curBr.getName())) {
						associatedBranches.put(br, curBr);
					}

				}
			}
			area.setBranchesObject(associatedBranches);
			areas.add(area);
		}

		for (Division div : jsonDiv) {
			HashMap<String, Area> associatedAreas = new HashMap<>();
			for (String ar : div.getAreas()) {
				for (Area curAr : areas) {
					if (null != ar && ar.equalsIgnoreCase(curAr.getName())) {
						associatedAreas.put(ar, curAr);
					}

				}
			}
			div.setAreasObject(associatedAreas);
			divisions.add(div);
		}

		HashMap<String, Division> associatedDiv = new HashMap<>();

		for (String div : jsonClient.getDivisions()) {
			for (Division curDiv : divisions) {
				if (null != div && div.equalsIgnoreCase(curDiv.getName())) {
					associatedDiv.put(div, curDiv);
				}
			}
		}
		jsonClient.setDivisionsObjects(associatedDiv);
		clientsConfig.put(jsonClient.getName(), jsonClient);

		clientsConfig.entrySet().stream().forEach(System.out::println);

	}

}
