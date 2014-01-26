package sdp.ggj14.game.entities.enemies;

import java.util.HashMap;

import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.game.entities.Projectile;
import sdp.ggj14.util.Sprite;

public class SprayerEnemy extends Enemy {
	public static final double SPEED = 14;
	public static final double HELIX_MULTIPLIER = 5;
	public static final int SPRAYER_SIZE = (int) (20 * Level.SPRITE_SCALE);
	public static final int COOLDOWN = 2000;
	
	public static HashMap<PowerUp.Type, Sprite> SPRITES = new HashMap<PowerUp.Type, Sprite>();
	
	{
		/*
		SPRITES.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/aliens/glob/s03.png"}, 10));
		SPRITES.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/aliens/glob/s03.png"}, 10));
		SPRITES.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/aliens/glob/s03.png"}, 10));
		SPRITES.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/aliens/glob/s03.png"}, 10));
		*/
		SPRITES.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
		SPRITES.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/aliens/flayer/s", 5, ".png"), 4));
	}
	
	private double cooldown = 0;

	public SprayerEnemy(double x, double y, int limit) {
		super(x, y, SPRAYER_SIZE, SPRAYER_SIZE, SPRITES, limit);
		super.contactDamage = 0.4;
		
		super.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		if (this.isPlayerActive(level.getPlayer())) {
			if (level.getPlayer().getPowerUp() == PowerUp.Type.HELIX) {
				cooldown -= elapsedTime * HELIX_MULTIPLIER;
			} else {
				cooldown -= elapsedTime;
			}
			
			if (cooldown <= 0) {
				cooldown = COOLDOWN;
				
				double speed = -50;
				int x = 40, y = 12;
				double direction = Math.signum(this.getX() - level.getPlayer().getX());
				
				if (level.getPlayer().getPowerUp() == PowerUp.Type.HELIX) {
					speed *= HELIX_MULTIPLIER*5;
					y += Math.random()*50 - 10;
				} else {
					direction = 1.0; // Only shoot left
				}

				if (direction > 0) {
					this.flipped = false;
				} else if (direction < 0) {
					this.flipped = true;
				}
				
				level.addBody(new Projectile(getX()-(x*direction), getY()+y, 1, speed*direction, false));
			}
		}
	}
	
}
