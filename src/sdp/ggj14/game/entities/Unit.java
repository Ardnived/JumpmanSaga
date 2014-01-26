package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.SagaBody;
import sdp.ggj14.util.Sprite;

public class Unit extends SagaBody {
	public static final int COLLISION_MARGIN = 10;
	public static final int FORCE_MULTIPLIER = 1;
	
	protected double hp;
	protected Sprite sprite;
	protected BodyFixture fixture;
	
	public Unit(double x, double y, int width, int height, double hp) {
		this(x, y, width, height, hp, 10);
	}

	public Unit(double x, double y, int width, int height, double hp, int mass) {
		super(width, height);
		this.hp = hp;
		
		super.translate(x, y);
		
		fixture = super.addFixture(new Rectangle(width - COLLISION_MARGIN, height - COLLISION_MARGIN), BodyFixture.DEFAULT_DENSITY, 0.5, 0.0);
		
		super.setMass(new Mass(new Vector2(0, 0), mass, 80000));
	}
	
	public boolean onCollision(Level level, Body other) {
		return true;
	}
	
	public boolean onCollisionStart(Level level, Body other) {
		return true;
	}
	
	public void onCollisionEnd(Level level, Body other) {
		// Do Nothing
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		//move(0, 10*elapsedTime);
		
		if (this.getSpriteObject(level) != null) {
			this.getSpriteObject(level).update(elapsedTime);	
		}
	}
	
	public void move(double x, double y) {
		super.applyForce(new Force(x*FORCE_MULTIPLIER, y*FORCE_MULTIPLIER));
	}
	
	public void move(double x, double y, final double duration) {
		super.applyForce(new Force(x*FORCE_MULTIPLIER, y*FORCE_MULTIPLIER) {
			double time = duration;
			
			@Override
			public boolean isComplete(double elapsedTime) {
				System.out.println("Complete? "+time);
				time -= elapsedTime;
				return time <= 0;
			}
		});
	}
	
	public double getHP() {
		return this.hp;
	}
	
	public Sprite getSpriteObject(Level level) {
		return this.sprite;
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return sprite.getCurrentSprite();
	}

}
