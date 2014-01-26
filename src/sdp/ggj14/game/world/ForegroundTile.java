package sdp.ggj14.game.world;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.SagaBody;
import sdp.ggj14.game.world.Tile.TypeSet;
import sdp.ggj14.util.ImageLoader;

public class ForegroundTile extends SagaBody implements Tile {
	Tile.Type type;
	
	public ForegroundTile(Tile.Type type, int x, int y) {
		super(Level.GRID_SIZE, Level.GRID_SIZE);
		this.type = type;
		
		BodyFixture fixture = super.addFixture(new Rectangle(Level.GRID_SIZE, Level.GRID_SIZE));
		fixture.setFilter(new CategoryFilter(Category.TILE.ordinal(), Category.PLAYER.ordinal()));
		
		super.translate(Level.GRID_SIZE*x + Level.GRID_SIZE/2, Level.GRID_SIZE*y + Level.GRID_SIZE/2);
	}
	
	@Override
	public BufferedImage getSprite(Level level) {
		if (this.type != null) {
			TypeSet typeSet = this.type.data.get(level.getPlayer().getPowerUp());
			
			Random random = new Random();
			String[] sprites = typeSet.sprites;
			int index = random.nextInt(sprites.length);
			
			return ImageLoader.get(sprites[index]);
		} else {
			return null;
		}
	}

}
