package wargame.assets;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Represente un son du jeu
 */
public class SoundAsset implements LineListener {
	/**
	 * Clip audio
	 */
	private Clip audioClip;
	private boolean loop;

	/**
	 * construit un son de jeu avec le liens d'un son
	 * 
	 * @param sound le son
	 */
	public SoundAsset(String sound) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundAsset.class.getResource(sound));

			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(Clip.class, format);

			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.addLineListener(this);
			audioClip.open(audioStream);
			audioStream.close();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | NullPointerException e) {
			throw new IllegalArgumentException("Can't load sound : " + sound, e);
		}
	}

	/**
	 * boucler le son
	 */
	public void loop() {
		loop = true;
		audioClip.start();
	}

	/**
	 * joue le son
	 */
	public void play() {
		loop = true;
		audioClip.setMicrosecondPosition(0L);
		audioClip.start();
	}

	/**
	 * coupe le son
	 */
	public void stop() {
		loop = false;
		audioClip.stop();
	}

	@Override
	public void update(LineEvent event) {
		Type v = event.getType();
		if (v == Type.STOP && loop) {
			audioClip.setMicrosecondPosition(0L);
			audioClip.start();
		}
	}

}
