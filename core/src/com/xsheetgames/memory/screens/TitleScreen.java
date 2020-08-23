package com.xsheetgames.memory.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.GameAssets;


public class TitleScreen extends AbstractScreen  {

	private SpriteBatch batch;
	private Sprite screenBackground, playButton, rateBtn, shareBtn;
	private boolean assetsLoaded;
	private boolean disposed = false;
	private boolean endApp = false;
	public static boolean adShowed = false;
	
	public TitleScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		if(this.disposed == false) {
			if(GameAssets.assetsLoaded(batch)) {
				
				if(TitleScreen.adShowed == false) {
					GameAssets.nativ.showFullScreenAd();
					TitleScreen.adShowed = true;
				}
				
				if(assetsLoaded == false) this.doAssetProcessing();
				this.batch.getProjectionMatrix().setToOrtho2D(0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
				this.batch.begin();
				batch.disableBlending();
				this.screenBackground.draw(batch);
				batch.enableBlending();
				this.playButton.draw(batch);
				this.rateBtn.draw(batch);
				//this.shareBtn.draw(batch);
				this.batch.end();
			}
		}		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
			if(this.endApp == true) {
				GameAssets.nativ.trackPageView("/Exit");
				Gdx.app.exit();
			}
			
			GameAssets.nativ.trackPageView("/TitleScreen");
			
			Gdx.input.setInputProcessor(this);
			Gdx.input.setCatchBackKey(true);
			
			this.batch = new SpriteBatch();
			this.assetsLoaded = false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		GameAssets.loadGameAssets();
		this.assetsLoaded = false;		
	}

	@Override
	public void dispose() {
		if(this.disposed == false) {
			if(this.batch != null) this.batch.dispose();
			this.playButton = null;
			this.rateBtn = null;
			this.shareBtn = null;
			this.screenBackground = null;
			this.disposed = true;
		}		
	}
	
	
	private void doAssetProcessing() {
		this.assetsLoaded = true;
		if(this.screenBackground == null) {
			this.screenBackground = new Sprite(GameAssets.fetchTexture("backgrounds/splash.jpg"));
			this.screenBackground.setSize(GameAssets.fetchTexture("backgrounds/splash.jpg").getWidth(), GameAssets.fetchTexture("backgrounds/splash.jpg").getHeight());
		}
		if(this.playButton == null) {			
			this.playButton = new Sprite(GameAssets.fetchTexture("loading/emptyLayer.png"));
			this.playButton.setSize(400f,200f);
			this.playButton.setPosition(230f,50f);
		}
		
		if(this.rateBtn == null) {			
			this.rateBtn = new Sprite(GameAssets.fetchTexture("images/rate.png"));
			this.rateBtn.setSize(GameAssets.fetchTexture("images/rate.png").getWidth(),GameAssets.fetchTexture("images/rate.png").getHeight());
			this.rateBtn.setPosition(1035,710f);
		}
		
		if(this.shareBtn == null) {			
			this.shareBtn = new Sprite(GameAssets.fetchTexture("images/share.png"));
			this.shareBtn.setSize(GameAssets.fetchTexture("images/share.png").getWidth(),GameAssets.fetchTexture("images/share.png").getHeight());
			this.shareBtn.setPosition(805f,20f);
		}
		
		if(GameAssets.manager.isLoaded("sounds/win.mp3") && Configuration.musicEnabled == true) {
			if(GameAssets.fetchMusic("sounds/win.mp3").isPlaying() == true) {
				GameAssets.fetchMusic("sounds/win.mp3").stop();
			}
		}
				
				
		if(GameAssets.fetchMusic("sounds/backloop.mp3").isPlaying() == false && Configuration.musicEnabled == true) {
			GameAssets.fetchMusic("sounds/backloop.mp3").stop();
			GameAssets.playMusic(GameAssets.fetchMusic("sounds/backloop.mp3"), true, 1f);
		}
		
	}
	
	
	public void shareButtonPressed() {		
		GameAssets.nativ.sendEvent("SocialAction", "Share Button", "pressed", 1);
		GameAssets.nativ.share("A Dogs Memory","I am currently playing <a href=http://xsheetgames.com/dog.html>A Dogs Memory</a> for Android. Love this game! Cannot stop playing it.");
	}
	
	public void rateButtonPressed() {
		GameAssets.nativ.sendEvent("SocialAction", "Rate Button", "pressed", 1);
		GameAssets.nativ.rate();
	}
	
	
	private boolean touchedEvent(Vector2 touchPoint) {
		boolean returnValue = false;
		if(playButton != null && playButton.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			SelectScreen cds = new SelectScreen(this.game);
			this.game.setScreen(cds);
			returnValue = true;
		}
		
		if(shareBtn != null && shareBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.shareButtonPressed();
			returnValue = true;
		}
		
		if(rateBtn != null && rateBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.rateButtonPressed();
			returnValue = true;
		}
		return returnValue;
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			GameAssets.nativ.trackPageView("/Exit");
			Gdx.app.exit();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPoint = new Vector2((float)screenX/(float)Gdx.graphics.getWidth()*(float)Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT - ((float)screenY / (float)Gdx.graphics.getHeight() * (float)Configuration.TARGET_HEIGHT));
		return this.touchedEvent(touchPoint);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
