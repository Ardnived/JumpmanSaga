package sdp.ggj14.game.world;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import sdp.ggj14.game.Level;
import sdp.ggj14.game.entities.PowerUp;

public interface Tile {
	public static class TypeSet {
		String[] sprites;
		Color tint;
		
		TypeSet(String[] sprites) {
			this(sprites, null);
		}
		
		TypeSet(String[] sprites, Color tint) {
			this.sprites = sprites;
			this.tint = tint;
		}
	}
	
	public static enum Type {
		DIRT() { @Override public void setup() {
			this.data.put(PowerUp.Type.DIODE, new TypeSet(new String[] {"/tiles/ground/s01.png", "/tiles/ground/s02.png"}));
			this.data.put(PowerUp.Type.OXIDE, new TypeSet(new String[] {"/tiles/ground/s05.png", "/tiles/ground/s06.png"}));
			this.data.put(PowerUp.Type.HELIX, new TypeSet(new String[] {"/tiles/ground/s04.png"}));
			this.data.put(PowerUp.Type.BOROS, new TypeSet(new String[] {"/tiles/ground/s08.png"}));
		} },
		BACKGROUND() { @Override public void setup() {
			this.data.put(PowerUp.Type.DIODE, new TypeSet(new String[] {"/tiles/background/s03.png"}));
			this.data.put(PowerUp.Type.OXIDE, new TypeSet(new String[] {"/tiles/background/s04.png"}));
			this.data.put(PowerUp.Type.HELIX, new TypeSet(new String[] {"/tiles/background/s01.png"}));
			this.data.put(PowerUp.Type.BOROS, new TypeSet(new String[] {"/tiles/background/s02.png"}));
		} },
		CAVERN() { @Override public void setup() {
			this.data.put(PowerUp.Type.DIODE, new TypeSet(new String[] {"/tiles/cavern/s01.png"}));
			this.data.put(PowerUp.Type.OXIDE, new TypeSet(new String[] {"/tiles/cavern/s02.png"}));
			this.data.put(PowerUp.Type.HELIX, new TypeSet(new String[] {"/tiles/cavern/s04.png"}));
			this.data.put(PowerUp.Type.BOROS, new TypeSet(new String[] {"/tiles/cavern/s03.png"}));
		} },
		SHIP() { @Override public void setup() {
			this.data.put(PowerUp.Type.DIODE, new TypeSet(new String[] {"/tiles/ship/s02.png"}));
			this.data.put(PowerUp.Type.OXIDE, new TypeSet(new String[] {"/tiles/ship/s03.png"}));
			this.data.put(PowerUp.Type.HELIX, new TypeSet(new String[] {"/tiles/ship/s05.png"}, Color.BLUE));
			this.data.put(PowerUp.Type.BOROS, new TypeSet(new String[] {"/tiles/ship/s02.png"}, Color.GRAY));
		} },
		WALL() { @Override public void setup() {
			this.data.put(PowerUp.Type.DIODE, new TypeSet(new String[] {"/tiles/ship/s01.png"}));
			this.data.put(PowerUp.Type.OXIDE, new TypeSet(new String[] {"/tiles/ship/s04.png"}));
			this.data.put(PowerUp.Type.HELIX, new TypeSet(new String[] {"/tiles/ship/s06.png"}, Color.CYAN));
			this.data.put(PowerUp.Type.BOROS, new TypeSet(new String[] {"/tiles/ship/s07.png"}, Color.GRAY));
		} };
		
		public HashMap<PowerUp.Type, TypeSet> data = new HashMap<PowerUp.Type, TypeSet>();
		
		Type() {
			setup();
		}
		
		public void setup() {}
	}
	
	public abstract BufferedImage getSprite(Level level);

}
