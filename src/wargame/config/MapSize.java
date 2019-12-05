package wargame.config;

import wargame.assets.IIconObject;
import wargame.assets.ImageAsset;

public enum MapSize implements IIconObject {
	LITTLE(0.5F), NORMAL(1F), BIG(1.5F), HUGE(2F);

	private float factor;
	private ImageAsset icon, iconHover;

	MapSize(float f) {
		this.factor = f;
		this.icon = new ImageAsset("map/size_" + name().toLowerCase() + ".png");
		this.iconHover = new ImageAsset("map/size_" + name().toLowerCase() + "_hover.png");
	}

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
