package fr.gravity.pangolin.entity.graphic;

import fr.gravity.pangolin.helper.TextureHelper;
import fr.gravity.pangolin.helper.TextureHelper.TextureId;

public class WallBlockGraphic extends EntityGraphic {

	public WallBlockGraphic(float x, float y) {
		super(TextureHelper.getInstance().getSingleSprite(TextureId.WALL_BLOCK), x, y);
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
