package sdp.ggj14;

import java.awt.Graphics;
import javax.swing.JFrame;
import sdp.ggj14.game.Level;

@SuppressWarnings("serial")
public class SagaFrame extends JFrame {
	Level level;
	
	public SagaFrame() {
		super.setSize(800, 600);
		
		
		
		this.level = new Level();
	}
	
	@Override
	public void paint(Graphics graphics) {
		level.paint(graphics);
	}
	
}
