package wargame;

public class Element {
	private ImageAsset asset;
	private Position position;

	/**
	 * Constructeur de l'élement qui lui donne aleatoirement des coordonnees
	 */
	public Element(ImageAsset asset) {
		this(new Position((int) Math.random() * IConfig.LARGEUR_CARTE,
				(int) Math.random() * IConfig.HAUTEUR_CARTE), asset);
	}

	public Element(Position pos, ImageAsset asset) {
		this.position = pos;
		this.asset = asset;
	}

	/**
	 * definir la position de l'element
	 * @param position la position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return la position de l'element
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * @return retourne l'image de la case
	 */
	public ImageAsset getSkin() {
		return asset;
	}
}
