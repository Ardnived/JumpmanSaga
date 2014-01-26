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
	public static final int AIR_CONTROL = 500;
	public static final int GROUND_CONTROL = 1000;
	
	public static final double AIR_DECAY = 0.01;
	public static final double FUEL_DECAY = 0.5;

	private static Map<PowerUp.Type, Sprite> IDLE = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FLYING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FALLING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> WALKING = new HashMap<PowerUp.Type, Sprite>();
	
	{
		IDLE.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/idle/s", 10, ".png"), 10));

		FLYING.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/flying/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/flyingBlue/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/flyingGreen/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/flying/loop_s", 3, ".png"), 10));

		FALLING.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));

		WALKING.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/walking/s", 8, ".png"), 10));
	}
	
	private PowerUp.Type powerUp = PowerUp.Type.DIODE;
	private double powerUpTimer = 0.0;
	
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
		
		if (other instanceof Enemy) {
			hp--;
		} else if (other instanceof Projectile) {
			hp -= 15;
		}
		
		if (hp < 0) hp = 0;
		
		return true;
	}
	
	@Override
	public void move(double x, double y) {
		if (y < 0) {
			if (this.fuel > 0) {
				this.fuel -= FUEL_DECAY;
				y *= JETPACK_THRUST;
			} else {
				y = 0;
			}
		}
		
		if (this.getLinearVelocity().y > 5 && 5 < this.getLinearVelocity().y) {
			if (this.fuel < 0) {
				x = 0;
			}
			
			x *= AIR_CONTROL;
		} else {
			x *= GROUND_CONTROL;
		}
		
		super.move(x, y);
	}
	
	public void jump() {
		
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		
		this.hp -= AIR_DECAY;
		
		if (powerUpTimer > 0) {
			powerUpTimer -= elapsedTime;
			
			if (powerUpTimer <= 0) {
				this.powerUp = PowerUp.Type.DIODE;
				this.powerUpTimer = 0.0;
			}
		}
		
		if (this.getLinearVelocity().y < 0) {
			this.onGround = false;
		}
		
		if (super.getForce().y < 0) {
			this.spriteSet = FLYING;
		} else if (super.getLinearVelocity().y > 0) {
			if (super.getForce().x != 0 && this.fuel > 0) {
				this.spriteSet = FLYING;
			} else {
				this.spriteSet = FALLING;
			}
		} else if (super.getForce().x != 0) {
			this.spriteSet = WALKING;
		} else {
			this.spriteSet = IDLE;
		}
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return this.spriteSet.get(this.powerUp).getCurrentSprite();
	}
	
	public void setPowerUp(PowerUp.Type powerUp) {
		this.powerUp = powerUp;
		this.powerUpTimer = powerUp.duration;
	}
	
	public PowerUp.Type getPowerUp() {
		return powerUp;
	}
	
	public double getPowerUpTimer() {
		return powerUpTimer;
	}
	
	public double getFuel() {
		return fuel;
	}

}
