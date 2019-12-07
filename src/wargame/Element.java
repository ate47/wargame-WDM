package wargame;

import java.io.Serializable;

import wargame.assets.IImagedObject;
import wargame.assets.ImageAsset;

/**
 * Element dans le plateau de jeu
 */
public abstract class Element implements Serializable, Cloneable {
	private static final long serialVersionUID = 2367195829376787992L;
	private IImagedObject asset;
	private ICase position;

	public Element(IImagedObject asset) {
		this.asset = asset;
	}

	/**
	 * definir la position de l'element
	 * 
	 * @param position
	 *            la position
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
		return asset.getImage();
	}

	@Override
	public abstract Element clone();
}
