package sdp.ggj14;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.UserInterface;
import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.SoundPlayer;

@SuppressWarnings("serial")
public class SagaFrame extends JPanel implements KeyListener {
	String state = "menu";
	Level level;
	UserInterface ui;
	
	public SagaFrame() {
		super.setBackground(Color.BLACK);
	}
	
	private void startGame() {
		SoundPlayer.play("effects/beep.wav");
		
		this.level = new Level();
		this.ui = new UserInterface(level);
		
		state = "playing";
		this.repaint();
	}
	
	public void destroyGame() {
		this.level = null;
		this.ui = null;
		
		for (Control control : Control.values()) {
			control.isDown = false;
		}
		
		state = "menu";
		this.repaint();
	}
	
	public void endGame() {
		for (Control control : Control.values()) {
			control.isDown = false;
		}
		
		state = "gameOver";
	}
	
	public void sagaUpdate(double elapsedTime) {
		switch (state) {
		case "playing":
			for (Control control : Control.values()) {
				if (control.isDown) {
					control.action(this);
				}
			}
			
			this.level.update(elapsedTime);
			
			if (this.level.getPlayer().getHP() <= 0) {
				this.endGame();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		switch (state) {
		case "playing":
			this.paintGame(graphics);
			break;
		case "menu":
			this.paintMenu(graphics);
			break;
		case "gameOver":
			this.paintEndScreen(graphics);
			break;
		}
	}
	
	private void paintGame(Graphics graphics) {
		level.paint(graphics);
		ui.paint(graphics);
	}
	
	private void paintMenu(Graphics graphics) {
		double ratio = super.getTopLevelAncestor().getSize().width / 1920.0;
		graphics.drawImage(ImageLoader.get("/ui/splash.png"), 0, 0, (int) (1920.0 * ratio), (int) (1080 * ratio), null);
	}
	
	private void paintEndScreen(Graphics graphics) {
		graphics.setColor(Color.MAGENTA);
		graphics.drawRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("Game Over", Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 4);
		
		graphics.drawString("You made it "+Math.round(level.getPlayer().getX()/Level.GRID_SIZE)+" metres.", Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 6 / 20);
		graphics.drawString(level.getPlayer().getHP()+" litres remaining oxygen.", Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 7 / 20);
		graphics.drawString(level.getPlayer().getFuel()+" litres remaining fuel.", Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT * 8 / 20);
	}
	
	
	enum Control {
		UP     (KeyEvent.VK_SPACE) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(0, -1); } },
		/*DOWN   (KeyEvent.VK_S) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(0, +1); } },*/
		LEFT   (KeyEvent.VK_A) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(-1, 0); } },
		RIGHT  (KeyEvent.VK_D) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(+1, 0); } },
		/*ACTION (KeyEvent.VK_SPACE) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().shoot(frame.level); } },*/
		MENU   (KeyEvent.VK_ESCAPE) 
			{ @Override public void action(SagaFrame frame) {  } };
		
		public boolean isDown;
		public int key;
		
		Control(int key) {
			this.key = key;
		}
		
		public abstract void action(SagaFrame frame);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (state) {
		case "playing":
			for (Control control : Control.values()) {
				if (event.getKeyCode() == control.key) {
					control.isDown = true;
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (state) {
		case "playing":
			for (Control control : Control.values()) {
				if (event.getKeyCode() == control.key) {
					control.isDown = false;
				}
			}
			break;
		case "menu":
			this.startGame();
			break;
		case "gameOver":
			this.destroyGame();
			break;
		}
	}
	
}
