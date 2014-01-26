package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.Sprite;

import org.dyn4j.dynamics.Body;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	public static final int JETPACK_THRUST = 1000;
	public static final int HORIZONTAL_MOVE = 1000;
	
	public static final double AIR_DECAY = 0.01;
	public static final double FUEL_DECAY = 0.5;

	private static Map<PowerUp.Type, Sprite> IDLE = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FLYING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FALLING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> WALKING = new HashMap<PowerUp.Type, Sprite>();
	
	{
		IDLE.put(PowerUp.Type.DIODE, new Sprite(constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.OXIDE, new Sprite(constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.HELIX, new Sprite(constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.BOROS, new Sprite(constructPaths("/player/idle/s", 10, ".png"), 10));

		FLYING.put(PowerUp.Type.DIODE, new Sprite(constructPaths("/player/flying/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.OXIDE, new Sprite(constructPaths("/player/flyingBlue/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.HELIX, new Sprite(constructPaths("/player/flyingGreen/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.BOROS, new Sprite(constructPaths("/player/flying/loop_s", 3, ".png"), 10));

		FALLING.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));

		WALKING.put(PowerUp.Type.DIODE, new Sprite(constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.OXIDE, new Sprite(constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.HELIX, new Sprite(constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.BOROS, new Sprite(constructPaths("/player/walking/s", 8, ".png"), 10));
	}
	
	private static String[] constructPaths(String prefix, int length, String suffix) {
		String[] paths = new String[length];
		for (int i = 0; i < length; i++) {
			String key;
			if (i < 10) {
				key = "0"+(i+1);
			} else {
				key = String.valueOf(i+1);
			}
			
			paths[i] = prefix+key+suffix;
		}
		
		return paths;
	}
	
	private PowerUp.Type powerUp = PowerUp.Type.DIODE;
	private double powerUpTimer = 0;
	
	private double fuel = 100.0;
	private Map<PowerUp.Type, Sprite> spriteSet;

	public Player() {
		super(100.0, 100.0, PLAYER_SIZE, PLAYER_SIZE, 100);
		this.spriteSet = IDLE;
	}

	@Override
	public boolean onCollision(Level level, Body other) {
		if (other instanceof Tile && other.getWorldCenter().y > this.getY()) {
			this.onGround = true;
		}
		
		if (other instanceof Enemy || other instanceof Projectile) {
			hp--;
			if (hp < 0) hp = 0;
		}
		
		return true;
	}
	
	@Override
	public void move(double x, double y) {
		if (y < 0) {
			if (this.fuel > 0) {
				this.fuel -= FUEL_DECAY;
			} else {
				y = 0;
			}
		}
		
		super.move(x*HORIZONTAL_MOVE, y*JETPACK_THRUST);
	}
	
	public void jump() {
		
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		this.hp -= AIR_DECAY;
		
		if (powerUpTimer > 0) {
			powerUpTimer -= elapsedTime;
		}
		
		if (this.getLinearVelocity().y < 0) {
			this.onGround = false;
		}
		
		if (!onGround && super.getForce().y < 0) {
			this.spriteSet = FLYING;
		} else if (!onGround) {
			this.spriteSet = FALLING;
		} else if (onGround && super.getForce().x != 0) {
			this.spriteSet = WALKING;
		} else {
			this.spriteSet = IDLE;
		}
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return this.spriteSet.get(this.powerUp).getCurrentSprite();
	}
	
	public PowerUp.Type getPowerUp() {
		return powerUp;
	}
	
	public double getFuel() {
		return fuel;
	}

}
