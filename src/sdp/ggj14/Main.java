package sdp.ggj14;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import sdp.ggj14.util.ImageLoader;
import sdp.ggj14.util.PropertiesLoader;

@SuppressWarnings("serial")
public class Main extends JFrame {
	public static final int SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 700;//374;
	
	private boolean running = true;
	private long desiredFPS = 60;
	private long desiredLoopTime = (1000*1000*1000)/desiredFPS;
	
	public static void main(String[] args) {
		new Main();
		
		PropertiesLoader.get(System.getProperty("user.dir")+"/dat/settings/Game");
	}
	
	public Main() {
		
		loadImages();
		
		SagaFrame panel = new SagaFrame();
		super.add(panel);
		super.addKeyListener((KeyListener) panel);
		
		super.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setVisible(true);
		
		run(panel);
	}
	
	private void loadImages() {
		ImageLoader.get("/ui/splash.png");
		ImageLoader.get("/level/background/landscape.jpg");
		ImageLoader.get("/level/background/swamp.jpg");
		ImageLoader.get("/level/background/night.jpg");
		ImageLoader.get("/level/background/divergence.jpg");
		ImageLoader.get("/level/background/landscape.jpg");
		ImageLoader.get("/ui/air.png");
		ImageLoader.get("/ui/fuel.png");
		ImageLoader.get("/ui/bar.png");
		ImageLoader.get("/items/artifact/s02.png");
		ImageLoader.get("/items/artifact/s06.png");
		ImageLoader.get("/items/new_life_form/s03.png");
		ImageLoader.get("/items/new_life_form/s08.png");
		ImageLoader.get("/projectiles/slime.png");
		ImageLoader.get("/aliens/snail/s06.png");
		ImageLoader.get("/aliens/flayer/s01.png");
		ImageLoader.get("/aliens/flayer/s02.png");
		ImageLoader.get("/aliens/flayer/s03.png");
		ImageLoader.get("/aliens/flayer/s04.png");
		ImageLoader.get("/aliens/flayer/s05.png");
		ImageLoader.get("/aliens/fuzz/s10.png");
		ImageLoader.get("/tiles/ground/s01.png");
		ImageLoader.get("/tiles/ground/s05.png");
		ImageLoader.get("/tiles/ground/s04.png");
		ImageLoader.get("/tiles/ground/s08.png");
		ImageLoader.get("/tiles/ground/s02.png");
		ImageLoader.get("/tiles/ground/s06.png");
		ImageLoader.get("/tiles/background/s01.png");
		ImageLoader.get("/tiles/background/s02.png");
		ImageLoader.get("/tiles/background/s03.png");
		ImageLoader.get("/tiles/background/s04.png");
		ImageLoader.get("/tiles/cavern/s01.png");
		ImageLoader.get("/tiles/cavern/s02.png");
		ImageLoader.get("/tiles/cavern/s03.png");
		ImageLoader.get("/tiles/cavern/s04.png");
		ImageLoader.get("/tiles/ship/s02.png");
		ImageLoader.get("/tiles/ship/s03.png");
		ImageLoader.get("/tiles/ship/s05.png");
		ImageLoader.get("/tiles/ship/s01.png");
		ImageLoader.get("/tiles/ship/s04.png");
		ImageLoader.get("/tiles/ship/s06.png");
		ImageLoader.get("/tiles/ship/s07.png");
		ImageLoader.get("/player/idle/s01.png");
		ImageLoader.get("/player/idle/s02.png");
		ImageLoader.get("/player/idle/s03.png");
		ImageLoader.get("/player/idle/s04.png");
		ImageLoader.get("/player/idle/s05.png");
		ImageLoader.get("/player/idle/s06.png");
		ImageLoader.get("/player/idle/s07.png");
		ImageLoader.get("/player/idle/s08.png");
		ImageLoader.get("/player/idle/s09.png");
		ImageLoader.get("/player/idle/s10.png");
		ImageLoader.get("/player/flying/loop_s01.png");
		ImageLoader.get("/player/flying/loop_s02.png");
		ImageLoader.get("/player/flying/loop_s03.png");
		ImageLoader.get("/player/flyingBlue/loop_s01.png");
		ImageLoader.get("/player/flyingBlue/loop_s02.png");
		ImageLoader.get("/player/flyingBlue/loop_s03.png");
		ImageLoader.get("/player/flyingGreen/loop_s01.png");
		ImageLoader.get("/player/flyingGreen/loop_s02.png");
		ImageLoader.get("/player/flyingGreen/loop_s03.png");
		ImageLoader.get("/player/flying/no_fuel.png");
		ImageLoader.get("/player/walking/s01.png");
		ImageLoader.get("/player/walking/s02.png");
		ImageLoader.get("/player/walking/s03.png");
		ImageLoader.get("/player/walking/s04.png");
		ImageLoader.get("/player/walking/s05.png");
		ImageLoader.get("/player/walking/s06.png");
		ImageLoader.get("/player/walking/s07.png");
		ImageLoader.get("/player/walking/s08.png");
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
