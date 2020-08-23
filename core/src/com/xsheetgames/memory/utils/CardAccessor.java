package com.xsheetgames.memory.utils;


import aurelienribon.tweenengine.TweenAccessor;

public class CardAccessor implements TweenAccessor<PlayCard>{
	
	public static final int SCALE_X = 1;
	public static final int SCALE_ROTATE = 2;

	@Override
	public int getValues(PlayCard target, int tweenType, float[] returnValues) {
		switch (tweenType) {
	        case SCALE_X:
		        returnValues[0] = target.frontSide.getScaleX();
		       	return 1;
	        case SCALE_ROTATE:
	        	returnValues[0] = target.frontSide.getScaleX();
	        	//returnValues[1] = target.frontSide.getRotation();
	        	return 2;
	        default: assert false; return -1;
	    }
	}

	@Override
	public void setValues(PlayCard target, int tweenType, float[] newValues) {
		switch (tweenType) {
	        case SCALE_X: 
				target.frontSide.setScale(newValues[0], 1f);
				target.backSide.setScale(newValues[0], 1f);
				break;
	        case SCALE_ROTATE:
	        	target.frontSide.setScale(newValues[0]);
	        	//target.frontSide.setRotation(newValues[1]);
	        	target.backSide.setScale(newValues[0]);
	        	//target.backSide.setRotation(newValues[1]);
	        	break;
			default: assert false; break;
		}
	}		
	

}
