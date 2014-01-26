package sdp.ggj14.game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.Main;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.Player;
import sdp.ggj14.game.entities.Unit;
import sdp.ggj14.game.entities.enemies.FlayerEnemy;
import sdp.ggj14.game.entities.enemies.SwayerEnemy;
import sdp.ggj14.game.entities.enemies.SprayerEnemy;
import sdp.ggj14.game.world.ForegroundTile;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.SoundPlayer;

public class Level extends World implements CollisionListener {
	public final static int WIDTH = 50, HEIGHT = 11;
	public final static int GRID_SIZE = 32;
	public final static int SCROLL_OFFSET = 100;
	
	String background = "/level/landscape_pixelated.jpg";
	double parallaxFactor = 0.2;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid = new Tile[WIDTH][HEIGHT];

	public Level() {
		super(new AxisAlignedBounds(WIDTH * GRID_SIZE * 2, HEIGHT * GRID_SIZE * 2));
		
		super.setGravity(ZERO_GRAVITY);
		
		this.createTestLevel();
		
		this.player = new Player();
		super.addBody(this.player);
		
		this.addListener(this);
		
		SoundPlayer.playSound("/aud/effects/FingerprintSuccess.wav");
	}
	
	public void createTestLevel() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				this.setTile(x, y, null);
			}
			
			this.setTile(x, HEIGHT-1, new ForegroundTile("/tiles/ground/s01.png", x, HEIGHT-1));
		}
		
		for (int y = 0; y < grid[0].length; y++) {
			this.setTile(0, y, new ForegroundTile(null, 0, y));
			this.setTile(WIDTH-1, y, new ForegroundTile(null, WIDTH-1, y));
		}
		
		Rectangle roofRect = new Rectangle(1000.0, 10.0);
		Body roof = new Body();
		roof.addFixture(new BodyFixture(roofRect));
		roof.setMass(Mass.Type.INFINITE);
		this.addBody(roof);
		
		this.setTile(7, 3, new ForegroundTile("/tiles/ground/s06.png", 7, 3));
		
		//super.addBody(new PowerUp(PowerUp.Type.AIR, x, y));
		
		super.addBody(new FlayerEnemy(1000.0, GRID_SIZE * (HEIGHT - 2) + 8));
		super.addBody(new SwayerEnemy(1200.0, 200.0));
		super.addBody(new SprayerEnemy(600.0,GRID_SIZE * (HEIGHT - 2) + 8));
	}
	
	public boolean update(double elapsedTime) {
		for (Body body : super.getBodies()) {
			if (body instanceof SagaBody) {
				((SagaBody) body).update(this, elapsedTime);
			}
		}
		
		return super.update(elapsedTime);
	}
	
	public void paint(Graphics graphics) {
		graphics.drawImage(ImageLoader.get(background), (int) (0 - this.getScrollX()*parallaxFactor), 0, null);
		
		for (Body body : super.getBodies()) {
			if (body instanceof SagaBody) {
				SagaBody sagaBody = ((SagaBody) body);
				
				if (sagaBody.intersects(this.getScrollX(), 0, Main.SCREEN_WIDTH + GRID_SIZE, Main.SCREEN_HEIGHT)) {
					Image sprite = sagaBody.getSprite(this);
					
					if (sprite != null) {
						graphics.drawImage(sprite, (int) sagaBody.getX() - sagaBody.drawWidth/2 - this.getScrollX(), (int) sagaBody.getY() - sagaBody.drawHeight/2, sagaBody.drawWidth, sagaBody.drawHeight, null);
					}
				}
			}
		}

		// Draw Player / WHY IS THE LOOP NOT DRAWING HIM? IDK
		graphics.drawImage(player.getSprite(this), (int) player.getX() - player.drawWidth/2 - this.getScrollX(), (int) player.getY() - player.drawHeight/2, player.drawWidth, player.drawHeight, null);
	}
	
	public int getScrollX() {
		return Math.min(GRID_SIZE * WIDTH - Main.SCREEN_WIDTH - GRID_SIZE, Math.max((int) player.getX() - SCROLL_OFFSET, GRID_SIZE));
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (this.grid[x][y] != null) {
			this.removeBody(this.grid[x][y]);
		}
		
		this.grid[x][y] = tile;
		
		if (tile != null) {
			this.addBody(tile);
		}
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
