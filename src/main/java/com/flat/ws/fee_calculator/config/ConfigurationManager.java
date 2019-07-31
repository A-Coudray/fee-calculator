package com.flat.ws.fee_calculator.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.flat.ws.fee_calculator.model.Client;

import com.google.gson.Gson;

public class ConfigurationManager {
	
	
	
	
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
    
    
    public void loadConfiguration () {
    	
    	
        String path = "src/main/resources/config/clients-config.json";
        BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Gson gson = new Gson();
        Client[] json = gson.fromJson(bufferedReader, Client[].class);
        
        System.out.println(json[0].toString());
    	
    }
    
    

}
