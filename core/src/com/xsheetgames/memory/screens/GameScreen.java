package com.xsheetgames.memory.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.GameAssets;
import com.xsheetgames.memory.layouts.AbstractCardLayout;
import com.xsheetgames.memory.layouts.CardDeck18;
import com.xsheetgames.memory.layouts.CardDeck28;
import com.xsheetgames.memory.layouts.CardDeck8;
import com.xsheetgames.memory.utils.AtlasAnimation;

public class GameScreen extends AbstractScreen {

	public static boolean paused = false;
	public static float timeCounter = 0f;
	public static int gameMode;
	
	private SpriteBatch batch;
	private Sprite screenBackground, blackLayer, rateBtn, shareBtn, rightBtn, statusBar;
	private boolean assetsLoaded;
	public boolean disposed = false, setVictory = false;
	private AtlasAnimation jumpingDog;
	private boolean deckNew;
	private GlyphLayout layout;
	
	AbstractCardLayout deck;
	
	public GameScreen(Game game) {
		this.game = game;
		GameScreen.timeCounter = 0f;
		this.deckNew = true;
		layout = new GlyphLayout();
	}
	
	
	@Override
	public void render(float delta) {
		if(this.disposed == false) {
			if(GameAssets.assetsLoaded(batch)) {
				if(assetsLoaded == false) this.doAssetProcessing();
				
				if(this.deckNew == true) {
					this.deck.resetDeck();
					this.deck.presentDeck();
					this.deckNew = false;
				}
				
				this.batch.getProjectionMatrix().setToOrtho2D(0, 0, Configuration.TARGET_WIDTH, Configuration.TARGET_HEIGHT);
				this.batch.begin();
				batch.disableBlending();
				this.screenBackground.draw(batch);
				batch.enableBlending();
				
				
				this.deck.doGameLogic(delta);
				this.deck.drawCards(batch, delta);
				
				this.statusBar.draw(batch);
				GameAssets.fetchFont("fonts/memory.fnt").draw(batch,String.format("%02d", (int) (GameScreen.timeCounter / 60)) + ":" + String.format("%02d", (int) (GameScreen.timeCounter % 60)),435f,88f);
				GameAssets.fetchFont("fonts/memory.fnt").draw(batch,AbstractCardLayout.trys+"", 855f, 85f);
				
				this.jumpingDog.draw(delta, batch);
				if(GameScreen.paused == true) {
					this.blackLayer.draw(batch);
					this.shareBtn.draw(batch);
					this.rightBtn.draw(batch);
					this.rateBtn.draw(batch);
					this.layout.setText(GameAssets.fetchFont("fonts/memory.fnt"), "PAUSE");
					GameAssets.fetchFont("fonts/memory.fnt").draw(batch, "PAUSE", Configuration.TARGET_WIDTH/2 - layout.width/2f, 550f);
				} else {
					GameScreen.timeCounter += delta;
				}
				
				this.batch.end();
				
				if(this.setVictory == true) {
					this.goVictory();
				}
			}
		}		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {			
		GameAssets.nativ.trackPageView("/GameScreen");
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		this.batch = new SpriteBatch();
		this.assetsLoaded = false;
	}

	@Override
	public void hide() {
		GameAssets.nativ.TriggerStandingInterstitials();
	}

	@Override
	public void pause() {
		GameScreen.paused = true;
	}

	@Override
	public void resume() {
		GameAssets.loadGameAssets();
		GameAssets.loadGameAssets2();
		this.assetsLoaded = false;
	}

	@Override
	public void dispose() {
		if(this.disposed == false) {
			GameAssets.tweenManager.killAll();
			if(this.batch != null) this.batch.dispose();
			this.screenBackground = null;
			this.disposed = true;
			this.rateBtn = null;
			this.shareBtn = null;
			this.rightBtn = null;
			this.statusBar = null;
			if(this.jumpingDog != null) {
				this.jumpingDog.dispose();
				this.jumpingDog = null;
			}
			deck.dispose();
			GameScreen.paused = false;
		}		
	}
	
	
	private void doAssetProcessing() {
		this.assetsLoaded = true;
		
		this.screenBackground = new Sprite(GameAssets.fetchTexture("backgrounds/background.jpg"));
		this.screenBackground.setSize(GameAssets.fetchTexture("backgrounds/background.jpg").getWidth(), GameAssets.fetchTexture("backgrounds/background.jpg").getHeight());
		
		this.blackLayer = new Sprite(GameAssets.fetchTexture("loading/blackLayer.png"));
		this.blackLayer.setSize(Configuration.TARGET_WIDTH+10f, Configuration.TARGET_HEIGHT+10f);
		this.blackLayer.setPosition(-5f, -5f);
		
		this.rateBtn = new Sprite(GameAssets.fetchTexture("images/rate.png"));
		this.rateBtn.setSize(GameAssets.fetchTexture("images/rate.png").getWidth(),GameAssets.fetchTexture("images/rate.png").getHeight());
		this.rateBtn.setPosition(1280f-400f-211f,350f);
		
		this.statusBar = new Sprite(GameAssets.fetchTexture("images/statusbar.png"));
		this.statusBar.setSize(GameAssets.fetchTexture("images/statusbar.png").getWidth(),GameAssets.fetchTexture("images/statusbar.png").getHeight());
		this.statusBar.setPosition(225f,0f);
		
		this.shareBtn = new Sprite(GameAssets.fetchTexture("images/share.png"));
		this.shareBtn.setSize(GameAssets.fetchTexture("images/share.png").getWidth(),GameAssets.fetchTexture("images/share.png").getHeight());
		this.shareBtn.setPosition(400f,350f);
			
		this.rightBtn = new Sprite(GameAssets.fetchTexture("images/btn_right.png"));
		this.rightBtn.setSize(GameAssets.fetchTexture("images/btn_right.png").getWidth(),GameAssets.fetchTexture("images/btn_right.png").getHeight());
		this.rightBtn.setPosition(1280f - 200f, 20f);
		
		this.jumpingDog = new AtlasAnimation(GameAssets.fetchTextureAtlas("images/dog_jump.pack"),"dog_jump_up",740f,0f,0f,0f,100f,100f, true, 7f, GameAssets.fetchSound("sounds/bark.ogg"),0.3f);
		
		if(this.deck == null) {
			if(GameScreen.gameMode == 1) this.deck = new CardDeck8(this);
			if(GameScreen.gameMode == 2) this.deck = new CardDeck18(this);
			if(GameScreen.gameMode == 3) this.deck = new CardDeck28(this);
		}
		this.deck.doAssets();
		
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
	

	@Override
	public boolean keyDown(int keycode) {
		if(GameScreen.paused == false) {
			if(keycode == Keys.BACK || keycode == Keys.ESCAPE || keycode == Keys.P) {
				GameScreen.paused = true;
				return true;
			}
		} else {
			if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
				this.dispose();
				this.game.setScreen(new TitleScreen(this.game));
			}
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
		
		if(GameScreen.paused == true) {
			if(shareBtn != null && shareBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
				this.shareButtonPressed();
			} else if(rateBtn != null && rateBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
				this.rateButtonPressed();
			} else if(rightBtn != null && rightBtn.getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
				GameScreen.paused = false;
			}
		}
		else {
			this.deck.queueTouch(touchPoint);
		}
		return false;
	}
	
	public void goVictory() {
		this.dispose();
		GameAssets.playSound(GameAssets.fetchSound("sounds/click.ogg"));
		GameAssets.loadGameAssets3();
		VictoryScreen vs = new VictoryScreen(this.game);
		this.game.setScreen(vs);
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
