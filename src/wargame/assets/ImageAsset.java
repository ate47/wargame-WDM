package wargame.assets;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represente une texture de jeu
 */
public class ImageAsset {
	/**
	 * les images de la texture
	 */
	private Image[] images;
	/**
	 * le temps entre chaque changement d'image pour {@link #getImageFromTime()}
	 */
	private long time;

	/**
	 * construit une texture de jeu avec les liens d'images dans images avec un
	 * temps entre chaque images
	 * 
	 * @param time   le temps (en millis) entre chaque images
	 * @param images les liens d'images, doivent partir du même package de
	 *               {@link ImageAsset} ou etre absolu
	 */
	public ImageAsset(long time, String... images) {
		this.time = time;
		if (images.length == 0)
			throw new IllegalArgumentException("empty images set");
		this.images = new Image[images.length];
		int i = 0;
		try {
			for (; i < images.length; i++)
				this.images[i] = ImageIO.read(ImageAsset.class.getResourceAsStream(images[i]));
		} catch (IOException | IllegalArgumentException e) {
			throw new RuntimeException("Error reading image : " + images[i], e);
		}
	}

	/**
	 * construit une texture de jeu avec les liens d'images dans images
	 * 
	 * @param images les liens d'images, doivent partir du même package de
	 *               {@link ImageAsset} ou etre absolu
	 */
	public ImageAsset(String... images) {
		this(250L, images);
	}

	/**
	 * retourne une image suivant une position
	 * 
	 * @param x coord x de la position
	 * @param y coord y de la position
	 * @return l'image
	 */
	public Image getImageFromPosition(int x, int y) {
		return images[Math.abs((x * (y + 1)) % images.length)];
	}

	/**
	 * @return l'image suivant le temps courant
	 */
	public Image getImageFromTime() {
		return images[(int) (System.currentTimeMillis() / time) % images.length];
	}

	/**
	 * @return le tableau des images de la texture
	 */
	public Image[] getImages() {
		return images;
	}
}
