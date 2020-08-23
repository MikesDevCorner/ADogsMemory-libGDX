package com.xsheetgames.memory.utils;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xsheetgames.memory.Configuration;
import com.xsheetgames.memory.screens.GameScreen;

public class AtlasAnimation {

	private Animation<TextureRegion> animation;
	private TextureRegion currentFrame;
	private float stateTime;
	private float renderX;
	private float renderY;
	private boolean animationRunning;
	private TextureAtlas atlas;
	private float velX, velY;
	private String animationName;
	private boolean looped;
	private float startDelay;
	private float actualDelay;
	private Sound audio;
	private boolean soundPlayed;
	private float audioDelay;
	private float audioStartDelay;
	
	
	public AtlasAnimation(TextureAtlas atlas, String animationName, float x, float y, float vx, float vy, float percentX, float percentY, boolean loop, float delay, Sound audio, float audioDelay) {
		this.atlas =atlas;
		this.renderX = x/* - (atlas.findRegions(animationName).get(0).getRegionWidth() / 100 * percentX)*/;
		this.renderY = y/* - (atlas.findRegions(animationName).get(0).getRegionHeight() / 100 * percentY)*/;
        this.animation = new Animation(1f/ 25f, this.atlas.findRegions(animationName), Animation.PlayMode.NORMAL);
        this.stateTime = 0f;
        this.animationRunning = true;
        this.velX = vx;
        this.velY = vy;
        this.animationName = animationName;
        this.looped = loop;
        this.startDelay = delay;
        this.actualDelay = delay;
        this.audio = audio;
        this.audioDelay = audioDelay;
        this.audioStartDelay = audioDelay;
	}
	
	
	public boolean isRunning() {
		return this.animationRunning;
	}
	
	
	public void resetGraphics(TextureAtlas atlas) {
		this.atlas = atlas;
		this.animation = new Animation(1f/ 25f, this.atlas.findRegions(this.animationName));
	}
	

	public void draw(float delta, SpriteBatch batch) {
		if(GameScreen.paused == false)
		{
			this.stateTime += delta;
			if(this.audioDelay >= 0f) this.audioDelay -= delta;
			this.renderX = this.renderX + (delta*this.velX);
			this.renderY = this.renderY + (delta*this.velY);
			
	        if(this.stateTime >= this.animation.getAnimationDuration())
	        {
	        	if(this.looped == false) {
	        		this.animationRunning = false;
	        	} else {
	        		if(this.actualDelay >= 0f) {
	        			this.actualDelay -= delta;
	        			this.stateTime -= delta;
	        		} else {
	        			this.stateTime = 0f;
	        			this.actualDelay = this.startDelay;
	        			this.audioDelay = this.audioStartDelay;
	        			this.soundPlayed = false;
	        		}
	        	}	        	
	        }
        }
		
		if(this.soundPlayed == false && this.audioDelay < 0f) {
			if(Configuration.soundEnabled == true) {
				if(this.audio != null) {
					this.audio.play();
					this.soundPlayed = true;
				}
			}
		}
        
        if(this.animationRunning) {
        	this.currentFrame = animation.getKeyFrame(this.stateTime, true);
            batch.draw(currentFrame, this.renderX, this.renderY, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        }
	}
	
	public void dispose() {
		this.animation = null;
		this.atlas = null;
		this.currentFrame = null;
	}
	
}
