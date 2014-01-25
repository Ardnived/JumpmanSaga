package sdp.ggj14.game;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private int maxProjectiles = 3;
	
	public Player() {
		super(100, 100, 50);
		super.sprite = "/player/idle/s01.png";
	}
	
	public void shoot(Level level) {
		if (projectiles.size() < maxProjectiles) {
			Projectile projectile = new Projectile(this.getX() + PLAYER_SIZE/2, this.getY(), 1);
			
			projectile.applyForce(new Vector2(30000, 0));
			projectiles.add(projectile);
			level.addBody(projectile);
		}
	}

}
