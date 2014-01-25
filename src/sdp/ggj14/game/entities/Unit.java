package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.SagaBody;
import sdp.ggj14.util.Sprite;

public class Unit extends SagaBody {
	int hp;
	Sprite sprite;
	BodyFixture fixture;

	public Unit(double x, double y, int width, int height, int hp) {
		super(width, height);
		this.hp = hp;
		
		super.translate(x, y);
		
		fixture = super.addFixture(new Rectangle(width, height), BodyFixture.DEFAULT_DENSITY, BodyFixture.DEFAULT_FRICTION, BodyFixture.DEFAULT_RESTITUTION);
		
		super.setMass();
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
	
	public int getHP() {
		return this.hp;
	}
	
	@Override
	public BufferedImage getSprite() {
		return sprite.getCurrentSprite();
	}

}
