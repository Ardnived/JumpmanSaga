package sdp.ggj14.game.entities;

import org.dyn4j.dynamics.Body;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;


public class PowerUp extends Unit {
	enum Type {
		
	}
	
	public PowerUp(double x, double y, int hp) {
		super(x, y, 48, 48, hp);
		
		super.sprite = new Sprite(new String[] {
				"/collectables/artifact_s02.png"}, 1);
	}
	
	@Override
	public boolean onCollision(Level level, Body other) {
		if (other == level.getPlayer()) {
			level.removeBody(this);
		}
		
		return false;
	}

}
