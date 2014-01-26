package sdp.ggj14;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import sdp.ggj14.util.ImageLoader;

@SuppressWarnings("serial")
public class MenuFrame extends JPanel implements KeyListener, MouseListener {
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		double ratio = super.getTopLevelAncestor().getSize().width / 1920.0;
		graphics.drawImage(ImageLoader.get("/ui/splash.png"), 0, 0, (int) (1920.0 * ratio), (int) (1080 * ratio), null);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent event) {
		this.startGame();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.startGame();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	private void startGame() {
		System.out.println("Start Game");
		//((Main) this.getTopLevelAncestor()).startGame();
	}

}
