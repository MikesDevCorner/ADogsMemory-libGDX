package com.xsheetgames.callbacks;

import com.xsheetgames.memory.utils.PlayCard;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class StartCardPresenting implements TweenCallback {

	PlayCard card;
	
	public StartCardPresenting(PlayCard card) {
		this.card = card;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		if(type == TweenCallback.START) {
			card.presentCard();		
		}
	}

}
