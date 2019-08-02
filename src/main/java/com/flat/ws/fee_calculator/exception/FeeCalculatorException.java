package com.flat.ws.fee_calculator.exception;

public class FeeCalculatorException extends Exception {

	/**
	 * 
	 */
	
	private int status;
	
	private static final long serialVersionUID = 7411276575294666319L;
	
	public FeeCalculatorException (String message, Throwable e, int status) {
		super(message, e);
		this.setStatus(status);
	}
	
	public FeeCalculatorException (String message, int status) {
		super(message);
		this.setStatus(status);
	}
	
	
	public FeeCalculatorException (String message, Throwable e) {
		super(message, e);
	}
	
	public FeeCalculatorException (String message) {
		super(message);
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	

}
