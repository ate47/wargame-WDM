package wargame;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageAsset {
	private Image[] images;
	private long time;

	public ImageAsset(String... images) {
		this(250L, images);
	}

	public ImageAsset(long time, String... images) {
		this.time = time;
		if (images.length == 0)
			throw new IllegalArgumentException("empty images set");
		this.images = new Image[images.length];
		try {
			for (int i = 0; i < images.length; i++)
				this.images[i] = ImageIO.read(ImageAsset.class.getResourceAsStream(images[i]));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Image getImageFromPosition(int x, int y) {
		return images[(x * (y + 1)) % images.length];
	}

	public Image getImageFromTime() {
		return images[(int) (System.currentTimeMillis() / time) % images.length];
	}

	public Image[] getImages() {
		return images;
	}
}
