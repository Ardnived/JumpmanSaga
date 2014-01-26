package sdp.ggj14.game.entities;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.Force;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.Sprite;

public class Projectile extends Unit {
	public static final int PROJECTILE_WIDTH = 21;
	public static final int PROJECTILE_HEIGHT = 12;
	
	public static final double PROJECTILE_DAMAGE = 10.0;
	
	public Projectile(double x, double y, int hp) {
		super(x+10, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, hp, 1);
		super.sprite = new Sprite(new String[] {"/projectiles/slime.png"}, 1);
		super.setGravityScale(0);
		
		this.fixture.setSensor(true);
		//this.fixture.setFilter(new CategoryFilter(Category.MISC.ordinal(), Category.PLAYER.ordinal()));
		
		Force test = new Force(-50, 0) {
			@Override
			public boolean isComplete(double elapsedTime){
				return false;
			}
		};
		this.applyForce(test);
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
