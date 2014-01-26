package sdp.ggj14.util;

import java.io.*;
import sun.audio.*;

public abstract class SoundPlayer {
	
	public static void play(String fileName) {
		try {
			InputStream in = new FileInputStream(System.getProperty("user.dir")+"/aud/"+fileName);
			AudioStream audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
