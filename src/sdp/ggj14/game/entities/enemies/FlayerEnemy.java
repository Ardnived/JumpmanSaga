package sdp.ggj14.game.entities.enemies;

import java.util.HashMap;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.game.entities.Projectile;
import sdp.ggj14.util.Sprite;

public class FlayerEnemy extends Enemy {
	public static final double SPEED = 50;
	public static final int FLAYER_SIZE = (int) (16 * Level.SPRITE_SCALE);
	public static final double COOLDOWN = 1200;
	
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
	
	private double cooldown = 0;

	public FlayerEnemy(double x, double y, int limit) {
		super(x, y, FLAYER_SIZE, FLAYER_SIZE, SPRITES, limit);
		super.contactDamage = 2;
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		if (super.isPlayerActive(level.getPlayer())) {
			double direction = level.getPlayer().getX() - this.getX();
			
			if (direction > 0) {
				this.flipped = true;
			} else if (direction < 0) {
				this.flipped = false;
			}
			
			if ((this.getX() > originalPosition-limit && direction < 0) || (this.getX() < originalPosition && direction > 0)) {
				super.getLinearVelocity().set(Math.min(5/direction * SPEED * elapsedTime, direction * elapsedTime), 0);
			}
			else { super.getLinearVelocity().set(0, 0); }
		}

		if (level.getPlayer().getPowerUp() == PowerUp.Type.OXIDE) {
			cooldown -= elapsedTime;
		
			if (cooldown <= 0) {
				cooldown = COOLDOWN;
				
				int x = -24, y = 40;
				x += Math.random()*50 - 10;
				
				level.addBody(new Projectile(getX()+x, getY()-y, 1, -50, true));
			}
		}
	}

}
