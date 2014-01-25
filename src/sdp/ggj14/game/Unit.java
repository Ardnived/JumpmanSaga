package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.Vector2f;

public class Unit {
	public static final float VELOCITY_DECAY = 0.2f; //aka air friction
	
	Vector2f velocity = new Vector2f();
	int hp;
	int x, y;
	String sprite;

	public Unit(int x, int y, int hp) {
		this.hp = hp;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		this.x += this.velocity.x;
		this.y += this.velocity.y;
		
		this.velocity.scale(VELOCITY_DECAY);
	}
	
	public void move(int x, int y) {
		this.velocity.x += x;
		this.velocity.y += y;
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
