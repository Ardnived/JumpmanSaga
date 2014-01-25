package sdp.ggj14.game;

import java.util.ArrayList;

public class Player extends Unit {
	
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private int maxProjectiles = 1;
	
	public Player() {
		super(10, 50, 50);
		super.sprite = "/player/idle/s01.png";
	}
	
	public void shoot() {
		if (projectiles.size() < maxProjectiles) {
			projectiles.add(new Projectile((int)fixture.getShape().getCenter().x, (int)fixture.getShape().getCenter().y, 1));
		}
	}

}
