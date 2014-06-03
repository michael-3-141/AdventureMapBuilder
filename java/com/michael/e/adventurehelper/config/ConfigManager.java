package com.michael.e.adventurehelper.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigManager {

	public static File file;
	
	public static final String GENERAL_CATEGORY = "General";
	
	public static final String DEBUG_MODE_KEY = "DebugMode";
	public static boolean debugMode;
	
	public static void init(File file)
	{
		ConfigManager.file = file;
		Configuration config = new Configuration(file);
		
		config.load();
		
		debugMode = config.get(GENERAL_CATEGORY, DEBUG_MODE_KEY, false).getBoolean(false);
		
		config.save();
	}
	
	public static void save()
	{
		if(file == null)return;
		Configuration config = new Configuration(file);
		
		config.load();
		
		config.get(GENERAL_CATEGORY, DEBUG_MODE_KEY, false).set(debugMode);
		
		config.save();
	}
}
