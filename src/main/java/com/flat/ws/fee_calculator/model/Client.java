package com.flat.ws.fee_calculator.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Client extends AbstractMethodError {
	
	@Override
	public String toString() {
		return "Client [divisions=" + divisions + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1061967158104984264L;
	
	
	private ArrayList <String> divisions;

	public ArrayList <String>  getDivisions() {
		return divisions;
	}

	public void setDivisions(ArrayList<String> divisions) {
		this.divisions = divisions;
	}

}
