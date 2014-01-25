package sdp.ggj14.game.entities;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	public static final int JETPACK_THRUST = 1000;
	public static final int HORIZONTAL_MOVE = 1000;
	
	public static final double COOLDOWN = 800.0;

	private static final Sprite IDLE = new Sprite(new String[] {
		"/player/idle/s01.png",
		"/player/idle/s02.png",
		"/player/idle/s03.png",
		"/player/idle/s04.png",
		"/player/idle/s05.png",
		"/player/idle/s06.png",
		"/player/idle/s07.png",
		"/player/idle/s08.png",
		"/player/idle/s09.png",
		"/player/idle/s10.png"}, 10);
	private static final Sprite FLYING = new Sprite(new String[] {
		"/player/flying/loop_s01.png",
		"/player/flying/loop_s02.png",
		"/player/flying/loop_s03.png"}, 10);
	private static final Sprite FALLING = new Sprite(new String[] {
		"/player/flying/no_fuel.png"}, 10);
	private static final Sprite WALKING = new Sprite(new String[] {
		"/player/walking/s01.png",
		"/player/walking/s02.png",
		"/player/walking/s03.png",
		"/player/walking/s04.png",
		"/player/walking/s05.png",
		"/player/walking/s06.png",
		"/player/walking/s07.png",
		"/player/walking/s08.png"}, 10);
	
	public int availableProjectiles = 3;
	private double cooldown = 0;

	public Player() {
		super(100.0, 100.0, PLAYER_SIZE, PLAYER_SIZE, 100);
		super.sprite = IDLE;
	}
	
	@Override
	public void move(double x, double y) {
		super.move(x*HORIZONTAL_MOVE, y*JETPACK_THRUST);
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		System.out.println("Player collided with "+other.getClass());
		return true;
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		if (cooldown > 0) {
			cooldown -= elapsedTime;
		}
		
		if (super.getForce().y > 0) {
			
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
