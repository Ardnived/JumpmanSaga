package sdp.ggj14.game.entities.enemies;

import java.util.HashMap;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.util.Sprite;

public class SwayerEnemy extends Enemy {
	public static final double SPEED = 16;
	public static final int SWAYER_SIZE = (int) (12 * Level.SPRITE_SCALE);
	
	public static HashMap<PowerUp.Type, Sprite> SPRITES = new HashMap<PowerUp.Type, Sprite>();
	
	{
		SPRITES.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/aliens/fuzz/s10.png"}, 5));
		SPRITES.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/items/new_life_form/s02.png"}, 5));
		SPRITES.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/aliens/fuzz/s10.png"}, 5));
		SPRITES.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/aliens/fuzz/s10.png"}, 5));
	}
	
	private double time = 0;

	public SwayerEnemy(double x, double y) {
		super(x, y, SWAYER_SIZE, SWAYER_SIZE, SPRITES);
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		if (super.isPlayerActive(level.getPlayer())) {
			time += 1;
			
			double direction = level.getPlayer().getX() - this.getX();
			direction = -1.0; // He only goes left.
			super.getLinearVelocity().set(Math.min(5/direction * SPEED * elapsedTime, direction * elapsedTime), 500*Math.sin(time/(5*Math.PI)));
		}
	}
	
}
