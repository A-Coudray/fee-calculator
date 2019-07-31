package com.flat.ws.fee_calculator;

import org.rapidoid.setup.App;

/**
 * Main class to start the server
 * @author acoudray
 *
 */
public class FeeCaclculatorServer 
{

    public static void main(String[] args) {

    	
    	App.bootstrap(args);
    	
    	
    }
    
    public static void shutdown () {
    	
    	App.shutdown();
    	
    }
}
