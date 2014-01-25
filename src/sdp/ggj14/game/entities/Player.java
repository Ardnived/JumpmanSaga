package sdp.ggj14.game.entities;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	public static final double COOLDOWN = 800.0;
	
	public int availableProjectiles = 3;
	private double cooldown = 0;

	public Player() {
		super(100.0, 100.0, PLAYER_SIZE, PLAYER_SIZE, 100);
		super.sprite = new Sprite(new String[] {
				"/player/flying/loop_s01.png",
				"/player/flying/loop_s02.png",
				"/player/flying/loop_s03.png"}, 10);
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		System.out.println("Player collided with "+other.getClass());
		return true;
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		if (cooldown > 0) {
			cooldown -= elapsedTime;
		}
	}
	
	public void shoot(Level level) {
		if (cooldown <= 0.0 && availableProjectiles > 0) {
			Projectile projectile = new Projectile(this.getX() + PLAYER_SIZE*3/5, this.getY(), 1);
			
			projectile.applyForce(new Vector2(100000, 0));
			level.addBody(projectile);
			
			this.availableProjectiles -= 1;
			this.cooldown = COOLDOWN;
		}
	}

}
