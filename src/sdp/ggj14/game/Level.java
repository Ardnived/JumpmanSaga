package sdp.ggj14.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.Player;
import sdp.ggj14.game.entities.Unit;
import sdp.ggj14.game.world.BackgroundTile;
import sdp.ggj14.game.world.ForegroundTile;
import sdp.ggj14.game.world.Tile;

public class Level extends World {
	public final static int WIDTH = 30, HEIGHT = 10;
	public final static int GRID_SIZE = 32;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[30][10];
	int scrollX = 0;

	public Level() {
		super(new AxisAlignedBounds(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
		System.out.println(super.getBounds());
		//super.getBounds().shiftCoordinates(new Vector2(WIDTH * GRID_SIZE / 2, HEIGHT * GRID_SIZE / 2));
		
		super.setGravity(EARTH_GRAVITY.negate());
		
		this.player = new Player();
		super.addBody(this.player);
		
		this.createTestLevel();
	}
	
	public void createTestLevel() {
		Random random = new Random();
		String[] variations = new String[] { "_s07", "_s08", "_s09", "" };
		
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				this.setTile(x, y, new BackgroundTile("/tiles/BACKGROUND"+variations[random.nextInt(variations.length)]+".png"));
			}
			
			this.setTile(x, 5, new ForegroundTile("/tiles/s01.png", x, 5));
		}
		
		this.setTile(7, 3, new ForegroundTile("/tiles/s06.png", 7, 3));
	}
	
	public boolean update(double elapsedTime) {
		this.player.update(elapsedTime);
		
		for (Enemy enemy : enemies) {
			enemy.update(elapsedTime);
		}
		
		return super.update(elapsedTime);
	}
	
	public void paint(Graphics graphics) {
		// Draw Tiles
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				graphics.drawImage(grid[x][y].getSprite(), x*GRID_SIZE - scrollX, y*GRID_SIZE, GRID_SIZE, GRID_SIZE, null);
			}
		}
		
		// Draw Player
		graphics.drawImage(player.getSprite(), (int) player.getX() - Player.PLAYER_SIZE/2, (int) player.getY() - Player.PLAYER_SIZE/2, Player.PLAYER_SIZE, Player.PLAYER_SIZE, null);
		
		// Draw Projectiles
		for (int i = 0; i < player.projectiles.size(); i++) {
			Unit projectile = player.projectiles.get(i);
			graphics.drawImage(projectile.getSprite(), (int) projectile.getX() - 24, (int) projectile.getY() - 24, 48, 48, null);
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (this.grid[x][y] != null && this.grid[x][y] instanceof ForegroundTile) {
			this.removeBody(((ForegroundTile) this.grid[x][y]).getBody());
		}
		
		this.grid[x][y] = tile;

		if (tile instanceof ForegroundTile) {
			this.addBody(((ForegroundTile) tile).getBody());
		}
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
