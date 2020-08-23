package com.xsheetgames.callbacks;

import com.xsheetgames.memory.utils.PlayCard;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class VanishCard implements TweenCallback {

	PlayCard card;
	
	public VanishCard(PlayCard card) {
		this.card = card;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		
		if(type == TweenCallback.COMPLETE) {
			card.setSolved(true);
		}
		
	}

}
