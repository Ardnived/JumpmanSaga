package sdp.ggj14.game.world;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.SagaBody;
import sdp.ggj14.util.ImageLoader;

public class Tile extends SagaBody {
	String sprite;
	
	public Tile(String sprite, int x, int y) {
		super(Level.GRID_SIZE, Level.GRID_SIZE);
		this.sprite = sprite;
		
		super.translate(Level.GRID_SIZE*x + Level.GRID_SIZE/2, Level.GRID_SIZE*y + Level.GRID_SIZE/2);
	}
	
	@Override
	public BufferedImage getSprite() {
		return ImageLoader.get(this.sprite);
	}

}
