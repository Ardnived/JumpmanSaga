package sdp.ggj14.game.entities.enemies;

import org.dyn4j.geometry.Vector2;

import sdp.ggj14.game.entities.Enemy;
import sdp.ggj14.util.Sprite;

public class FlayerEnemy extends Enemy {
	public static final double SPEED = 20;
	public static final int FLAYER_SIZE = (int) (20 * 48.0/16.0);

	public FlayerEnemy(double x, double y) {
		super(x, y, 1);
		super.sprite = new Sprite(new String[] {
				"/aliens/flayer/s01.png",
				"/aliens/flayer/s02.png",
				"/aliens/flayer/s03.png",
				"/aliens/flayer/s04.png",
				"/aliens/flayer/s05.png"}, 10);
	}
	
	@Override
	public void update(double elapsedTime) {
		//int direction = 
		//super.applyForce(new Vector2(0, 0));
	}

}
