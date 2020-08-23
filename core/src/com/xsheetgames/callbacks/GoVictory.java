package com.xsheetgames.callbacks;

import com.xsheetgames.memory.screens.GameScreen;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class GoVictory implements TweenCallback {

	GameScreen gs;
	
	public GoVictory(GameScreen gameScreen) {
		this.gs = gameScreen;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		
		if(type == TweenCallback.START) {
			this.gs.setVictory = true;
		}
		
	}

}
