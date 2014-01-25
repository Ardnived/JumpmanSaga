package sdp.ggj14.game.world;

import java.awt.image.BufferedImage;

import sdp.ggj14.util.ImageLoader;

public class Tile {
	boolean passable;
	String sprite;
	
	public Tile(String sprite) {
		this.sprite = sprite;
	}
	
	public BufferedImage getSprite() {
		return ImageLoader.get(this.sprite);
	}

}
