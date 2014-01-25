package sdp.ggj14.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.World;

public class Level extends World {
	final static int WIDTH = 30, HEIGHT = 10;
	final static int GRID_SIZE = 32;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[30][10];
	int scrollX = 0;

	public Level() {
		super(new AxisAlignedBounds(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
		super.setGravity(EARTH_GRAVITY);
		
		this.player = new Player(this);
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
	
	public boolean update(double elapsedTime) {
		this.player.update(elapsedTime);
		
		for (Enemy enemy : enemies) {
			enemy.update(elapsedTime);
		}
		
		return super.update(elapsedTime);
	}
	
	public void paint(Graphics graphics) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				graphics.drawImage(grid[x][y].getSprite(), x*GRID_SIZE - scrollX, y*GRID_SIZE, GRID_SIZE, GRID_SIZE, null);
			}
		}
		
		graphics.drawImage(player.getSprite(), (int) player.getCenter().x - 24, (int) player.getCenter().y - 24, 48, 48, null);
		for (int i = 0; i < player.projectiles.size(); i++) {
			Unit projectile = player.projectiles.get(i);
			graphics.drawImage(projectile.getSprite(), (int)projectile.getCenter().x-24, (int)projectile.getCenter().y-24, 48, 48, null);
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.grid[x][y] = tile;
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
