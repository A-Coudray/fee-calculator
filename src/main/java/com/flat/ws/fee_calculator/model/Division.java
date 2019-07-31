package com.flat.ws.fee_calculator.model;

import java.util.HashMap;

public class Division extends AOrganizationUnit {
	
	private HashMap<String, AOrganizationUnit> areas;

	public HashMap<String, AOrganizationUnit> getAreas() {
		return areas;
	}

	public void setAreas(HashMap<String, AOrganizationUnit> areas) {
		this.areas = areas;
	}


}
