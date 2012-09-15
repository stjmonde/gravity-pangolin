package fr.gravity.pangolin.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.gravity.pangolin.ScreenAbstract;
import fr.gravity.pangolin.util.GameUtil;

public abstract class EntityGraphic extends Sprite {

	protected float stateTime = 0;
	
//	public EntityGraphic(float x, float y) {
//		setPosition(x, y);
//	}
	
	/**
	 * Sets the sprite with position
	 * @param sprite
	 * @param x
	 * @param y
	 */
	public void set(Sprite sprite, float x, float y) {
		set(sprite);
		setPosition(x, y);
	}
	
	@Override
	public void set(Sprite sprite) {
		// Get a copy of the x and y values to reset it after setting the sprite
		// (setting a sprite resets the coordinates)
		float xCpy = getX();
		float yCpy = getY();
		
		// Set the sprite and its size
		super.set(sprite);
		ScreenAbstract screen = GameUtil.getScreen();
		setSize(Math.abs(sprite.getRegionWidth()) / screen.getPpuX(), Math.abs(sprite.getRegionHeight()) / screen.getPpuY());
		
		// Reset the coordinates
		setX(xCpy);
		setY(yCpy);
	}

	@Override
	public void setPosition(float x, float y) {
		setX(GameUtil.projectCoordinateX(x));
		setY(GameUtil.projectCoordinateY(y));
	}

//	@Override
//	public void setX(float x) {
//		super.setX(GameUtil.projectCoordinateX(x));
//	}
//
//	@Override
//	public void setY(float y) {
//		super.setY(GameUtil.projectCoordinateY(y));
//	}
//	
//	public void setXProjected(float x) {
//		super.setX(x);
//	}
//	
//	public void setYProjected(float y) {
//		super.setY(y);
//	}

	public void resetStateTime() {
		stateTime = 0;
	}
	
	public void addStateTime(float add) {
		stateTime += add;
	}
}