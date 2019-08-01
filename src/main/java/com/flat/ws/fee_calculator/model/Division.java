package com.flat.ws.fee_calculator.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Division {
	
	


	private ArrayList<String> areas;
	
	private String name;
	
	private OrganizationUnitConfig config;

	private HashMap <String, Area> areasObject;
	
	public ArrayList<String> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<String> areas) {
		this.areas = areas;
	}

	public OrganizationUnitConfig getConfig() {
		return config;
	}

	public void setConfig(OrganizationUnitConfig config) {
		this.config = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Division [areas=" + areas + ", name=" + name + ", config=" + config + "]";
	}

	public HashMap <String, Area> getAreasObject() {
		return areasObject;
	}

	public void setAreasObject(HashMap <String, Area> areasObject) {
		this.areasObject = areasObject;
	}
	
}
