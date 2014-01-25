package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import javax.vecmath.Vector2f;

import sdp.ggj14.util.ImageLoader;

public class Unit {
	Vector2f velocity; // We may not actually use this.
	int hp;
	int x, y;
	String sprite;

	public Unit(int x, int y, int hp) {
		this.hp = hp;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.x += velocity.x;
		this.y += velocity.y;
	}
	
	public Vector2f getVelocity() {
		return this.velocity;
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
