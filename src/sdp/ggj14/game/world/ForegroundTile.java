package sdp.ggj14.game.world;

import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.game.Level;

public class ForegroundTile extends Tile {
	
	public ForegroundTile(Tile.Type type, int x, int y) {
		super(type, x, y);
		
		super.addFixture(new Rectangle(Level.GRID_SIZE, Level.GRID_SIZE));
	}

}
