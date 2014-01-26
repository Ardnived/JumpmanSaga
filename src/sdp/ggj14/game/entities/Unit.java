package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.SagaBody;
import sdp.ggj14.util.Sprite;

public class Unit extends SagaBody {
	protected double hp;
	protected Sprite sprite;
	protected BodyFixture fixture;
	protected boolean onGround;

	public Unit(double x, double y, int width, int height, double hp) {
		this(x, y, width, height, hp, 100);
	}

	public Unit(double x, double y, int width, int height, double hp, int mass) {
		super(width, height);
		this.hp = hp;
		
		super.translate(x, y);
		
		fixture = super.addFixture(new Rectangle(width, height), BodyFixture.DEFAULT_DENSITY, 0.5, 0.0);
		
		super.setMass(new Mass(new Vector2(0, 0), mass, 80000));
	}
	
	public boolean onCollision(Level level, Body other) {
		return true;
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		if (!this.onGround) {
			move(0, 0.3);
		}
		
		if (sprite != null) {
			sprite.update(elapsedTime);	
		}
	}
	
	public void move(double x, double y) {
		super.applyForce(new Vector2(x, y).multiply(10000));
		/*
		System.out.println(super.getMass());
		System.out.println(super.getForce());
		System.out.println(super.getWorldCenter());
		*/
	}
	
	public double getHP() {
		return this.hp;
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return sprite.getCurrentSprite();
	}

}
