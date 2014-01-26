package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import sdp.ggj14.Main;
import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;


public class Enemy extends Unit {
	public static final int ACTIVE_RANGE = Main.SCREEN_WIDTH + 100;
	
	protected HashMap<PowerUp.Type, Sprite> spriteData;

	public Enemy(double x, double y, int width, int height, HashMap<PowerUp.Type, Sprite> spriteData) {
		super(x, y, width, height, 1);
		
		this.spriteData = spriteData;
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return spriteData.get(level.getPlayer().getPowerUp()).getCurrentSprite();
	}
	
	public boolean isPlayerActive(Player player) {
		return player.getX() > this.getX() - ACTIVE_RANGE;
	}

}
