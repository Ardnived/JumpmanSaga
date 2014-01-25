package sdp.ggj14.game.world;

import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.game.Level;

public class ForegroundTile extends Tile {
	
	public ForegroundTile(String sprite, int x, int y) {
		super(sprite, x, y);
		
		super.addFixture(new Rectangle(Level.GRID_SIZE, Level.GRID_SIZE));
	}

}
