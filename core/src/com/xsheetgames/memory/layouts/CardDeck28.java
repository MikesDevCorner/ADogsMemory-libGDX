package com.xsheetgames.memory.layouts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.screens.GameScreen;
import com.xsheetgames.memory.utils.PlayCard;


public class CardDeck28 extends AbstractCardLayout {
	
	float bottomDistance = 110f;
	float ySpacer = 20f;
	float leftDistance = 70f;
	float xSpacer = 0f;
	int cardsInRow = 7;
	
	
	public CardDeck28(GameScreen gs) {
		super(gs);	
		this.scalingPercent = 50;
		this.xSpacer = 0f;
	    this.DeckSize = 28;
	    this.cardPresentedOpenTime = 1.65f;
	    this.cardPresentedOpenDelay = 0.06f;
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
