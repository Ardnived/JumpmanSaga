package sdp.ggj14.util;

import java.awt.image.BufferedImage;

public class Sprite {
	public static String[] constructPaths(String prefix, int length, String suffix) {
		String[] paths = new String[length];
		for (int i = 0; i < length; i++) {
			String key;
			if (i < 10) {
				key = "0"+(i+1);
			} else {
				key = String.valueOf(i+1);
			}
			
			paths[i] = prefix+key+suffix;
		}
		
		return paths;
	}
	
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