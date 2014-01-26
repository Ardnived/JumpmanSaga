package sdp.ggj14.game.entities.enemies;

import java.util.HashMap;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.util.Sprite;

public class FlayerEnemy extends Enemy {
	public static final double SPEED = 50;
	public static final int FLAYER_SIZE = (int) (20 * Level.SPRITE_SCALE);
	
	public static HashMap<PowerUp.Type, Sprite> SPRITES = new HashMap<PowerUp.Type, Sprite>();
	
	{
		/*
		SPRITES.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/aliens/snail/s07.png"}, 10));
		SPRITES.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/aliens/snail/s07.png"}, 10));
		SPRITES.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/aliens/snail/s07.png"}, 10));
		SPRITES.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/aliens/snail/s07.png"}, 10));
		*/
		SPRITES.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
	}

	public FlayerEnemy(double x, double y) {
		super(x, y, FLAYER_SIZE, FLAYER_SIZE, SPRITES);
		super.contactDamage = 2;
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		if (super.isPlayerActive(level.getPlayer())) {
			double direction = level.getPlayer().getX() - this.getX();
			direction = -1.0; // He only goes left.
			super.getLinearVelocity().set(Math.min(5/direction * SPEED * elapsedTime, direction * elapsedTime), 0);
		}
	}

}
