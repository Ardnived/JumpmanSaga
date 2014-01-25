package sdp.ggj14.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	final static int WIDTH = 30, HEIGHT = 10;
	final static int GRID_SIZE = 32;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[30][10];

	public Level() {
		this.player = new Player();
		this.createTestLevel();
	}
	
	public void createTestLevel() {
		Random random = new Random();
		String[] variations = new String[] { "_s07", "_s08", "_s09", "" };
		
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				this.setTile(x, y, new Tile("/tiles/BACKGROUND"+variations[random.nextInt(variations.length)]+".png"));
			}
		}
	}
	
	public void update() {
		this.player.update();
		
		for (Enemy enemy : enemies) {
			enemy.update();
		}
	}
	
	public void paint(Graphics graphics) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				graphics.drawImage(grid[x][y].getSprite(), x*GRID_SIZE, y*GRID_SIZE, GRID_SIZE, GRID_SIZE, null);
			}
		}
		
		graphics.drawImage(player.getSprite(), player.getX(), player.getY(), 48, 48, null);
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.grid[x][y] = tile;
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}

}
