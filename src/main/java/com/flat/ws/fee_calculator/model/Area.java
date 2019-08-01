package com.flat.ws.fee_calculator.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Area{


	private ArrayList<String> branches;
	

	private String name;
	
	private OrganizationUnitConfig config;
	
	private HashMap <String, Branch> branchesObject;
	
	private Division parent;
	
	
	public ArrayList<String> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<String> branches) {
		this.branches = branches;
	}




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

	
	@Override
	public String toString() {
		return "Area [branches=" + branches + ", name=" + name + ", config=" + config + "]";
	}

	public HashMap <String, Branch> getBranchesObject() {
		return branchesObject;
	}

	public void setBranchesObject(HashMap <String, Branch> branchesObject) {
		this.branchesObject = branchesObject;
	}

	public Division getParent() {
		return parent;
	}

	public void setParent(Division parent) {
		this.parent = parent;
	}
}
