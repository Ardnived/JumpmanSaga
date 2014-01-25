package sdp.ggj14.game;

import java.awt.image.BufferedImage;

import org.dyn4j.dynamics.Body;

public abstract class SagaBody extends Body {
	public int drawWidth, drawHeight;
	
	public SagaBody(int drawWidth, int drawHeight) {
		super(1);
		
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
	}
	
	public abstract BufferedImage getSprite();
	
	public double getX() {
		return super.getWorldCenter().x;
	}
	
	public double getY() {
		return super.getWorldCenter().y;
	}
}