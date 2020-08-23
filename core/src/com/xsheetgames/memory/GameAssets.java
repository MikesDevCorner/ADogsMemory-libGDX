package com.xsheetgames.memory;

import java.util.Date;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.interfaces.NativeFunctions;

public class GameAssets {
	
	public static AssetManager manager;
	public static Vector2 emptyVector2 = new Vector2();
	public static NativeFunctions nativ; 
	public static FileHandle LogFileHandle;
	public static FileHandle settingsFileHandle;
	
	public static TweenManager tweenManager = new TweenManager();


	/******************************************************************************************/
	
	
	public static void load() {
		GameAssets.LogFileHandle = null;
		GameAssets.settingsFileHandle = null;
		manager = new AssetManager();
		loadAssetsForLoadingScreen(true);
	}
	
	
	private static void initStaticFiles() {
		
		if(Gdx.files.isExternalStorageAvailable()) {
			GameAssets.LogFileHandle = Gdx.files.external("a_dogs_memory/errorlog.txt");
		}
		
		if(Gdx.files.isLocalStorageAvailable()) {
			
			GameAssets.settingsFileHandle = Gdx.files.local("a_dogs_memory/settings.txt");
		
			//Settings:
			try {
				if(GameAssets.settingsFileHandle.exists() == false) { //create settings if not exists
					GameAssets.settingsFileHandle.writeString(Configuration.musicEnabled + ";" + Configuration.soundEnabled + ";" + Configuration.vibrateEnabled,false);
				}
				
				String settingsString = GameAssets.settingsFileHandle.readString();
				String[] splitResult = settingsString.split(";");
				Configuration.musicEnabled = Boolean.parseBoolean(splitResult[0]);
				Configuration.soundEnabled = Boolean.parseBoolean(splitResult[1]);
				Configuration.vibrateEnabled = Boolean.parseBoolean(splitResult[2]);
				
				
			} catch(Exception e) {
				Date n = new Date();
				if(GameAssets.LogFileHandle != null) GameAssets.LogFileHandle.writeString(n.toString() + ": Problem reading the settings.txt.", true);
			}
		} else {
			GameAssets.LogFileHandle = null;
		}
		
	}
	
	
	public static void loadAssetsForLoadingScreen(boolean firstTime) {
		
		manager.load("loading/blackLayer.png", Texture.class);
		manager.load("backgrounds/background.jpg", Texture.class);
		manager.load("fonts/memory.fnt", BitmapFont.class);
		manager.finishLoading();				
		
		if(firstTime) initStaticFiles();		
		if(firstTime) loadGameAssets();
	}
	
	
	public static void loadGameAssets() {
		manager.load("backgrounds/splash.jpg", Texture.class);
		manager.load("images/cards.pack", TextureAtlas.class);
		manager.load("images/countdown.pack", TextureAtlas.class);
		
		manager.load("images/rate.png", Texture.class);
		manager.load("images/share.png", Texture.class);
		manager.load("images/btn_right.png", Texture.class);
		manager.load("images/mittel.png", Texture.class);
		manager.load("images/viel.png", Texture.class);
		manager.load("images/wenig.png", Texture.class);
		
		
		manager.load("images/statusbar.png", Texture.class);
		
		manager.load("sounds/backloop.mp3", Music.class);
		
		manager.load("loading/emptyLayer.png", Texture.class);
		
		manager.load("sounds/bark.ogg", Sound.class);
		manager.load("sounds/bark2.ogg", Sound.class);
		manager.load("sounds/fail.ogg", Sound.class);
		manager.load("sounds/flip.ogg", Sound.class);
		manager.load("sounds/flip2.ogg", Sound.class);
		manager.load("sounds/success.ogg", Sound.class);
		manager.load("sounds/win.mp3", Music.class);
		manager.load("sounds/click.ogg", Sound.class);
	}
	
	public static void loadGameAssets2() {
		manager.load("images/dog_jump.pack", TextureAtlas.class);
	}
	
	public static void loadGameAssets3() {
		manager.load("images/dog_bark.pack", TextureAtlas.class);
		manager.load("backgrounds/victory.jpg", Texture.class);
	}
	
	public static void unloadGameAssets() {
		try { manager.unload("backgrounds/splash.jpg"); } catch(Exception e) {}
		try { manager.unload("images/cards.pack"); } catch(Exception e) {}
		try { manager.unload("images/countdown.pack"); } catch(Exception e) {}
		try { manager.unload("images/dog_bark.pack"); } catch(Exception e) {}
		try { manager.unload("images/dog_jump.pack"); } catch(Exception e) {}
		
		try { manager.unload("images/rate.png"); } catch(Exception e) {}
		try { manager.unload("images/share.png"); } catch(Exception e) {}
		try { manager.unload("images/btn_right.png"); } catch(Exception e) {}
		try { manager.unload("images/mittel.png"); } catch(Exception e) {}
		try { manager.unload("images/viel.png"); } catch(Exception e) {}
		try { manager.unload("images/wenig.png"); } catch(Exception e) {}
		
		try { manager.unload("images/statusbar.png"); } catch(Exception e) {}
		
		try { manager.unload("backgrounds/victory.jpg"); } catch(Exception e) {}
		
		try { manager.unload("loading/emptyLayer.png"); } catch(Exception e) {}		
		
		try { manager.unload("sounds/backloop.mp3"); } catch(Exception e) {}
		
		try { manager.unload("sounds/bark.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/bark2.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/fail.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/flip.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/flip2.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/success.ogg"); } catch(Exception e) {}
		try { manager.unload("sounds/win.mp3"); } catch(Exception e) {}
		try { manager.unload("sounds/click.ogg"); } catch(Exception e) {}
	}
	
	public static boolean assetsLoaded(SpriteBatch batch) {
		if(manager != null && batch != null) {
			boolean val = manager.update();
			
			if(val == false) {
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
				drawLoadingScreen(batch);
			}
			return val;
		} else return false;
	}
	
	
	public static void setNative(NativeFunctions n) {
		nativ = n;
	}
	
	
	
	
	public static void playSound (Sound sound) {
		if (Configuration.soundEnabled == true) sound.play(1f);
	}
	
	public static void playSound (Sound sound, float volume) {
		if (Configuration.soundEnabled == true) sound.play(volume);
	}
	
	public static void playMusic (Music music, boolean looping, float volume) {
		if (Configuration.musicEnabled == true) {
			music.setLooping(looping);
			music.setVolume(volume);
			music.play();
		}
	}
	
	public static void pauseMusic(Music music) {
		try {
			music.pause();
		} catch(Exception e) {}
	}	
	
	public static void queueTextureAssetLoading(String asset) {
		manager.load(asset, Texture.class);
	}
	
	public static void queueSoundAssetLoading(String asset) {
		manager.load(asset, Sound.class);
	}
	
	public static void queueMusicAssetLoading(String asset) {
		manager.load(asset, Music.class);
	}
	
	public static void queueTextureAtlasAssetLoading(String asset) {
		manager.load(asset, TextureAtlas.class);
	}
	
	
	public static Texture fetchTexture(String name) {
		if(manager.isLoaded(name)) {
			return manager.get(name, Texture.class);
		} return null;
	}
	
	public static BitmapFont fetchFont(String name) {
		if(manager.isLoaded(name)) {
			return manager.get(name, BitmapFont.class);
		} else return null;
	}

	
	public static Sound fetchSound(String name) {
		if(manager.isLoaded(name)) {
			return manager.get(name, Sound.class);
		} else return null;
	}
	
	public static Music fetchMusic(String name) {
		if(manager.isLoaded(name)) {
			return manager.get(name, Music.class);
		} else return null;
	}
	
	public static TextureAtlas fetchTextureAtlas(String name) {
		if(manager.isLoaded(name)) {
			return manager.get(name, TextureAtlas.class);
		} else return null;
	}
	
	public static void vibrate(int duration) {
		if(Configuration.vibrateEnabled) Gdx.input.vibrate(duration);
	}
	
	
	public static boolean loadAssets() {
		return !manager.update();
	}
	
	public static float getLoadingProcess() {
		return manager.getProgress();
	}
	
	public static int getLoadingSteps(int step) {
		int progress = (int)(getLoadingProcess()*100);
		progress = ((int)(progress/step))*step;
		return progress;	
	}
	
	public static void drawLoadingScreen(SpriteBatch batch) {
		batch.begin();
		batch.draw(manager.get("backgrounds/background.jpg", Texture.class), 0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
		batch.draw(manager.get("loading/blackLayer.png", Texture.class), -10, -10, Configuration.TARGET_WIDTH+10, Configuration.TARGET_HEIGHT+10);
		//GameAssets.fetchFont("fonts/memory.fnt").draw(batch, "LOADING "+GameAssets.getLoadingSteps(5)+" of 100", 415f, 200f);
		GlyphLayout layout = new GlyphLayout();
		layout.setText(GameAssets.fetchFont("fonts/memory.fnt"), "LOADING...");
		GameAssets.fetchFont("fonts/memory.fnt").draw(batch, "LOADING...", Configuration.TARGET_WIDTH/2 - layout.width/2, 230f);
		batch.end();
	}
	
	public static void unloadAsset(String fileName) {
		manager.unload(fileName);
	}
	
	public static void dispose() {
		manager.dispose();
		manager = null;
	}	
}
