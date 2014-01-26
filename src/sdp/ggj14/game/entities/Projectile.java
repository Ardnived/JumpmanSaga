package sdp.ggj14.game.entities;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Force;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.Sprite;

public class Projectile extends Unit {
	public static final int PROJECTILE_WIDTH = 21;
	public static final int PROJECTILE_HEIGHT = 12;
	
	public static final double PROJECTILE_DAMAGE = 10.0;
	
	public Projectile(double x, double y, int hp, double speed, boolean up) {
		super(x+10, y, up ? PROJECTILE_HEIGHT : PROJECTILE_WIDTH, up ? PROJECTILE_WIDTH : PROJECTILE_HEIGHT, hp, 1);
		
		String path;
		
		if (up) {
			path = "/projectiles/slime_up.png";
		} else {
			path = "/projectiles/slime.png";
		}
		
		super.sprite = new Sprite(new String[] {path}, 1);
		super.setGravityScale(0);
		
		this.fixture.setSensor(true);
		//this.fixture.setFilter(new CategoryFilter(Category.MISC.ordinal(), Category.PLAYER.ordinal()));
		
		double dx = 0, dy = 0;
		
		if (up) {
			dy = speed;
		} else {
			dx = speed;
		}
		
		Force move = new Force(dx, dy) {
			@Override
			public boolean isComplete(double elapsedTime){
				return false;
			}
		};
		this.applyForce(move);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		if (other instanceof Player) {
			((Player) other).modifyHP(-PROJECTILE_DAMAGE);
		}
		
		if (other instanceof Player || other instanceof Tile) {
			level.removeBody(this);
		}
		
		return false;
	}

}
