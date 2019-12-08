package wargame.config;

import wargame.assets.IIconObject;
import wargame.assets.ImageAsset;

/**
 * Represente une taille de jeu
 */
public enum MapSize implements IIconObject {
	/**
	 * petit
	 */
	LITTLE(0.5F),
	/**
	 * normal
	 */
	NORMAL(1F),
	/**
	 * gros
	 */
	BIG(1.5F),
	/**
	 * enorme
	 */
	HUGE(2F);

	private float factor;
	private ImageAsset icon, iconHover;

	MapSize(float f) {
		this.factor = f;
		this.icon = new ImageAsset("map/size_" + name().toLowerCase() + ".png");
		this.iconHover = new ImageAsset("map/size_" + name().toLowerCase() + "_hover.png");
	}

	/**
	 * @return le facteur de croissance de la taille
	 */
	public float getFactor() {
		return factor;
	}

	@Override
	public ImageAsset getIcon() {
		return icon;
	}

	@Override
	public ImageAsset getIconHover() {
		return iconHover;
	}
}
