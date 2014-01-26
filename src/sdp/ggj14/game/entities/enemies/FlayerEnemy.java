package sdp.ggj14.game.entities.enemies;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.util.Sprite;

public class FlayerEnemy extends Enemy {
	public static final double SPEED = 50;
	public static final int FLAYER_SIZE = (int) (20 * 48.0/16.0);

	public FlayerEnemy(double x, double y) {
		super(x, y, 1);
		super.sprite = new Sprite(new String[] {
		"/aliens/snail/s06.png"}, 10);
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		double direction = level.getPlayer().getX() - this.getX();
		direction = -1.0; // He only goes left.
		super.getLinearVelocity().set(Math.min(5/direction * SPEED * elapsedTime, direction * elapsedTime), 0);
	}

}
