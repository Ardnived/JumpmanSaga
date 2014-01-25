package sdp.ggj14.game.world;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.game.Level;

public class ForegroundTile extends Tile {
	Body body;

	public ForegroundTile(String sprite, int x, int y) {
		super(sprite);
		
		body = new Body();
		body.addFixture(new Rectangle(Level.GRID_SIZE, Level.GRID_SIZE));
		body.translate(Level.GRID_SIZE*x, Level.GRID_SIZE*y);
		//body.setMass();
	}
	
	public Body getBody() {
		return body;
	}

}
