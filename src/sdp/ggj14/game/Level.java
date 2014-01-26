package sdp.ggj14.game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.dynamics.Settings.ContinuousDetectionMode;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;

import sdp.ggj14.Main;
import sdp.ggj14.game.entities.Player;
import sdp.ggj14.game.entities.PowerUp;
import sdp.ggj14.game.entities.Unit;
import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.game.entities.enemies.*;
import sdp.ggj14.game.world.BackgroundTile;
import sdp.ggj14.game.world.ForegroundTile;
import sdp.ggj14.game.world.Tile;
import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.JSONLoader;

public class Level extends World implements CollisionListener {
	//public final static int WIDTH = 50, HEIGHT = 11;
	public final static int GRID_SIZE = 32;
	public final static int SCROLL_OFFSET = 200;
	public final static double SPRITE_SCALE = 48.0 / 16.0;
	
	double parallaxFactor = 0.15;
	
	int shipLocation;
	int helmLocation;
	
	Player player;
	Tile[][] grid;
	ArrayList<BackgroundTile> backgroundTiles = new ArrayList<BackgroundTile>();
	
	// HACK IN THE CLOUDS
	String cloud = "/level/environment/cloud.png";
	int cloudPos[] = new int[] {800};
	int cloudTop[] = new int[] {-50};
	int cloudWidth[] = new int[] {(900*3/4)};
	int cloudHeight[] = new int[] {(489*3/4)};
	// // HACK

	@SuppressWarnings("unchecked")
	public Level() {
		super.setGravity(EARTH_GRAVITY.negate().multiply(10));
		
		Settings settings = super.getSettings();
		settings.setContinuousDetectionMode(ContinuousDetectionMode.NONE);
		settings.setSleepLinearVelocity(0.1);
		settings.setSleepTime(0.2);
		settings.setPositionConstraintSolverIterations(5);
		settings.setVelocityConstraintSolverIterations(5);

		Map<String, Object> data = JSONLoader.get(System.getProperty("user.dir")+"/dat/levels/Level01.json");
		String[] terrain = ((String) data.get("terrain")).split(":");
		this.grid = new Tile[terrain[0].length() + 2][terrain.length];
		
		this.createBounds();
		this.loadTiles(terrain);
		this.loadUnits((ArrayList<Map<String, String>>) data.get("units"));
		
		this.player = new Player();
		super.addBody(this.player);
		
		this.addListener(this);
		
		this.shipLocation = this.getWidth();
		this.helmLocation = this.getWidth();
	}
	
	public void createBounds() {
		//super.setBounds(new AxisAlignedBounds(getWidth() * GRID_SIZE * 2, getHeight() * GRID_SIZE * 2));
		//super.setBounds(new AxisAlignedBounds(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT));
		super.setBounds(null);
		
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
					this.setTile(x+1, y, new BackgroundTile(Tile.Type.CAVERN, x+1, y));
					break;
				case 'B':
					this.setTile(x+1, y, new BackgroundTile(Tile.Type.WALL, x+1, y));
					
					if (x+1 < shipLocation) {
						this.shipLocation = x+1;
					}
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
				int limit = 500;
				if (unitData.get("limit")!=null) {
					limit = Integer.parseInt(unitData.get("limit"));
				}
				double x = Double.parseDouble(unitData.get("x"));
				double y = Double.parseDouble(unitData.get("y"));
				Unit unit;
				
				switch (className) {
				case "PowerUp":
					unit = new PowerUp(PowerUp.Type.valueOf(unitData.get("type")), x, y);
					break;
				case "enemies.FlayerEnemy":
					unit = new FlayerEnemy(x, y, limit);
					break;
				case "enemies.SprayerEnemy":
					unit = new SprayerEnemy(x, y, limit);
					break;
				case "enemies.SwayerEnemy":
					unit = new SwayerEnemy(x, y, limit);
					break;
				default:
					Class<?> cls = Class.forName("sdp.ggj14.game.entities."+className);
					unit = (Unit) cls.getDeclaredConstructor(new Class[] { double.class, double.class }).newInstance(x, y);
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
		//super.getBounds().shiftCoordinates(new Vector2(Main.SCREEN_WIDTH/2.0 + this.getScrollX(), -Main.SCREEN_HEIGHT/2.0));
		
		for (Body body : super.getBodies()) {
			if (body instanceof SagaBody) {
				((SagaBody) body).update(this, elapsedTime);
			}
		}
		
		for (int i = 0; i < this.cloudPos.length; i++) {
			Player player = this.getPlayer();
			if (this.cloudPos[i] - this.cloudWidth[i]/2 + 100 < player.getX() && player.getX() < this.cloudPos[i] + this.cloudWidth[i]/2 - 100
					&& this.cloudTop[i] + 100 > player.getY() && player.getY() < this.cloudTop[i] + this.cloudHeight[i] - 100) {
				if (player.getPowerUp() == PowerUp.Type.HELIX) {
					player.modifyFuel(Player.FUEL_DECAY);
				} else {
					player.modifySpeed(0.96);
				}
			}
		}
		
		return super.update(elapsedTime);
	}
	
	public void paint(Graphics graphics) {
		// Draw Background
		graphics.drawImage(this.getBackground(), (int) (0 - this.getScrollX()*parallaxFactor), 0, null);
		
		// Draw Background Tiles
		for (BackgroundTile tile : this.backgroundTiles) {
			graphics.drawImage(tile.getSprite(this), (int) tile.x * GRID_SIZE - this.getScrollX(), (int) tile.y * GRID_SIZE, GRID_SIZE, GRID_SIZE, null);
		}
		
		// Draw Physics Objects
		for (Body body : super.getBodies()) {
			if (body instanceof SagaBody) {
				SagaBody sagaBody = ((SagaBody) body);
				
				if (sagaBody.intersects(this.getScrollX(), 0, Main.SCREEN_WIDTH + GRID_SIZE, Main.SCREEN_HEIGHT)) {
					BufferedImage sprite = sagaBody.getSprite(this);
					
					if (sprite != null) {
						int x = (int) sagaBody.getX() - sagaBody.drawWidth/2 - this.getScrollX();
						int y = (int) sagaBody.getY() - sagaBody.drawHeight/2;
						
						if (body instanceof Unit) {
							x -= Unit.COLLISION_MARGIN/2;
							y -= Unit.COLLISION_MARGIN/2;
						}
						
						if (sagaBody.getFlipped()) {
							AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
							tx.translate(-sprite.getWidth(null), 0);
							AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
							sprite = op.filter(sprite, null);
						}
						
						graphics.drawImage(sprite, x, y, sagaBody.drawWidth, sagaBody.drawHeight, null);
					}
				}
			}
		}
		
		// Draw Clouds
		for (int i = 0; i < this.cloudPos.length; i++) {
			graphics.drawImage(ImageLoader.get(cloud), (int) (this.cloudPos[i] - this.cloudWidth[i]/2 - (this.getScrollX()*0.9)), this.cloudTop[i], this.cloudWidth[i], this.cloudHeight[i], null);
		}
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
	
	public void setTile(int x, int y, Tile newTile) {
		Tile oldTile = this.grid[x][y];
		
		if (oldTile != null) {
			if (oldTile instanceof BackgroundTile) {
				this.backgroundTiles.remove(oldTile);
			} else if (oldTile instanceof ForegroundTile) {
				this.removeBody((ForegroundTile) oldTile);
			}
		}
		
		this.grid[x][y] = newTile;

		if (newTile != null) {
			if (newTile instanceof BackgroundTile) {
				this.backgroundTiles.add((BackgroundTile) newTile);
			} else if (newTile instanceof ForegroundTile) {
				this.addBody((ForegroundTile) newTile);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		return this.grid[x][y];
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getShipLocation() {
		return this.shipLocation;
	}
	
	public int getHelmLocation() {
		return this.helmLocation;
	}

	@Override
	public boolean collision(ContactConstraint arg0) {
		return true;
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
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2, BodyFixture arg3, Penetration arg4) {
		return true;
	}

	@Override
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2, BodyFixture arg3, Manifold arg4) {
		return true;
	}

}
