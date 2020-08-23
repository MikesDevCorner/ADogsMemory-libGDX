package com.xsheetgames.memory;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xsheetgames.memory.interfaces.NativeFunctions;

public class Main implements NativeFunctions {
	public static void main(String[] args) {
		
		Main game = new Main();
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "A-Dogs-Pairs";
		//cfg.useGL20 = true;
		cfg.width = 1280;  //800;
		cfg.height = 800;  //480;
		
		new LwjglApplication(new MemoryGdxGame(game), cfg);
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
