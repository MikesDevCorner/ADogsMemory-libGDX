package com.xsheetgames.callbacks;

import com.xsheetgames.memory.utils.PlayCard;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class TurnCardSilent implements TweenCallback {

	PlayCard card;
	
	public TurnCardSilent(PlayCard card) {
		this.card = card;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		
		if(type == TweenCallback.COMPLETE) {
			card.changeUpfront();
			card.setFinishCounter(0.1f);
		}
		
	}

}
