package com.trainpuzzle.infrastructure;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import android.os.Environment;
import android.util.Log;
import com.thoughtworks.xstream.XStream;
import com.trainpuzzle.controller.Config;
import com.trainpuzzle.model.level.Campaign;
import com.trainpuzzle.model.level.Level;

public class FileManager {
	private static String storageLocation = Environment.getExternalStorageDirectory().getPath() +"/train/";
	

    
	//Saving and Loading Levels
	public static void saveLevel(Level level, String filename) {
		saveObject(level,filename);
	}

	public static void saveLevel(Level level, String campaignName, String levelNumber){
		String filename = "Campaigns/" + campaignName + "/Saves/" + levelNumber + ".xml";
		saveLevel(level, filename);
	}
	
	public static Level loadLevel(String filename) {
		return (Level)loadObject(filename);
	}
	
	public static Level loadLevelSave(String campaignName, String levelNumber) {
		String filename = "Campaigns/" + campaignName + "/Saves/" + levelNumber + ".xml";
		return loadLevel(filename);
	}
	
	public static Level loadLevelMaster(String campaignName, String levelNumber) {
		String filename = "Campaigns/" + campaignName + "/Levels/" + levelNumber + ".xml";
		return loadLevel(filename);
	}
	
	//Saving and Loading Campaigns
	public static void saveCampaign(Campaign campaign, String campaignName) {
		String filename = "Campaigns/" + campaignName + ".xml";
		saveObject(campaign, filename);
	}
	
	public static Campaign loadCampaign(String campaignName) {
		String filename =  "Campaigns/" + campaignName +  ".xml";
		Campaign loadedCampaign = (Campaign)loadObject(filename);
		return loadedCampaign;
	}
	
    public static void saveObject(Object object, String filename) {
		File file = new File(storageLocation + filename); 
		try {
			PrintStream out = new PrintStream(file);
			XStream xstream = new XStream();
			xstream.toXML(object, out);
			System.out.println("wrote to file: " + file.getAbsoluteFile());
		}
		// TODO: Print out exceptions. Should display them in a dialog...
		catch (IOException e) { 
			System.out.println(e); 
		}
	}
    
    // Saving and Loading Configuration
    public static Config loadConfig(){
    	File configFile = new File(storageLocation + "config.xml");
    	Config loadedConfig;
    	
    	if(configFile.exists()){
    		loadedConfig = (Config)FileManager.loadObject("config.xml");
    	}
    	else{
    		initializeDirectories();
    		initializeFiles();
    		loadedConfig = (Config)FileManager.loadObject("config.xml");
    	}
    	
    	return loadedConfig;
    }
    
	public static void saveConfig(Config config){
		FileManager.saveObject(config, "config.xml");
	}
    //Helper functions for initialization of application files and directories
	
	private static void initializeDirectories(){
		checkOrInitializeDirectory(storageLocation);
		checkOrInitializeDirectory(storageLocation +"/Campaigns");
		checkOrInitializeDirectory(storageLocation +"/Campaigns/Campaign1");
		checkOrInitializeDirectory(storageLocation +"/Campaigns/Campaign1/Saves");
	}
	
	private static void initializeFiles(){
		Config defaultConfig = new Config();
		saveConfig(defaultConfig);
		Campaign defaultCampaign = new Campaign();
		saveCampaign(defaultCampaign, "Campaign1");
	}
	
    private static void checkOrInitializeDirectory(String directory) {
        // Create app dir  in specified location if it does not already exist
        File path = new File(directory);
        if(! path.isDirectory()) {
            if ( path.mkdirs() )
            {
                Log.d("SUCCESS","Directory created: " + storageLocation);
            }
            else
            {
                Log.w("FAIL","Failed to create directory: " + storageLocation);
            }
        }
    }
	//Helper functions for loading and saving objects
  	public static Object loadObject(String filename) {
  		File file = new File(storageLocation + filename);
  		Object objectLoaded = new Object();
  		try {
	    	
	    	XStream xstream = new XStream();
	    	objectLoaded = xstream.fromXML(file);
	        System.out.println("loaded from file: " + file.getAbsoluteFile());    
	    }
		// TODO: Print out exceptions. Should display them in a dialog...
	    catch (Exception e) { System.out.println(e); }
  		return objectLoaded;
	}
}