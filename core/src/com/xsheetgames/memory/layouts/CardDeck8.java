package com.xsheetgames.memory.layouts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.screens.GameScreen;
import com.xsheetgames.memory.utils.PlayCard;


public class CardDeck8 extends AbstractCardLayout {
	
	float bottomDistance = 130f;
	float ySpacer = 40f;
	float leftDistance = 70f;
	float xSpacer = 0f;
	int cardsInRow = 4;
	
	
	public CardDeck8(GameScreen gs) {
		super(gs);	
		this.scalingPercent = 90;
		this.xSpacer = 0f;
	    this.DeckSize = 8;
	    this.cardPresentedOpenTime = 0.65f;
	    this.cardPresentedOpenDelay = 0.087f;
	    this.fillCardArray();
	}
	
	
	@Override
	public void drawCards(SpriteBatch batch, float delta) {
		if(this.gameScreen.disposed == false) {
			int i = 0;
			for(PlayCard card : cards) {
				this.doPositionLogic(i, card);
				card.draw(batch, delta);
				i++;
			}
		}
	}
	
	
	public void doPositionLogic(int cardNumber, PlayCard card) {
		float actualX=0, actualY=0;
		if(this.xSpacer == 0f) {
			this.xSpacer = (Configuration.TARGET_WIDTH - (2*this.leftDistance) - (this.cardsInRow*card.getCardSize())) / (this.cardsInRow-1);
		}
		
		int numberInRow = cardNumber%this.cardsInRow;
		actualX = leftDistance + (numberInRow * (card.getCardSize() + this.xSpacer));
		
		int numberInColumn = cardNumber / this.cardsInRow;
		actualY = bottomDistance + (numberInColumn * (card.getCardSize() + this.ySpacer));
		
		card.setPosition(actualX, actualY);
	}
}
