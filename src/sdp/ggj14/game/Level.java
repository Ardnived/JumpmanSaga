package sdp.ggj14.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

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
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.game.entities.Unit;
import sdp.ggj14.game.world.ForegroundTile;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.JSONLoader;
import sdp.ggj14.util.SoundPlayer;

public class Level extends World implements CollisionListener {
	//public final static int WIDTH = 50, HEIGHT = 11;
	public final static int GRID_SIZE = 32;
	public final static int SCROLL_OFFSET = 200;
	
	double parallaxFactor = 0.15;
	
	int shipLocation;
	int helmLocation;
	
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Tile[][] grid;

	@SuppressWarnings("unchecked")
	public Level() {
		super.setGravity(ZERO_GRAVITY);

		Map<String, Object> data = JSONLoader.get(System.getProperty("user.dir")+"/dat/levels/Level01.json");
		String[] terrain = ((String) data.get("terrain")).split(":");
		this.grid = new Tile[terrain[0].length() + 2][terrain.length];
		
		this.createBounds();
		this.loadTiles(terrain);
		this.loadUnits((ArrayList<Map<String, String>>) data.get("units"));
		
		this.player = new Player();
		super.addBody(this.player);
		
		this.addListener(this);
		
		SoundPlayer.playSound("/aud/effects/FingerprintSuccess.wav");
	}
	
	public void createBounds() {
		super.setBounds(new AxisAlignedBounds(getWidth() * GRID_SIZE * 2, getHeight() * GRID_SIZE * 2));
		
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				this.setTile(x, y, null);
			}
			
			this.setTile(x, getHeight()-1, new ForegroundTile(Tile.Type.DIRT, x, getHeight()-1));
		}

		for (int y = 0; y < grid[0].length; y++) {
			this.setTile(0, y, new ForegroundTile(null, 0, y));
			this.setTile(getWidth()-1, y, new ForegroundTile(null, getWidth()-1, y));
		}

		Rectangle roofRect = new Rectangle(1000.0, 10.0);
		Body roof = new Body();
		roof.addFixture(new BodyFixture(roofRect));
		roof.setMass(Mass.Type.INFINITE);
		this.addBody(roof);
	}
	
	public void loadTiles(String[] terrain) {
		for (int y = 0; y < terrain.length; y++) {
			terrain[y] = terrain[y].trim();

			char[] symbols = terrain[y].toCharArray();
			for (int x = 0; x < symbols.length; x++) {
				switch (symbols[x]) {
				case 'Z':
					this.setTile(x+1, y, new ForegroundTile(Tile.Type.DIRT, x+1, y));
					break;
				case 'Y':
					this.setTile(x+1, y, new ForegroundTile(Tile.Type.SHIP, x+1, y));
					break;
				case 'A':
					this.setTile(x+1, y, new Tile(Tile.Type.BACKGROUND, x+1, y));
					break;
				case 'B':
					this.setTile(x+1, y, new Tile(Tile.Type.WALL, x+1, y));
					break;
				default:
					// Do Nothing
				}
			}
		}
	}
	
	public void loadUnits(ArrayList<Map<String, String>> units) {
		try {
			for (Map<String, String> unitData : units) {
				String className = unitData.get("class");
				double x = Double.parseDouble(unitData.get("x"));
				double y = Double.parseDouble(unitData.get("y"));
				Unit unit;
				
				switch (className) {
				case "PowerUp":
					unit = new PowerUp(PowerUp.Type.valueOf(unitData.get("type")), x, y);
					break;
				default:
					Class<?> cls = Class.forName("sdp.ggj14.game.entities."+className);
					unit = (Unit) cls.getDeclaredConstructor(new Class[] { double.class, double.class }).newInstance(x, y);;
					break;
				}
				
				this.addBody(unit);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Failed to load unit.");
			e.printStackTrace();
		}
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
		graphics.drawImage(this.getBackground(), (int) (0 - this.getScrollX()*parallaxFactor), 0, null);
		
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
	
	public BufferedImage getBackground() {
		String fileName;
		
		switch(this.getPlayer().getPowerUp()) {
		case DIODE:
			fileName = "landscape";
			break;
		case OXIDE:
			fileName = "swamp";
			break;
		case HELIX:
			fileName = "night";
			break;
		case BOROS:
			fileName = "divergence";
			break;
		default:
			fileName = "landscape";
		}
		
		return ImageLoader.get("/level/background/"+fileName+".jpg");
	}
	
	public int getScrollX() {
		return Math.min(GRID_SIZE * getWidth() - Main.SCREEN_WIDTH - GRID_SIZE, Math.max((int) player.getX() - SCROLL_OFFSET, GRID_SIZE));
	}
	
	public int getWidth() {
		return this.grid.length;
	}
	
	public int getHeight() {
		return this.grid[0].length;
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
