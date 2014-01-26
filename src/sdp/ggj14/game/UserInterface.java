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
		
		graphics.drawImage(ImageLoader.get("/ui/air.png"), 13, 13, (int)(117*(air/100)), 9, null);
		graphics.drawImage(ImageLoader.get("/ui/bar.png"), 10, 10, 123, 15, null);

		graphics.drawImage(ImageLoader.get("/ui/fuel.png"), 33, 33, (int)(117*(fuel/100)), 9, null);
		graphics.drawImage(ImageLoader.get("/ui/bar.png"), 30, 30, 123, 15, null);
		
		if (level.getPlayer().getPowerUpTimer() > 0) {
			graphics.drawString("Powerup Timer: "+Math.round(level.getPlayer().getPowerUpTimer())+"ms", 10, 80);
		}
		
		String text;
		if (level.getShipLocation() >= level.getPlayer().getX()/Level.GRID_SIZE) {
			text = Math.round(level.getShipLocation() - level.getPlayer().getX()/Level.GRID_SIZE)+"m to the Ship!";
		} else {
			text = Math.round(level.getHelmLocation() - level.getPlayer().getX()/Level.GRID_SIZE)+"m to the Helm!";
		}
		graphics.drawString(text, 10, 100);
	}

}
