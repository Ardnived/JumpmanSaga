package sdp.ggj14;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import sdp.ggj14.game.Level;

@SuppressWarnings("serial")
public class SagaFrame extends JFrame implements KeyListener {
	Level level;
	private long dt;
	
	public SagaFrame() {
		super.setSize(800, 600);
		super.addKeyListener(this);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		
		this.level = new Level();
	}
	
	public void sagaUpdate(long dt) {
		//System.out.println(dt);
		
		for (Control control : Control.values()) {
			if (control.isDown) {
				control.action(this);
			}
		}
		
		this.level.update();
	}
	
	@Override
	public void paint(Graphics graphics) {
		level.paint(graphics);
	}
	
	
	enum Control {
		UP     (KeyEvent.VK_W) { @Override public void action(SagaFrame frame) { frame.level.getPlayer().Up(); } },
		DOWN   (KeyEvent.VK_S) { @Override public void action(SagaFrame frame) { frame.level.getPlayer().Down(); } },
		LEFT   (KeyEvent.VK_A) { @Override public void action(SagaFrame frame) { frame.level.getPlayer().Left(); } },
		RIGHT  (KeyEvent.VK_D) { @Override public void action(SagaFrame frame) { frame.level.getPlayer().Right(); } },
		ACTION (KeyEvent.VK_SPACE) { @Override public void action(SagaFrame frame) {  } },
		MENU   (KeyEvent.VK_ESCAPE) { @Override public void action(SagaFrame frame) {  } };
		
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
