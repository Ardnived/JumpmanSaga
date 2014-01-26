package sdp.ggj14.game;

import java.awt.Color;
import java.awt.Graphics;

import sdp.ggj14.util.ImageLoader;

public class UserInterface {
	
	private Level level;
	
	public UserInterface(Level level) {
		this.level = level;
	}
	
	public void paint(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		//graphics.drawString("Jumpman Saga v0.1", 15, 15);
		
		double air = level.getPlayer().getHP();
		double fuel = level.getPlayer().getFuel();
		
		graphics.drawImage(ImageLoader.get("/ui/airIcon.png"), 10, 8, 18, 18, null);
		graphics.drawImage(ImageLoader.get("/ui/air.png"), 33, 13, (int)(117*(air/100)), 9, null);
		graphics.drawImage(ImageLoader.get("/ui/airBar.png"), 30, 10, 123, 15, null);

		graphics.drawImage(ImageLoader.get("/ui/fuelIcon.png"), 10, 28, 18, 18, null);
		graphics.drawImage(ImageLoader.get("/ui/fuel.png"), 33, 33, (int)(117*(fuel/100)), 9, null);
		graphics.drawImage(ImageLoader.get("/ui/fuelBar.png"), 30, 30, 123, 15, null);
		
		if (level.getPlayer().getPowerUpTimer() > 0) {
			graphics.drawImage(ImageLoader.get("/ui/powerupBar.png"), 160, 13, (int)(1000*(level.getPlayer().getPowerUpTimer()/level.getPlayer().getPowerUp().duration)), 9, null);
		}
		
		String text;
		if (level.getShipLocation() >= level.getPlayer().getX()/Level.GRID_SIZE) {
			text = Math.round(level.getShipLocation() - level.getPlayer().getX()/Level.GRID_SIZE)+"m to the Ship!";
		} else {
			text = Math.round(level.getHelmLocation() - level.getPlayer().getX()/Level.GRID_SIZE)+"m to the Helm!";
		}
		
		graphics.drawString(text, 10, 65);
	}

}
