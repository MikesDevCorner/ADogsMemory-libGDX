package com.xsheetgames.memory;

import com.badlogic.gdx.Application;

public class Configuration {

	public static boolean useAds = false;
	public static String store = "amazon"; //play, amazon
	public static String GoogleAnalyticsTracker = "";
	
	
	public static int TARGET_WIDTH;
	public static int TARGET_HEIGHT;
	
	public static boolean waitForAd = false;
	public static boolean soundEnabled;
	public static boolean musicEnabled;
	public static boolean vibrateEnabled;
	public static int debugLevel;
	
	public static String rateTarget;
	public static String shareAdress;
	
	public static String chartboostAppKeyPlay = "";
	public static String chartboostAppSigneturePlay = "";	
	
	public static String chartboostAppKeyAmazon = "";
	public static String chartboostAppSignetureAmazon = "";
	
	
	public static void load() {
		TARGET_HEIGHT = 800;
		TARGET_WIDTH = 1280;
		
		soundEnabled = true;
		musicEnabled = true;
		vibrateEnabled = true;
		
		if(Configuration.store.equals("amazon")) {
			rateTarget = "http://www.amazon.com/gp/mas/dl/android?p=";
			shareAdress = "http://www.amazon.com/gp/mas/dl/android?p=";
		}
		if(Configuration.store.equals("play")) {
			rateTarget = "market://details?id=";
			shareAdress = "https://play.google.com/store/apps/details?id=";
		}
		
		/*****DEBUG CONFIGURATION****************************/
		debugLevel = Application.LOG_ERROR;
		//debugLevel = Application.LOG_DEBUG;
		//debugLevel = Application.LOG_NONE;
		//debugLevel = Application.LOG_INFO;
		/***************************************************/
	}
	
}
