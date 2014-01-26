package sdp.ggj14.game.entities;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.ImageLoader;


public class PowerUp extends Unit {
	public enum Type {
		DIODE(10000, "artifact/s02.png"),
		HELIX(10000, "artifact/s06.png"),
		BOROS(10000, "new_life_form/s03.png"),
		OXIDE(10000, "new_life_form/s08.png");
		
		public int duration;
		String sprite;
		
		Type(int duration, String sprite) {
			this.sprite = "/items/"+sprite;
			this.duration = duration;
		}
	};
	
	Type type;
	
	public PowerUp(Type type, double x, double y) {
		super(x, y, 48, 48, 1);
		this.type = type;
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
