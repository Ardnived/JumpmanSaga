package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.Sprite;
import sdp.ggj14.util.Vector2f;

public class Unit extends Body {
	int hp;
	Sprite sprite;
	BodyFixture fixture;

	public Unit(double x, double y, int width, int height, int hp) {
		super(1);
		this.hp = hp;
		
		super.translate(x, y);
		
		fixture = super.addFixture(new Rectangle(width, height), BodyFixture.DEFAULT_DENSITY, BodyFixture.DEFAULT_FRICTION, BodyFixture.DEFAULT_RESTITUTION);
		
		super.setMass();
		System.out.println(x+" -> "+this.getX());
	}
	
	public boolean onCollision(Level level, Body other) {
		return true;
	}
	
	public void update(double elapsedTime) {
		sprite.update(elapsedTime);
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
		return sprite.getCurrentSprite();
	}

}
