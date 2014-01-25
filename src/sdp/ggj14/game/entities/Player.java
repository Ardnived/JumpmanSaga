package sdp.ggj14.game.entities;

import java.util.ArrayList;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

import org.dyn4j.geometry.Vector2;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	public static final double COOLDOWN = 800.0;
	
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private int maxProjectiles = 3;
	private double cooldown = 0;

	public Player() {
		super(100.0, 100.0, PLAYER_SIZE, PLAYER_SIZE, 50);
		super.sprite = new Sprite(new String[] {
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
	}
	
	@Override
	public void update(double elapsedTime) {
		super.update(elapsedTime);
		
		if (cooldown > 0) {
			cooldown -= elapsedTime;
		}
	}
	
	public void shoot(Level level) {
		if (cooldown <= 0.0 && projectiles.size() < maxProjectiles) {
			Projectile projectile = new Projectile(this.getX() + PLAYER_SIZE/2, this.getY(), 1);
			
			projectile.applyForce(new Vector2(30000, 0));
			projectiles.add(projectile);
			level.addBody(projectile);
			
			cooldown = COOLDOWN;
		}
	}

}
