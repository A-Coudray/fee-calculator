package com.flat.ws.fee_calculator.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Client {
	

	private ArrayList<String> divisions;
	
	private HashMap<String, Division> divisionsObjects;
	
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

	public HashMap<String, Division> getDivisionsObjects() {
		return divisionsObjects;
	}

	public void setDivisionsObjects(HashMap<String, Division> divisionsObjects) {
		this.divisionsObjects = divisionsObjects;
	}
	
	public ArrayList<String> getDivisions() {
		return divisions;
	}

	public void setDivisions(ArrayList<String> divisions) {
		this.divisions = divisions;
	}

	

	@Override
	public String toString() {
		return "Client [divisions=" + divisions + ", name=" + name + ", config=" + config + "]";
	}

}
