package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.Sprite;
import sdp.ggj14.util.Vector2f;

public class Unit extends Body {
	public static final float VELOCITY_DECAY = 0.2f; //aka air friction
	public static final int WIDTH = 48, HEIGHT = 48;
	
	//Vector2f velocity = new Vector2f();
	int hp;
	//int x, y;
	//String sprite;
	Sprite spriteObj;
	BodyFixture fixture;

	public Unit(int x, int y, int hp) {
		super(1);
		this.hp = hp;
		//this.x = x;
		//this.y = y;
		
		fixture = super.addFixture(new Rectangle(WIDTH, HEIGHT));
		fixture.getShape().getCenter().x = x;
		fixture.getShape().getCenter().y = y;
		fixture.createMass();
	}
	
	public void update(double elapsedTime) {
		spriteObj.update(elapsedTime);
		//this.x += this.velocity.x;
		//this.y += this.velocity.y;
		
		//this.velocity.scale(VELOCITY_DECAY);
	}
	
	public void move(double x, double y) {
		System.out.println("test "+x+", "+y);
		super.applyImpulse(new Vector2(x, y));
		//this.velocity.x += x;
		//this.velocity.y += y;
		
		//System.out.println(this.fixture.);
	}
	
	public Vector2 getCenter() {
		return fixture.getShape().getCenter();
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public BufferedImage getSprite() {
		//return ImageLoader.get(this.sprite);
		return spriteObj.getCurrentSprite();
	}

}
