package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Mass;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.ImageLoader;


public class PowerUp extends Unit {
	public static final int POWERUP_SIZE = (int) (10 * Level.SPRITE_SCALE);
	
	public enum Type {
		DIODE(0, ""),
		HELIX(2500, "artifact/s06.png"),
		BOROS(5000, "new_life_form/s03.png"),
		OXIDE(3000, "artifact/s08.png");
		
		public int duration;
		String sprite;
		
		Type(int duration, String sprite) {
			this.sprite = "/items/"+sprite;
			this.duration = duration;
		}
	};
	
	Type type;
	
	public PowerUp(Type type, double x, double y) {
		super(x, y, POWERUP_SIZE, POWERUP_SIZE, 1);
		this.type = type;
		
		this.fixture.setSensor(true);
		this.fixture.setFilter(new CategoryFilter(Category.MISC.ordinal(), Category.PLAYER.ordinal()));
		
		this.setMass(Mass.Type.INFINITE);
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		if (other == level.getPlayer()) {
			level.getPlayer().setPowerUp(this.type);
			level.removeBody(this);
		}
		
		return false;
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		return ImageLoader.get(type.sprite);
	}

}
