package com.xsheetgames.memory.client;

import com.xsheetgames.memory.MemoryGdxGame;
import com.xsheetgames.memory.interfaces.NativeFunctions;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication implements NativeFunctions {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		return cfg;
	}

	@Override
	public ApplicationListener createApplicationListener () {
		return new MemoryGdxGame(this);
	}

	@Override
	public void showMessage(String title, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openURL(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void share(String subject, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trackPageView(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendException(String description, boolean fatal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEvent(String category, String subCategory,
			String component, long value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFullScreenAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void TriggerStandingInterstitials() {

	}

}