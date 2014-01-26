package sdp.ggj14.game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;

public abstract class SagaBody extends Body {
	public int drawWidth, drawHeight;
	protected boolean flipped = false;
	
	public enum Category { ENEMY, PLAYER, TILE, MISC }
	
	public SagaBody(int drawWidth, int drawHeight) {
		super(1);
		
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		
		this.setLinearDamping(0.1);
		this.setAngularDamping(1);
	}
	
	public void update(Level level, double elapsedTime) {
		// Do Nothing
	}
	
	public abstract BufferedImage getSprite(Level level);
	
	public double getX() {
		return super.getWorldCenter().x;
	}
	
	public double getY() {
		return super.getWorldCenter().y;
	}
	
	public boolean getFlipped() {
		return this.flipped;
	}
	
	public boolean intersects(int x, int y, int width, int height) {
		return new Rectangle(x, y, width, height).intersects(new Rectangle((int) getX(), (int) getY(), drawWidth, drawHeight));
	}
}
