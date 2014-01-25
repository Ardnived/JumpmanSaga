package sdp.ggj14.game.entities.enemies;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.util.Sprite;

public class SwayerEnemy extends Enemy {
	public static final double SPEED = 16;
	public static final int FLAYER_SIZE = (int) (20 * 48.0/16.0);
	
	private double time = 0;

	public SwayerEnemy(double x, double y) {
		super(x, y, 1);
		super.sprite = new Sprite(new String[] {
				"/aliens/fuzz/s10.png"}, 10);
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		time += 1;
		
		double direction = level.getPlayer().getX() - this.getX();
		direction = -1.0; // He only goes left.
		super.getLinearVelocity().set(Math.min(5/direction * SPEED * elapsedTime, direction * elapsedTime), 500*Math.sin(time/(5*Math.PI)));
	}
	
}
