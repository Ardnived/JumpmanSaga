package sdp.ggj14;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private boolean running = true;
	private long desiredFPS = 60;
	private long desiredLoopTime = (1000*1000*1000)/desiredFPS;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		SagaFrame panel = new SagaFrame();
		super.add(panel);
		super.setSize(800, 600);
		super.addKeyListener(panel);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setVisible(true);
		run(panel);
	}
	
	private void run(SagaFrame saga) {
		long updateTime;
		long prevUpdateTime = System.nanoTime();
		long beginLoopTime;
		long endLoopTime;
		long loopTime = 0;
		float deltaTime;
		
		while (running) {
			beginLoopTime = System.nanoTime();
			
			updateTime = System.nanoTime();
			deltaTime = updateTime - prevUpdateTime;
			saga.sagaUpdate(deltaTime/(1000.0*1000.0));
			prevUpdateTime = System.nanoTime();

			saga.repaint();
			
			endLoopTime = System.nanoTime();
			loopTime = endLoopTime-beginLoopTime;
			if (loopTime < desiredLoopTime) {
				try {
					Thread.sleep((desiredLoopTime-loopTime)/(1000*1000));
				} catch(InterruptedException e) { }
			}
		}
	}

}
