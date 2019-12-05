package wargame.config;

import wargame.IType;
import wargame.IType.TypeBon;
import wargame.IType.TypeMauvais;
import wargame.assets.IIconObject;
import wargame.assets.ImageAsset;

/**
 * Represente une faction pour un soldat
 */
public enum Faction implements IIconObject {
	BLANC("Blanc", TypeBon.values(), 10),
	VERT("Vert", TypeMauvais.values(), 15);
	public static Faction getRandomFaction() {
		return values()[(int) (Math.random() * values().length)];
	}

	private IType[] values;
	private ImageAsset icon;
	private int nombreGenere;
	private String name;

	Faction(String name, IType[] values, int nombreGenere) {
		this.name = name;
		this.values = values;
		this.nombreGenere = nombreGenere;
		this.icon = new ImageAsset("faction/faction_"+name().toLowerCase()+".png");
	}

	@Override
	public ImageAsset getIcon() {
		return icon;
	}

	/**
	 * @return une autre faction que celle-ci aléatoirement
	 */
	public Faction getOthers() {
		int n = values().length;
		return values()[(ordinal() + 1 + (int) (Math.random() * (n - 1))) % n];
	}

	/**
	 * @return un type de soldat de cette faction
	 */
	public IType getRandomElement() {
		return values[(int) (Math.random() * values.length)];
	}

	/**
	 * @return le tableau des types de soldats pour cette faction
	 */
	public IType[] getTypes() {
		return values;
	}

	/**
	 * @return le nombre d'élement a généré pour un jeu normal
	 */
	public int nombreGenere() {
		return nombreGenere;
	}

	public String getName() {
		return name;
	}

	@Override
	public ImageAsset getIconHover() {
		return null;
	}
}