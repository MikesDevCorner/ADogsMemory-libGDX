package com.xsheetgames.memory.utils;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xsheetgames.callbacks.HideCardNoise;
import com.xsheetgames.callbacks.ShowCardNoise;
import com.xsheetgames.callbacks.TurnCardSilent;
import com.xsheetgames.callbacks.VanishCard;
import com.xsheetgames.memory.GameAssets;

public class PlayCard {

	public Sprite frontSide;
	public Sprite backSide;
	private boolean frontSideUp;
	private boolean solved;
	private float x, y;
	private String frontGraphicString;
	private int orderNumber;
	private float finishCounter;
	private float finishCounterStart = 0.25f;
	
	private int scalingPercentage = 100;
	
	private float turnDuration = 0.5f;
	private float vanishDuration = 0.2f;
	private float timeSpanOpened = 0.87f;
	private float timeSpanPresentOpened = 1f;
	private float timeSpanWaitVanish = 0.87f;
	
	public PlayCard(String frontGraphic, int orderNumber, int scalingPercent, float timeSpanPresented) {
		this.x = 0;
		this.y = 0;
		this.frontGraphicString = frontGraphic;
		this.frontSideUp = false;
		this.solved = false;
		this.orderNumber = orderNumber;
		this.finishCounter = 0f;
		this.scalingPercentage = scalingPercent;
		this.timeSpanPresentOpened = timeSpanPresented;
	}
	
	
	public void setPosition(float x, float y) {
			if(this.x <= 0f) this.x = x;
			if(this.y <= 0f) this.y = y;
			
			float scalePositionDifferenceX = (frontSide.getRegionWidth() / 2) * (1f - frontSide.getScaleX()) * ( 1f - (this.scalingPercentage/100f));
			float scalePositionDifferenceY = (frontSide.getRegionHeight() / 2) * (1f - frontSide.getScaleY()) * ( 1f - (this.scalingPercentage/100f));
			
			frontSide.setBounds(this.x - scalePositionDifferenceX, this.y - scalePositionDifferenceY, frontSide.getRegionWidth()*this.scalingPercentage/100f, frontSide.getRegionHeight()*this.scalingPercentage/100f);
			backSide.setBounds(this.x - scalePositionDifferenceX, this.y - scalePositionDifferenceY, backSide.getRegionWidth()*this.scalingPercentage/100f, backSide.getRegionHeight()*this.scalingPercentage/100f);
	}
	
	public void setFinishCounter(float counter) {
		this.finishCounter = counter;
	}
	
	public void setSolved(boolean solved) {
		this.solved = solved;
	}
	
	public boolean getSolved() {
		return this.solved;
	}
	
	public void draw(SpriteBatch batch, float delta) {
		
		if(this.finishCounter > 0f) {
			this.finishCounter -= delta;
		}
		
		if(this.solved == false) {
			if(frontSideUp) {
				frontSide.draw(batch);
			} else {
				backSide.draw(batch);
			}
		}
	}
	
	public void resetGraphics() {
		this.frontSide = new Sprite(GameAssets.fetchTextureAtlas("images/cards.pack").findRegion(frontGraphicString));
		this.backSide = new Sprite(GameAssets.fetchTextureAtlas("images/cards.pack").findRegion("card_back"));
	}
	
	public void doAssets() {
		this.resetGraphics();
	}
	
	public void dispose() {
		this.frontSide = null;
		this.backSide = null;
	}
	
	public void resetCard() {
		this.solved = false;
		this.frontSideUp = false;
		this.finishCounter = 0f;
		this.x = 0f;
		this.y = 0f;
	}
	
	public int getOrderNumer() {
		return this.orderNumber;
	}
	
	public float getCardSize() {
		return this.frontSide.getRegionWidth()*this.scalingPercentage/100f;
	}
	
	public boolean isEverythingFinished() {
		return this.finishCounter <= 0f;
	}
	
	public boolean processTouch(Vector2 touchPoint) {		
		if(this.frontSide != null) {
			if(this.frontSideUp == false) {
				Rectangle rect = this.frontSide.getBoundingRectangle();
				rect.setX(this.x);
				rect.setY(this.y);
				
				if(rect.contains(touchPoint.x, touchPoint.y)) {
					this.showCard();
					this.finishCounter = finishCounterStart;
					return true;
				} else {
					return false;
				}
			} else return false;
		} else return false;
	}
	
	private void showCard() {
		Timeline.createSequence()
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/2).target(0f).ease(Quint.IN).setCallback(new ShowCardNoise(this)))
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/2).target(1f).ease(Quint.OUT))
	    .start(GameAssets.tweenManager);
	}
	
	public void hideCard() {
		Timeline.createSequence()
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/2).target(0f).ease(Quint.IN).setCallback(new HideCardNoise(this)).delay(this.timeSpanOpened))
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/2).target(1f).ease(Quint.OUT))
	    .start(GameAssets.tweenManager);
	}
	
	public void presentCard() {
		Timeline.createSequence()
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/4).target(0f).ease(Quint.IN).setCallback(new TurnCardSilent(this)))
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/4).target(1f).ease(Quint.OUT))
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/4).target(0f).ease(Quint.IN).setCallback(new TurnCardSilent(this)).delay(this.timeSpanPresentOpened))
	    .push(Tween.to(this, CardAccessor.SCALE_X, this.turnDuration/4).target(1f).ease(Quint.OUT))
	    .start(GameAssets.tweenManager);
	}
	
	
	public void vanishCard() {
		Timeline.createSequence()
	    .push(Tween.to(this, CardAccessor.SCALE_ROTATE, this.vanishDuration).target(0f, 120f).ease(Linear.INOUT).setCallback(new VanishCard(this)).delay(this.timeSpanWaitVanish))
	    .start(GameAssets.tweenManager);
	}
	
	
	
	public void changeUpfront() {
		this.frontSideUp = !this.frontSideUp;
	}
	
}
