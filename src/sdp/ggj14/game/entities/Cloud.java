package sdp.ggj14.game.entities;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Circle;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.Sprite;

public class Cloud extends Unit {

	public Cloud(double x, double y) {
		this(x, y, 600);
	}
	
	public Cloud(double x, double y, int radius) {
		super(x, y, radius, radius, 1);
		
		super.fixture = super.addFixture(new Circle(radius));
		super.fixture.setSensor(true);
		
		super.sprite = new Sprite(new String[] {"/level/environment/cloud.png"}, 1);
	}
	
	@Override
	public boolean onCollisionStart(Level level, Body other) {
		if (other instanceof Player) {
			((Player) other).modifySpeed(1/2);
		}
		
		return true;
	}
	
	@Override
	public void onCollisionEnd(Level level, Body other) {
		if (other instanceof Player) {
			((Player) other).modifySpeed(2);
		}
	}

}
