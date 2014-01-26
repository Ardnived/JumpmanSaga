package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.dyn4j.dynamics.Body;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.enemies.SwayerEnemy;
import sdp.ggj14.util.SoundPlayer;
import sdp.ggj14.util.Sprite;
import sun.audio.AudioPlayer;
import sun.audio.ContinuousAudioDataStream;

public class Player extends Unit {
	public static final int PLAYER_SIZE = 48;
	public static final int JETPACK_THRUST = 1500;
	public static final int AIR_CONTROL = 600;
	public static final int GROUND_CONTROL = 1000;
	
	public static final double BOROS_MULTIPLIER = 5.0;
	
	public static final double MAX_AIR = 100.0;
	public static final double MAX_FUEL = 100.0;
	
	public static final double AIR_DECAY = 0.001;
	public static final double FUEL_DECAY = 0.25;

	private static Map<PowerUp.Type, Sprite> IDLE = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FLYING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> FALLING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> WALKING = new HashMap<PowerUp.Type, Sprite>();
	private static Map<PowerUp.Type, Sprite> JUMPING = new HashMap<PowerUp.Type, Sprite>();
	
	{
		IDLE.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/idle/s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/idle/blue_s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/idle/green_s", 10, ".png"), 10));
		IDLE.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/idle/purple_s", 10, ".png"), 10));

		FLYING.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/flying/loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/flying/blue_loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/flying/green_loop_s", 3, ".png"), 10));
		FLYING.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/flying/purple_loop_s", 3, ".png"), 10));
		
		/*
		FALLING.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/player/flying/no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/player/flying/blue_no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/player/flying/green_no_fuel.png"}, 10));
		FALLING.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/player/flying/purple_no_fuel.png"}, 10));
		*/

		FALLING.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/player/fall/s01.png"}, 10));
		FALLING.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/player/fall/blue_s01.png"}, 10));
		FALLING.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/player/fall/green_s01.png"}, 10));
		FALLING.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/player/fall/purple_s01.png"}, 10));
		
		WALKING.put(PowerUp.Type.DIODE, new Sprite(Sprite.constructPaths("/player/walking/s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.OXIDE, new Sprite(Sprite.constructPaths("/player/walking/blue_s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.HELIX, new Sprite(Sprite.constructPaths("/player/walking/green_s", 8, ".png"), 10));
		WALKING.put(PowerUp.Type.BOROS, new Sprite(Sprite.constructPaths("/player/walking/purple_s", 8, ".png"), 10));

		JUMPING.put(PowerUp.Type.DIODE, new Sprite(new String[] {"/player/jump/s01.png"}, 10));
		JUMPING.put(PowerUp.Type.OXIDE, new Sprite(new String[] {"/player/jump/blue_s01.png"}, 10));
		JUMPING.put(PowerUp.Type.HELIX, new Sprite(new String[] {"/player/jump/green_s01.png"}, 10));
		JUMPING.put(PowerUp.Type.BOROS, new Sprite(new String[] {"/player/jump/purple_s01.png"}, 10));
	}
	
	private int fallingCounter = 0;
	
	private ContinuousAudioDataStream jetpackSound;
	private ContinuousAudioDataStream breathingSound;
	
	private PowerUp.Type powerUp = PowerUp.Type.DIODE;
	private double powerUpTimer = 0.0;
	private double gaspTimer = 0.0;
	
	private double speedModifier = 1.0;
	private double fuel = MAX_FUEL;
	private Map<PowerUp.Type, Sprite> spriteSet;

	public Player() {
		super(100.0, 100.0, PLAYER_SIZE, PLAYER_SIZE, MAX_AIR, 5);
		this.spriteSet = IDLE;
		breathingSound = SoundPlayer.play("/effects/jm_vo_calm_loop.wav", true);
	}
	
	@Override
	public void move(double x, double y) {
		if (y < 0) {
			if (this.fuel > 0) {
				this.modifyFuel(-FUEL_DECAY);
				y *= JETPACK_THRUST;
				if (jetpackSound == null){
					jetpackSound = SoundPlayer.play("/effects/jm_jetpack_loop.wav", true);
				}
			} else {
				y = 0;
				jetpackSound = null;
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
		
		if (x > 0) {
			flipped = false;
		} else if (x < 0) {
			flipped = true;
		}
		
		if (this.getPowerUp() == PowerUp.Type.BOROS) {
			x *= BOROS_MULTIPLIER;
			y *= BOROS_MULTIPLIER;
		}
		
		super.move(x * speedModifier, y * speedModifier);
		
		//System.out.println(super.getForce());
		//System.out.println(super.getLinearVelocity());
	}
	
	public void jump() {
		if (this.spriteSet == WALKING || this.spriteSet == IDLE) {
			super.move(0, -10000000);
		}
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		super.update(level, elapsedTime);
		gaspTimer += elapsedTime;
		
		this.speedModifier = Math.min(1.0, speedModifier + 0.03);
		
		double airLoss = -AIR_DECAY * elapsedTime;
		
		if (this.getPowerUp() == PowerUp.Type.BOROS) {
			airLoss *= BOROS_MULTIPLIER;
		}
		
		this.modifyHP(airLoss);
		
		if (powerUpTimer > 0.0) {
			powerUpTimer -= elapsedTime;
			
			if (powerUpTimer <= 0) {
				this.powerUp = PowerUp.Type.DIODE;
				this.powerUpTimer = 0.0;
			}
		}
		
		String test = "";
		
		if (super.getForce().y < 0) {
			this.spriteSet = FLYING;
			fallingCounter = 0;
			test = "FLYING";
		} else if (super.getLinearVelocity().y < -10) {
			this.spriteSet = JUMPING;
			fallingCounter = 0;
			test = "JUMPING";
			stopJetpack();
		} else if (super.getLinearVelocity().y > 0) {
			if (super.getForce().y < 0 && this.fuel > 0) {
				this.spriteSet = FLYING;
				fallingCounter = 0;
				test = "FLYING 2";
			} else if (super.getLinearVelocity().y > 2) {
				fallingCounter++;
				if (fallingCounter > 10) {
					this.spriteSet = FALLING;
					test = "FALLING";
					stopJetpack();
				}
			}
		} else if (super.getForce().x != 0) {
			this.spriteSet = WALKING;
			fallingCounter = 0;
			test = "WALKING";
			stopJetpack();
		} else {
			this.spriteSet = IDLE;
			fallingCounter = 0;
			test = "IDLE";
			stopJetpack();
		}
		
		//System.out.println(super.getLinearVelocity().toString()+" "+test);
	}
	
	private void stopJetpack() {
		if (jetpackSound != null){
			AudioPlayer.player.stop(jetpackSound);
			jetpackSound = null;
		}
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return this.spriteSet.get(this.powerUp).getCurrentSprite();
	}
	
	@Override
	public Sprite getSpriteObject(Level level) {
		return this.spriteSet.get(this.powerUp);
	}
	
	public void modifyHP(double mod) {
		hp = Math.min(Math.max(0, hp + mod), MAX_AIR);
		if (hp <= 0) {
			AudioPlayer.player.stop(breathingSound);
			breathingSound = null;
			SoundPlayer.play("/effects/jm_vo_death.wav", false);
		}
		
		if (mod > 0) {
			SoundPlayer.play("/effects/jm_ui_oxygen_pickup.wav", false);
		}
	}
	
	public void modifyFuel(double mod) {
		fuel = Math.min(Math.max(0, fuel + mod), MAX_FUEL);
		if (mod > 0) {
			SoundPlayer.play("/effects/jm_ui_fuel_pickup.wav", false);
		}
	}
	
	public void modifySpeed(double mod) {
		speedModifier *= mod;
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
	
	@Override
	public boolean onCollision(Level level, Body other) {
		if (other instanceof Enemy && gaspTimer >= 1000){
			if (other instanceof SwayerEnemy && this.getPowerUp() == PowerUp.Type.OXIDE) {
				return false;
			}
			SoundPlayer.play("/effects/jm_vo_gasp_0"+((int)(Math.random()*(5)+1))+".wav", false);
			gaspTimer = 0.0;
		}
		return false;
	}

}
