package sdp.ggj14;

import javax.swing.JFrame;

public class Main {
	
	private boolean running = true;
	private long desiredFPS = 60;
	private long desiredLoopTime = (1000*1000*1000)/desiredFPS;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		SagaFrame frame = new SagaFrame();
		//frame.pack();
		frame.setVisible(true);
		run(frame);
	}
	
	private void run(SagaFrame frame) {
		long updateTime;
		long prevUpdateTime = System.nanoTime();
		long beginLoopTime;
		long endLoopTime;
		long loopTime = 0;
		float deltaTime;
		while(running) {
			beginLoopTime = System.nanoTime();
			frame.repaint();
			
			updateTime = System.nanoTime();
			deltaTime = updateTime - prevUpdateTime;
			frame.sagaUpdate(Math.round(deltaTime/(1000*1000)));
			prevUpdateTime = System.nanoTime();
			
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
