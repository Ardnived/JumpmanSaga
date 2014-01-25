package sdp.ggj14;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import sdp.ggj14.game.Level;
import sdp.ggj14.game.UserInterface;

@SuppressWarnings("serial")
public class SagaFrame extends JPanel implements KeyListener {
	Level level;
	UserInterface ui;
	
	public SagaFrame() {
		this.level = new Level();
		this.ui = new UserInterface();
	}
	
	public void sagaUpdate(double elapsedTime) {
		//System.out.println(dt);
		
		for (Control control : Control.values()) {
			if (control.isDown) {
				control.action(this);
			}
		}
		
		this.level.update(elapsedTime);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		level.paint(graphics);
		ui.paint(graphics);
	}
	
	
	enum Control {
		UP     (KeyEvent.VK_W) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(0, -4); } },
		DOWN   (KeyEvent.VK_S) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(0, +4); } },
		LEFT   (KeyEvent.VK_A) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(-4, 0); } },
		RIGHT  (KeyEvent.VK_D) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().move(+4, 0); } },
		ACTION (KeyEvent.VK_SPACE) 
			{ @Override public void action(SagaFrame frame) { frame.level.getPlayer().shoot(); } },
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
		for (Control control : Control.values()) {
			if (event.getKeyCode() == control.key) {
				control.isDown = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		for (Control control : Control.values()) {
			if (event.getKeyCode() == control.key) {
				control.isDown = false;
			}
		}
	}
	
}
