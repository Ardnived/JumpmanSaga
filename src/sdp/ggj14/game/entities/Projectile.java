package sdp.ggj14.game.entities;

import org.dyn4j.dynamics.Body;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

public class Projectile extends Unit {
	public static final int PROJECTILE_SIZE = 8;
	
	public Projectile(double x, double y, int hp) {
		super(x, y, 8, 8, hp);
		super.sprite = new Sprite(new String[] {"/projectiles/player_default.png"}, 1);
		super.setGravityScale(0);
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		level.removeBody(this);
		return false;
	}

}
