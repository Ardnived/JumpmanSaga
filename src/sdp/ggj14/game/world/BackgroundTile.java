package sdp.ggj14.game.world;

import java.awt.image.BufferedImage;
import java.util.Random;

import sdp.ggj14.game.Level;
import sdp.ggj14.util.ImageLoader;

public class BackgroundTile implements Tile {
	Tile.Type type;
	public double x, y;
	
	public BackgroundTile(Tile.Type type, double x, double y) {
		this.type = type;
		this.x = x;
		this.y = y;
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
