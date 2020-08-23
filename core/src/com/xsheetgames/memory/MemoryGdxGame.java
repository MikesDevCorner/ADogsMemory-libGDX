package com.xsheetgames.memory;

import java.util.Date;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.xsheetgames.memory.interfaces.NativeFunctions;
import com.xsheetgames.memory.screens.TitleScreen;
import com.xsheetgames.memory.utils.CardAccessor;
import com.xsheetgames.memory.utils.PlayCard;

public class MemoryGdxGame extends Game {

	
	public MemoryGdxGame(NativeFunctions nativeFunctions) {
		GameAssets.setNative(nativeFunctions);
	}
	
	
	@Override
	public void create() {
		Configuration.load();
		GameAssets.load();
		Tween.registerAccessor(PlayCard.class, new CardAccessor());
		Gdx.app.setLogLevel(Configuration.debugLevel);
		Gdx.input.setCatchMenuKey(true);
		setScreen(new TitleScreen(this));
   }
	   
	   

   @Override
   public void render() {
	   try {
	   		Gdx.gl.glClearColor(1, 1, 1, 1);
	   		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	   		super.render();
	   } catch(Exception e) {
		   try {
			   	GameAssets.nativ.trackPageView("/Exception");
			   	String myException = "";
			   	String ExceptionName = "";
			   	for(StackTraceElement ste : e.getStackTrace()) {
			   		myException += ste.toString() + "\r\n";
			   	}
			   	if(e.getMessage() == null || e.getMessage() == "" || e.getMessage() == "null") ExceptionName = e.getClass().getName();
			   	else ExceptionName = e.getMessage();
		   		GameAssets.nativ.sendException(ExceptionName + "\r\n" + myException, true);
		   } catch( Exception ex) {
			   
		   }
		   try {
			   if(Gdx.files.isExternalStorageAvailable()) {
				   Date n = new Date();
				   String stackTrace = "";
				   String ExceptionName = "";
				   if(e.getMessage() == null || e.getMessage() == "" || e.getMessage() == "null") ExceptionName = e.getClass().getName();
				   else ExceptionName = e.getMessage();
				   for(StackTraceElement element : e.getStackTrace()) {
					   stackTrace += "\r\n"+element.toString();
				   }
				   if(GameAssets.LogFileHandle != null) GameAssets.LogFileHandle.writeString("\r\n\r\n\r\n\r\n\r\n-----------------------\r\nException occured. Time: "+n.toString() +"\r\nMessage: "+ExceptionName+"\r\nStackTrace: "+stackTrace+"\r\n-----------------------", true);
			   }
		   }
		   catch(Exception ex) {
			   
		   }
		   finally {
			   if(Configuration.debugLevel >= Application.LOG_DEBUG) {
				   Gdx.app.error("Exception occured", "OH NO, AN EXCEPTION!", e);
			   }
			   Gdx.app.exit();
		   }
	   }
   }
   
   
   public void dispose() {
	   try {
		   this.getScreen().dispose();
		   GameAssets.dispose();
		   super.dispose();
	   } catch(Exception excp) { }	   
		
   }
	
}
