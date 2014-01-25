package sdp.ggj14.game;

import java.awt.Color;
import java.awt.Graphics;

import sdp.ggj14.util.ImageLoader;

public class UserInterface {
	
	public double air = 100;
	private Level level;
	
	public UserInterface(Level level) {
		this.level = level;
	}
	
	public void paint(Graphics graphics) {
		graphics.setColor(Color.WHITE);
		//graphics.drawString("Jumpman Saga v0.1", 15, 15);
		
		air = level.getPlayer().getHP();
		
		graphics.drawImage(ImageLoader.get("/ui/airbar.png"), 13, 13, (int)(117*(air/100)), 9, null);
		graphics.drawImage(ImageLoader.get("/ui/airbarcontainer.png"), 10, 10, 123, 15, null);
	}

}
