package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.util.ImageLoader;

public class Unit extends Body {
	public static final float VELOCITY_DECAY = 0.2f; //aka air friction
	public static final int WIDTH = 48, HEIGHT = 48;
	
	//Vector2f velocity = new Vector2f();
	int hp;
	//int x, y;
	String sprite;
	BodyFixture fixture;

	public Unit(double x, double y, int hp) {
		super(1);
		this.hp = hp;
		
		super.translate(x, y);
		
		fixture = super.addFixture(new Rectangle(WIDTH, HEIGHT), BodyFixture.DEFAULT_DENSITY, BodyFixture.DEFAULT_FRICTION, BodyFixture.DEFAULT_RESTITUTION);
		
		super.setMass();
	}
	
	public void update() {
		// TODO: Stub
	}
	
	public void move(double x, double y) {
		super.applyForce(new Vector2(x, y).multiply(10000));
		/*
		System.out.println(super.getMass());
		System.out.println(super.getForce());
		System.out.println(super.getWorldCenter());
		*/
	}
	
	public double getX() {
		return super.getWorldCenter().x;
	}
	
	public double getY() {
		return super.getWorldCenter().y;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public BufferedImage getSprite() {
		return ImageLoader.get(this.sprite);
	}

}
