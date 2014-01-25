package sdp.ggj14.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactConstraint;

import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.Player;
import sdp.ggj14.game.entities.Unit;
import sdp.ggj14.game.world.ForegroundTile;
import sdp.ggj14.game.world.Tile;

public class Level extends World implements CollisionListener {
	public final static int WIDTH = 30, HEIGHT = 10;
	public final static int GRID_SIZE = 32;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[30][10];
	int scrollX = 0;

	public Level() {
		super(new AxisAlignedBounds(WIDTH * GRID_SIZE * 2, HEIGHT * GRID_SIZE * 2));
		
		super.setGravity(EARTH_GRAVITY.negate());
		
		this.player = new Player();
		super.addBody(this.player);
		
		this.createTestLevel();
		
		this.addListener(this);
	}
	
	public void createTestLevel() {
		Random random = new Random();
		String[] variations = new String[] { "_s07", "_s08", "_s09", "" };
		
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				this.setTile(x, y, new Tile("/tiles/BACKGROUND"+variations[random.nextInt(variations.length)]+".png", x, y));
			}
			
			this.setTile(x, 5, new ForegroundTile("/tiles/s01.png", x, 5));
		}
		
		for (int y = 0; y < grid[0].length; y++) {
			this.setTile(0, y, new ForegroundTile("/tiles/s03.png", 0, y));
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
		for (Body body : super.getBodies()) {
			if (body instanceof SagaBody) {
				SagaBody sagaBody = ((SagaBody) body);
				graphics.drawImage(sagaBody.getSprite(), (int) sagaBody.getX() - sagaBody.drawWidth/2 - scrollX, (int) sagaBody.getY() - sagaBody.drawHeight/2, sagaBody.drawWidth, sagaBody.drawHeight, null);
			}
		}

		// Draw Player / WHY IS THE LOOP NOT DRAWING HIM? IDK
		graphics.drawImage(player.getSprite(), (int) player.getX() - player.drawWidth/2 - scrollX, (int) player.getY() - player.drawHeight/2, player.drawWidth, player.drawHeight, null);
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.removeBody(this.grid[x][y]);
		this.grid[x][y] = tile;
		this.addBody(tile);
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}
	
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean collision(Body arg0, Body arg1) {
		boolean cancel = false;
		
		if (arg0 instanceof Unit) {
			cancel &= ((Unit) arg0).onCollision(this, arg1);
		}
		
		if (arg1 instanceof Unit) {
			cancel &= ((Unit) arg1).onCollision(this, arg0);
		}
		
		return !cancel;
	}

	@Override
	public boolean collision(ContactConstraint arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2,
			BodyFixture arg3, Penetration arg4) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2,
			BodyFixture arg3, Manifold arg4) {
		// TODO Auto-generated method stub
		return true;
	}

}
