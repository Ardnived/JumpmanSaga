package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;

import sdp.ggj14.Main;
import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;


public class Enemy extends Unit {
	public static final int ACTIVE_RANGE = Main.SCREEN_WIDTH + 100;
	
	protected HashMap<PowerUp.Type, Sprite> spriteData;
	protected double contactDamage = 1;

	public Enemy(double x, double y, int width, int height, HashMap<PowerUp.Type, Sprite> spriteData) {
		super(x, y, width, height, 1);
		
		this.spriteData = spriteData;
		this.fixture.setFilter(new CategoryFilter(Category.ENEMY.ordinal(), Category.PLAYER.ordinal()));
	}

	@Override
	public boolean onCollision(Level level, Body other) {
		if (other instanceof Player) {
			((Player) other).modifyHP(-1);
		}
		
		return true;
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return spriteData.get(level.getPlayer().getPowerUp()).getCurrentSprite();
	}
	
	@Override
	public Sprite getSpriteObject(Level level) {
		return this.spriteData.get(level.getPlayer().getPowerUp());
	}
	
	public boolean isPlayerActive(Player player) {
		return player.getX() > this.getX() - ACTIVE_RANGE;
	}

}
