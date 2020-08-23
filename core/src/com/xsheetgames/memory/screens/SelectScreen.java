package com.xsheetgames.memory.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.GameAssets;


public class SelectScreen extends AbstractScreen  {

	private SpriteBatch batch;
	private Sprite screenBackground, wenigBtn, mittelBtn, vielBtn;
	private boolean assetsLoaded;
	private boolean disposed = false;
	
	public SelectScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		if(this.disposed == false) {
			if(GameAssets.assetsLoaded(batch)) {
				
				if(assetsLoaded == false) this.doAssetProcessing();
				this.batch.getProjectionMatrix().setToOrtho2D(0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
				this.batch.begin();
				batch.disableBlending();
				this.screenBackground.draw(batch);
				batch.enableBlending();
				this.wenigBtn.draw(batch);
				this.mittelBtn.draw(batch);
				this.vielBtn.draw(batch);
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
			GameAssets.nativ.trackPageView("/SelectScreen");
			
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
			this.wenigBtn = null;
			this.mittelBtn = null;
			this.vielBtn = null;
			this.screenBackground = null;
			this.disposed = true;
		}		
	}
	
	
	private void doAssetProcessing() {
		this.assetsLoaded = true;
		if(this.screenBackground == null) {
			this.screenBackground = new Sprite(GameAssets.fetchTexture("backgrounds/background.jpg"));
			this.screenBackground.setSize(GameAssets.fetchTexture("backgrounds/background.jpg").getWidth(), GameAssets.fetchTexture("backgrounds/background.jpg").getHeight());
		}
		
		if(this.wenigBtn == null) {			
			this.wenigBtn = new Sprite(GameAssets.fetchTexture("images/wenig.png"));
			this.wenigBtn.setSize(GameAssets.fetchTexture("images/wenig.png").getWidth(),GameAssets.fetchTexture("images/wenig.png").getHeight());
			this.wenigBtn.setPosition(108f,300f);
		}
		
		if(this.mittelBtn == null) {			
			this.mittelBtn = new Sprite(GameAssets.fetchTexture("images/mittel.png"));
			this.mittelBtn.setSize(GameAssets.fetchTexture("images/mittel.png").getWidth(),GameAssets.fetchTexture("images/mittel.png").getHeight());
			this.mittelBtn.setPosition(499,300f);
		}
		
		if(this.vielBtn == null) {			
			this.vielBtn = new Sprite(GameAssets.fetchTexture("images/viel.png"));
			this.vielBtn.setSize(GameAssets.fetchTexture("images/viel.png").getWidth(),GameAssets.fetchTexture("images/viel.png").getHeight());
			this.vielBtn.setPosition(890,300f);
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
	
	private boolean touchedEvent(Vector2 touchPoint) {
		boolean returnValue = false;
		if(wenigBtn != null && wenigBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			GameAssets.loadGameAssets2();
			GameScreen.gameMode = 1;
			CountdownScreen cds = new CountdownScreen(this.game);
			this.game.setScreen(cds);
			returnValue = true;
		}
		if(mittelBtn != null && mittelBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			GameAssets.loadGameAssets2();
			GameScreen.gameMode = 2;
			CountdownScreen cds = new CountdownScreen(this.game);
			this.game.setScreen(cds);
			returnValue = true;
		}
		if(vielBtn != null && vielBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
			this.dispose();
			GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
			GameAssets.loadGameAssets2();
			GameScreen.gameMode = 3;
			CountdownScreen cds = new CountdownScreen(this.game);
			this.game.setScreen(cds);
			returnValue = true;
		}
		return returnValue;
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			this.dispose();
			this.game.setScreen(new TitleScreen(this.game));
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
