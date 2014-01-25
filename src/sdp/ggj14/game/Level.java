package sdp.ggj14.game;

import java.util.ArrayList;

public class Level {
	final static int WIDTH = 30, HEIGHT = 10;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[30][10];

	public Level() {
		
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.grid[x][y] = tile;
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}

}
