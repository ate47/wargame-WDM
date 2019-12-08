package wargame.config;

import wargame.assets.IIconObject;
import wargame.assets.ImageAsset;

/**
 * Represente une difficulté de jeu
 */
public enum Difficulty implements IIconObject {

	/**
	 * Facile
	 */
	EASY("Facile", 1, 2F, 0.75F),

	/**
	 * Normal
	 */
	NORMAL("Normal", 1, 1F, 1F),

	/**
	 * Dur
	 */
	HARD("Dur", 2, 0.75F, 1F),

	/**
	 * Extreme
	 */
	ULTRA("Extreme", 3, 0.5F, 1.5F);

	private int areaCount;
	private float vie, ennemis;
	private String name;
	private ImageAsset icon, iconHover;

	Difficulty(String name, int areaCount, float vie, float ennemis) {
		this.name = name;
		this.areaCount = areaCount;
		this.vie = vie;
		this.ennemis = ennemis;
		this.icon = new ImageAsset("map/difficulty_" + name().toLowerCase() + ".png");
		this.iconHover = new ImageAsset("map/difficulty_" + name().toLowerCase() + "_hover.png");
	}

	/**
	 * @return le nom de la difficulté
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return le nombre de zone pour les ennemis 
	 */
	public int getAreaCount() {
		return areaCount;
	}

	/**
	 * @return le multiplicateur de vie
	 */
	public float getMultiplicateurVie() {
		return vie;
	}

	/**
	 * @return le multiplicateur d'ennemis
	 */
	public float getMultiplicateurEnnemis() {
		return ennemis;
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
