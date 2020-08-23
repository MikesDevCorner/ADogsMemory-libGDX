package com.xsheetgames.memory.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.GameAssets;
import com.xsheetgames.memory.layouts.AbstractCardLayout;
import com.xsheetgames.memory.utils.AtlasAnimation;

public class VictoryScreen extends AbstractScreen {

	private SpriteBatch batch;
	private Sprite screenBackground, shareBtn, rateBtn, rightBtn, replay;
	private boolean assetsLoaded;
	private boolean disposed = false;
	private AtlasAnimation barkingDog;
	private boolean adShowed = false;
	public static int howOftan = 0;
	
	public VictoryScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		if(this.disposed == false) {
			if(GameAssets.assetsLoaded(batch)) {
				
				if(VictoryScreen.howOftan % 2 == 0 && this.adShowed == false) {
					GameAssets.nativ.showFullScreenAd();
					this.adShowed = true;
				}
				
				if(assetsLoaded == false) this.doAssetProcessing();
				this.batch.getProjectionMatrix().setToOrtho2D(0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
				this.batch.begin();
				batch.disableBlending();
				this.screenBackground.draw(batch);
				batch.enableBlending();
				this.barkingDog.draw(delta, batch);
				this.shareBtn.draw(batch);
				this.rateBtn.draw(batch);
				this.rightBtn.draw(batch);
				this.replay.draw(batch);
				GameAssets.fetchFont("fonts/memory.fnt").draw(batch, String.format("%02d", (int) (GameScreen.timeCounter / 60)) + ":" + String.format("%02d", (int) (GameScreen.timeCounter % 60)), 455f, 450f);
				GameAssets.fetchFont("fonts/memory.fnt").draw(batch, AbstractCardLayout.trys+"", 480f, 378f);
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
		GameAssets.nativ.trackPageView("/VictoryScreen");
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		this.adShowed = false;
		VictoryScreen.howOftan++;
		
		this.batch = new SpriteBatch();
		this.assetsLoaded = false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		GameAssets.loadGameAssets();
		GameAssets.loadGameAssets2();
		GameAssets.loadGameAssets3();
		this.assetsLoaded = false;
	}

	@Override
	public void dispose() {
		if(this.disposed == false) {
			if(this.batch != null) this.batch.dispose();
			this.screenBackground = null;
			if(this.barkingDog != null) {
				this.barkingDog.dispose();
				this.barkingDog = null;
			}
			this.shareBtn = null;
			this.rateBtn = null;
			this.replay = null;
			this.disposed = true;
		}		
	}
	
	
	private void doAssetProcessing() {
		this.assetsLoaded = true;
		
		this.screenBackground = new Sprite(GameAssets.fetchTexture("backgrounds/victory.jpg"));
		this.screenBackground.setSize(GameAssets.fetchTexture("backgrounds/victory.jpg").getWidth(), GameAssets.fetchTexture("backgrounds/victory.jpg").getHeight());
		
		this.rateBtn = new Sprite(GameAssets.fetchTexture("loading/emptyLayer.png"));
		this.rateBtn.setSize(220,60);
		this.rateBtn.setPosition(180f,38f);
		
		this.shareBtn = new Sprite(GameAssets.fetchTexture("loading/emptyLayer.png"));
		this.shareBtn.setSize(220,60);
		this.shareBtn.setPosition(180f,115f);
		
		this.rightBtn = new Sprite(GameAssets.fetchTexture("loading/emptyLayer.png"));
		this.rightBtn.setSize(200,160);
		this.rightBtn.setPosition(989f, 367f);
		
		this.replay = new Sprite(GameAssets.fetchTexture("loading/emptyLayer.png"));
		this.replay.setSize(200,160);
		this.replay.setPosition(974f, 535f);
		
		this.barkingDog = new AtlasAnimation(GameAssets.fetchTextureAtlas("images/dog_bark.pack"),"dog_bark_sml",898f,13f,0f,0f,100f,100f, true, 3f, GameAssets.fetchSound("sounds/bark2.ogg"),0.4f);
		
		if(Configuration.musicEnabled == true) {
			GameAssets.fetchMusic("sounds/backloop.mp3").stop();
			GameAssets.playMusic(GameAssets.fetchMusic("sounds/win.mp3"), false, 1f);
		}		
	}
	

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			this.dispose();
			this.game.setScreen(new TitleScreen(this.game));
			return true;
		}
		else return false;
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
		
		if(shareBtn != null && shareBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.shareButtonPressed();
			return true;
		} else if(rateBtn != null && rateBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.rateButtonPressed();
			return true;
		} else if(rightBtn != null && rightBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			TitleScreen ts = new TitleScreen(this.game);
			this.game.setScreen(ts);
			return true;
		} else if(replay != null && replay.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			CountdownScreen gs = new CountdownScreen(this.game);
			this.game.setScreen(gs);
			return true;
		}
		return false;
	}
	
	public void shareButtonPressed() {		
		GameAssets.nativ.sendEvent("SocialAction", "Share Button", "pressed", 1);
		GameAssets.nativ.share("A Dogs Memory","I am currently playing <a href=http://xsheetgames.com/dog.html>A Dogs Memory</a> for Android. Love this game! Cannot stop playing it.");
	}
	
	public void rateButtonPressed() {
		GameAssets.nativ.sendEvent("SocialAction", "Rate Button", "pressed", 1);
		GameAssets.nativ.rate();
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

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
