package fr.gravity.pangolin;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.gravity.pangolin.entity.Entity;
import fr.gravity.pangolin.entity.pangolin.Pangolin;
import fr.gravity.pangolin.entity.pangolin.Pangolin.Direction;
import fr.gravity.pangolin.util.GameUtil;

public class Controller {

	// enum Keys {
	// LEFT, RIGHT, UP, DOWN, GRAVITY_CHANGE
	// }

	private PangolinWorld pangolinWorld;
	private Pangolin pangolin;

	static Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();

	public Controller(PangolinWorld world, Pangolin pangolin) {
		this.pangolinWorld = world;
		this.pangolin = pangolin;
		initKeys();
	}

	// ** Key presses and touches **************** //

	public void initKeys() {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.SPACE, false);
	}

	public void keyDown(int keycode) {
		keys.get(keys.put(keycode, true));
	}

	public void keyUp(int keycode) {
		keys.get(keys.put(keycode, false));
	}

	/** The main update method **/
	public void update(float delta) {
		processInput();
		if (!isBlocked())
			controlPangolinMovement(delta);
	}

	/**
	 * Checks if the pangolin is actually blocked. If it is, the velocity is set
	 * so the pangolin gets out.
	 * 
	 * @return true if the pangolin is actually blocked, otherwise returns
	 *         false.
	 */
	private boolean isBlocked() {
		Entity collidedEntity = CollisionHelper.collidesAny(pangolin, pangolinWorld.getBlocks());
		final float GET_ME_OUT_RATE = 25F;

		boolean isBlocked = false;
		if (CollisionHelper.collidesLeft(pangolin, collidedEntity)) {
			pangolin.getVelocity().x = GET_ME_OUT_RATE;
			isBlocked = true;
		} else if (CollisionHelper.collidesRight(pangolin, collidedEntity)) {
			pangolin.getVelocity().x = -GET_ME_OUT_RATE;
			isBlocked = true;
		}

		if (CollisionHelper.collidesUp(pangolin, collidedEntity)) {
			pangolin.getVelocity().y = GET_ME_OUT_RATE;
			isBlocked = true;
		} else if (CollisionHelper.collidesDown(pangolin, collidedEntity)) {
			pangolin.getVelocity().y = -GET_ME_OUT_RATE;
			isBlocked = true;
		}
		return isBlocked;
	}

	private void controlPangolinMovement(float delta) {

		if (GameUtil.isOutOfScreen(pangolin.getBoundingRectangle()))
			GravityPangolinGame.getInstance().gameOver();

		pangolin.setLanded(false);

		Direction gravityDirection = pangolinWorld.getGravity().getDirection();
		// Control for the X axis
		{
			Vector2 positionCpy = pangolin.getPosition();
			Vector2 velocityCpy = pangolin.getVelocity().cpy();

			velocityCpy.set(pangolin.getVelocity().x, 0);
			pangolin.translate(velocityCpy.tmp().mul(delta));

			Entity collidedEntity = CollisionHelper.collidesAny(pangolin, pangolinWorld.getBlocks());
			if (collidedEntity != null)
				if (CollisionHelper.collidesLeft(pangolin, collidedEntity)) {
					pangolin.getVelocity().set(0, pangolin.getVelocity().y);
					positionCpy.x = collidedEntity.getX() + collidedEntity.getWidth();
//					pangolin.setPosition(new Vector2(pangolin.x, collidedEntity.y));
					if (gravityDirection == Direction.LEFT) {
						pangolin.setLanded(true);
					}
				} else if (CollisionHelper.collidesRight(pangolin, collidedEntity)) {
					pangolin.getVelocity().set(0, pangolin.getVelocity().y);
					positionCpy.x = collidedEntity.getX() - pangolin.getWidth();
//					pangolin.setPosition(new Vector2(pangolin.x + collidedEntity.width, collidedEntity.y));
					if (gravityDirection == Direction.RIGHT)
						pangolin.land(Direction.RIGHT);
				}
			pangolin.setPosition(positionCpy);
		}
		// Control for the Y axis
		{
			Vector2 positionCpy = pangolin.getPosition().cpy();
			Vector2 velocityCpy = pangolin.getVelocity().cpy();

			velocityCpy.set(0, pangolin.getVelocity().y);
			pangolin.translate(velocityCpy.tmp().mul(delta));

			Entity collidedEntity = CollisionHelper.collidesAny(pangolin, pangolinWorld.getBlocks());
			if (collidedEntity != null)
				if (CollisionHelper.collidesDown(pangolin, collidedEntity)) {
					pangolin.getVelocity().set(pangolin.getVelocity().x, 0);
					positionCpy.y = collidedEntity.getY() + collidedEntity.getHeight();
//					pangolin.setPosition(new Vector2(pangolin.x, collidedEntity.y + collidedEntity.height));
					if (gravityDirection == Direction.DOWN)
						pangolin.setLanded(true);
				} else if (CollisionHelper.collidesUp(pangolin, collidedEntity)) {
					pangolin.getVelocity().set(pangolin.getVelocity().x, 0);
					positionCpy.y = collidedEntity.getY() - pangolin.getHeight();
//					pangolin.setPosition(new Vector2(pangolin.x, collidedEntity.y));
					if (gravityDirection == Direction.UP)
						pangolin.setLanded(true);
				}
			pangolin.setPosition(positionCpy);
		}
	}

	/** Change Pangolin's state and parameters based on input controls **/
	private void processInput() {

		if (pangolin == null)
			return;

		pangolin.idle();

		if (keys.get(Keys.SPACE) && pangolin.isLanded()) {
			pangolinWorld.getGravity().switchDirection();
			// To avoid one frame of pangolin upDirection down (not falling)
			// when the
			// button is pressed
			pangolin.setLanded(false);
		}

		Direction gravityDirection = pangolinWorld.getGravity().getDirection();
		if (keys.get(Keys.LEFT) && gravityDirection != Direction.RIGHT && gravityDirection != Direction.LEFT) {
			pangolin.go(Direction.LEFT);
		} else if (gravityDirection == Direction.LEFT) {
			pangolin.fall(Direction.LEFT);
		}
		if (keys.get(Keys.RIGHT) && gravityDirection != Direction.LEFT && gravityDirection != Direction.RIGHT) {
			pangolin.go(Direction.RIGHT);
		} else if (gravityDirection == Direction.RIGHT) {
			pangolin.fall(Direction.RIGHT);
		}
		if (keys.get(Keys.UP) && gravityDirection != Direction.DOWN && gravityDirection != Direction.UP) {
			pangolin.go(Direction.UP);
		} else if (gravityDirection == Direction.UP) {
			pangolin.fall(Direction.UP);
		}
		if (keys.get(Keys.DOWN) && gravityDirection != Direction.UP && gravityDirection != Direction.DOWN) {
			pangolin.go(Direction.DOWN);
		} else if (gravityDirection == Direction.DOWN) {
			pangolin.fall(Direction.DOWN);
		}

	}
}
