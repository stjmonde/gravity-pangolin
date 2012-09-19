package fr.gravity.pangolin.entity.graphic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.gravity.pangolin.TextureLoader;
import fr.gravity.pangolin.TextureLoader.TextureId;
import fr.gravity.pangolin.entity.EntityGraphic;

public class GravityChangerBlockGraphic extends EntityGraphic {

	public GravityChangerBlockGraphic(float x, float y) {
		set(getSprite(), x, y);
	}

	private Sprite getSprite() {
		TextureRegion[] textureRegions = TextureLoader.getInstance().getTextureRegions(TextureId.GRAVITY_CHANGER);
		return new Sprite(textureRegions[0]);
	}
	
	@Override
	public void touchDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchDownOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchUpOut() {
		// TODO Auto-generated method stub
		
	}

}