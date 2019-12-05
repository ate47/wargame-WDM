package wargame.assets;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Represente un son du jeu
 */
public class SoundAsset {
	/**
	 * Clip audio
	 */
	private Clip audioClip;

	/**
	 * construit un son de jeu avec le liens d'un son
	 * 
	 * @param sound le son
	 */
	public SoundAsset(String sound) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(sound));

			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			audioClip = (Clip) AudioSystem.getLine(info);

			audioClip.open(audioStream);
			audioStream.close();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			throw new IllegalArgumentException("Can't load sound : " + sound, e);
		}
	}
	/**
	 * joue le son
	 */
	public void play() {
		audioClip.setMicrosecondPosition(0L);
		audioClip.start();
	}

	/**
	 * coupe le son
	 */
	public void stop() {
		audioClip.stop();
	}

}
