package sdp.ggj14.game;

import java.util.ArrayList;

import sdp.ggj14.util.Sprite;

public class Player extends Unit {
	
	private Level level;
	public ArrayList<PlayerProjectile1> projectiles = new ArrayList<PlayerProjectile1>();
	private int maxProjectiles = 1;
	
	public Player(Level level) {
		super(10, 50, 50);
		this.level = level;
		//super.sprite = "/player/idle/s01.png";
		super.spriteObj = new Sprite(new String[] {"/player/idle/s01.png", "/player/idle/s02.png", "/player/idle/s03.png"}, 1);
	}
	
	public void shoot() {
		if (projectiles.size() < maxProjectiles) {
			projectiles.add(new PlayerProjectile1((int)fixture.getShape().getCenter().x, (int)fixture.getShape().getCenter().y, 1));
		}
	}

}
