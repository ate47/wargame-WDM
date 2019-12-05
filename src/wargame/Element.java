package wargame;

import wargame.ICarte.ICase;
import wargame.assets.ImageAsset;

public class Element {
	private ImageAsset asset;
	private ICase position;



	public Element(ImageAsset asset) {
		this.asset = asset;
	}

	/**
	 * definir la position de l'element
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
