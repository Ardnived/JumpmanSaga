package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import sdp.ggj14.util.ImageLoader;

public class Tile {
	enum Sprite {
		
	}
	
	boolean passable;
	String sprite;
	
	public Tile(String sprite) {
		this(sprite, true);
	}
	
	public Tile(String sprite, boolean passable) {
		this.sprite = sprite;
		this.passable = passable;
	}
	
	public BufferedImage getSprite() {
		return ImageLoader.get(this.sprite);
	}

}
