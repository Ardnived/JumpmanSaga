package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import sdp.ggj14.util.ImageLoader;

public class Unit {
	int hp;
	int x, y;
	String sprite;

	public Unit(int x, int y, int hp) {
		this.hp = hp;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public BufferedImage getSprite() {
		return ImageLoader.get(this.sprite);
	}

}
