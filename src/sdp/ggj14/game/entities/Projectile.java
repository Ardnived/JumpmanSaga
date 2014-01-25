package sdp.ggj14.game.entities;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

public class Projectile extends Unit {
	public static final int PROJECTILE_WIDTH = 24;
	public static final int PROJECTILE_HEIGHT = 9;
	
	public Projectile(double x, double y, int hp) {
		super(x+10, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, hp, 1);
		super.sprite = new Sprite(new String[] {"/projectiles/player.png"}, 1);
		super.setGravityScale(0);
		
		Force test = new Force(50, 0) {
			@Override
			public boolean isComplete(double elapsedTime){
				return false;
			}
		};
		this.applyForce(test);
	}
	
	@Override
	public void update(double elapsedTime) {
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		level.removeBody(this);
		level.getPlayer().availableProjectiles += 1;
		return false;
	}

}
