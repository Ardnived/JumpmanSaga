package sdp.ggj14.util;

import java.io.*;
import sun.audio.*;

public abstract class SoundPlayer {
	
	public static ContinuousAudioDataStream play(String fileName, boolean loop) {
		try {
			InputStream in = new FileInputStream(System.getProperty("user.dir")+"/aud/"+fileName);
			AudioStream audioStream = new AudioStream(in);
			if (loop) {
				AudioData audioData = audioStream.getData();
				ContinuousAudioDataStream loopStream = new ContinuousAudioDataStream(audioData);
				AudioPlayer.player.start(loopStream);
				return loopStream;
			}
			else {
				AudioPlayer.player.start(audioStream);
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}

//To stop sound from playing, take the returning ContinuousAudioDataStream object and call the function AudioPlayer.player.stop(dataStream);
//Non looping sounds only return null