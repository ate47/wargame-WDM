package wargame;

import wargame.assets.ImageAsset;

/**
 * Element dans le plateau de jeu
 */
public class Element {
	private ImageAsset asset;
	private ICase position;

	public Element(ImageAsset asset) {
		this.asset = asset;
	}

	/**
	 * definir la position de l'element
	 * 
	 * @param position la position
	 */
	public void setPosition(ICase position) {
		this.position = position;
	}

	/**
	 * @return la position de l'element
	 */
	public ICase getPosition() {
		return position;
	}

	/**
	 * @return retourne l'image de la case
	 */
	public ImageAsset getSkin() {
		return asset;
	}
}
