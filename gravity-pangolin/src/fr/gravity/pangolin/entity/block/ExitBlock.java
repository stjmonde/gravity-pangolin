package fr.gravity.pangolin.entity.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;

import fr.gravity.pangolin.entity.Entity;
import fr.gravity.pangolin.entity.graphic.ExitBlockGraphic;
import fr.gravity.pangolin.entity.pangolin.Pangolin;
import fr.gravity.pangolin.entity.pangolin.Pangolin.Direction;
import fr.gravity.pangolin.game.CountDown;
import fr.gravity.pangolin.game.GravityPangolinGame;
import fr.gravity.pangolin.world.PangolinWorld;

public class ExitBlock extends Entity {

	private static final int STOP_PERIOD = 1000;
	private CountDown countDown = new CountDown(STOP_PERIOD);

	private PangolinWorld pangolinWorld;
	private Direction direction = Direction.DOWN;

	public ExitBlock(PangolinWorld pangolinWorld, float x, float y, Direction direction) {
		super(pangolinWorld.getWorld(), x, y, 1);
		this.pangolinWorld = pangolinWorld;
	}

	@Override
	public void createGraphic(float x, float y) {
		direction = PangolinWorld.getInstance().getExitDirection();
		entityGraphic = new ExitBlockGraphic(x, y, direction);
	}

	@Override
	protected void createBody(World world, float x, float y) {
		EdgeShape edgeShape = new EdgeShape();
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KinematicBody;
		bd.position.set(x, y);
		
		FixtureDef def = new FixtureDef();
		def.shape = edgeShape;
		def.density = 1;
		def.filter.groupIndex = 99;

		if (direction == Direction.DOWN) {
			edgeShape.set(0, 0, 1, 0);
		}
		else if (direction == Direction.UP) {
			edgeShape.set(0, 1, 1, 1);
			bd.position.set(x, y + 1F);
		}
		else if (direction == Direction.LEFT)
			edgeShape.set(0, 0, 0, 1);
		else if (direction == Direction.RIGHT)
			edgeShape.set(1, 0, 1, 1);

		body = world.createBody(bd);
		body.createFixture(def);
		origin = new Vector2(0, 0);
		
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public void touchDown() {
	}

	@Override
	public void touchUp() {
	}

	// @Override
	// public void draw(SpriteBatch batch, float parentAlpha) {
	// entityGraphic.draw(batch);
	// }

	@Override
	public Actor hit(float x, float y) {
		return null;
	}

	// @Override
	// public Entity collides() {
	// Pangolin pangolin = pangolinWorld.getPangolin();
	// Direction gravityDirection = pangolinWorld.getGravity().getDirection();
	// if (gravityDirection != direction)
	// return null;
	//
	// if (!countDown.isStarted()) {
	// pangolin.disableController();
	// countDown.start();
	// return null;
	// }
	// FadeOut fadeOutAction = FadeOut.$(2f);
	// pangolin.action(fadeOutAction);
	// // if (!countDown.waitForIt())
	// // return null;
	//
	// GravityPangolinGame.getInstance().youWin();
	// return null;
	// }

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
