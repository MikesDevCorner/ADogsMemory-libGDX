package com.xsheetgames.memory.layouts;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.xsheetgames.callbacks.GoVictory;
import com.xsheetgames.callbacks.StartCardPresenting;
import com.xsheetgames.memory.GameAssets;
import com.xsheetgames.memory.screens.GameScreen;
import com.xsheetgames.memory.utils.PlayCard;

public abstract class AbstractCardLayout {

	public static int trys = 0;
	
	protected int DeckSize, scalingPercent;
	protected Array<PlayCard> cards;
	protected Array<String> cardDefinitions;
	private Array<PlayCard> cardPair;
	private float timespanPairSeparator = 0.75f;
	private float delayAfterVictory = 0.5f;
	private Array<Vector2> quedTouches;
	protected GameScreen gameScreen;
	protected float cardPresentedOpenTime;
	protected float cardPresentedOpenDelay;
	
	
	public AbstractCardLayout(GameScreen gs) {
		this.cardDefinitions = new Array<String>(14);
		this.cardDefinitions.add("card_bone");
	    this.cardDefinitions.add("card_bowl");
	    this.cardDefinitions.add("card_duck");
	    this.cardDefinitions.add("card_frisbee");
	    this.cardDefinitions.add("card_house");
	    this.cardDefinitions.add("card_hydrant");
	    this.cardDefinitions.add("card_stick");
	    this.cardDefinitions.add("card_teddy");
	    this.cardDefinitions.add("card_waterball");
	    this.cardDefinitions.add("card_hals");
	    this.cardDefinitions.add("card_post");
	    this.cardDefinitions.add("card_blume");
	    this.cardDefinitions.add("card_wurst");
	    this.cardDefinitions.add("card_dogge");
		    
		quedTouches = new Array<Vector2>(20);
		cardPair = new Array<PlayCard>(2);
		this.gameScreen = gs;
		AbstractCardLayout.trys = 0;
	}
	
	
	public abstract void drawCards(SpriteBatch batch, float delta);
	
	
	public void resetGraphics() {
		for(PlayCard card : cards) {
			card.resetGraphics();
		}
	}
	
	
	public void doGameLogic(float delta) {	
		
		if(GameScreen.paused == false) {
			GameAssets.tweenManager.update(delta);
		}
		
		
		if(this.gameScreen.disposed == false) {
			
			boolean everythingReady = true;
			for(PlayCard card : cards) {
				if(card.isEverythingFinished() == false) {
					everythingReady = false;
					break;
				}
			}
			
			if(everythingReady) {
				Vector2 nextGuess = null;
				if(quedTouches.size > 0) {
					nextGuess = quedTouches.first();
					quedTouches.removeIndex(0);
					for(PlayCard card : cards) {
						if(card.processTouch(nextGuess) == true) {
							this.cardPair.add(card);
							break;
						}
					}
				}
			}
			
			if(this.cardPair.size == 2) {
				if(this.cardPair.get(0).getOrderNumer() == this.cardPair.get(1).getOrderNumer()) {
					this.cardPair.get(0).vanishCard();
					this.cardPair.get(1).vanishCard();
					this.cardPair.get(0).setFinishCounter(this.timespanPairSeparator);
					GameAssets.playSound(GameAssets.fetchSound("sounds/success.ogg"));
				} else {
					this.cardPair.get(0).hideCard();
					this.cardPair.get(1).hideCard();
					this.cardPair.get(0).setFinishCounter(this.timespanPairSeparator);
					GameAssets.playSound(GameAssets.fetchSound("sounds/fail.ogg"));
				}
				this.cardPair.clear();
				AbstractCardLayout.trys++;
			}
			
			if(this.checkEveryCardSolved() == true) {
				GoVictory callback = new GoVictory(this.gameScreen);
				Tween.call(callback).delay(this.delayAfterVictory).start(GameAssets.tweenManager);
			}
		}		
	}
	
	
	public boolean checkEveryCardSolved() {
		boolean returnValue = true;
		for(PlayCard card : cards) {
			if(card.getSolved() == false) {
				returnValue = false;
			}
		}
		return returnValue;
	}
	
	
	public void doAssets() {
		for(PlayCard card : cards) {
			card.doAssets();
		}
	}
	
	public void resetDeck() {
		for(PlayCard card : cards) {
			card.resetCard();
		}
		this.cards.shuffle();
		this.quedTouches.clear();
		this.cardPair.clear();
	}

	
	public void fillCardArray() {
		this.cards = new Array<PlayCard>(18);
		this.cardDefinitions.shuffle();
		
		for(int i = 1; i<=this.DeckSize/2;i++) {
			PlayCard tmpCard = new PlayCard(this.cardDefinitions.get(i-1), i, this.scalingPercent,cardPresentedOpenTime);
			this.cards.add(tmpCard);
			tmpCard = new PlayCard(this.cardDefinitions.get(i-1), i, this.scalingPercent,cardPresentedOpenTime);
			this.cards.add(tmpCard);
		}
	}
	
	
	public void dispose() {
		for(PlayCard card : cards) {
			card.dispose();
		}
		this.cards = null;
		this.cardDefinitions = null;
	}
	
	
	public void presentDeck() {
		float delay = 0.0f;
		for(PlayCard card : cards) {
			StartCardPresenting callback = new StartCardPresenting(card);
			Tween.call(callback).delay(delay).start(GameAssets.tweenManager);
			delay += this.cardPresentedOpenDelay;
		}
	}
	
	
	public void queueTouch(Vector2 touchCoordinates) {
		quedTouches.add(touchCoordinates);
	}
}
