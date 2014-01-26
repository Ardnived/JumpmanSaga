package sdp.ggj14.util;

import java.awt.image.BufferedImage;

public class Sprite {
	private String[] imagePaths;
	private int currentSprite;
	private double timePassed = 0;
	private int speed;
	
	public Sprite(String[] imagePaths, int speed) {
		this.imagePaths = imagePaths;
		this.speed = speed;
	}
	
	public void update(double elapsedTime){
		timePassed += elapsedTime;
		if (timePassed > 1000/speed) {
			currentSprite++;
			timePassed = 0;
			if (currentSprite > imagePaths.length-1) currentSprite = 0;
		}
	}
	
	public BufferedImage getCurrentSprite() {
		return ImageLoader.get(imagePaths[currentSprite]);
	}
	
	public int getSpriteIndex() {
		return this.currentSprite;
	}
}