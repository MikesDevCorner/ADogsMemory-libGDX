package com.xsheetgames.callbacks;

import com.xsheetgames.memory.GameAssets;
import com.xsheetgames.memory.utils.PlayCard;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class ShowCardNoise implements TweenCallback {

	PlayCard card;
	
	public ShowCardNoise(PlayCard card) {
		this.card = card;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		
		if(type == TweenCallback.COMPLETE) {
			card.changeUpfront();
			GameAssets.playSound(GameAssets.fetchSound("sounds/flip.ogg"));
		}
		
	}

}
