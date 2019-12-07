package wargame;

import wargame.assets.IImagedObject;
import wargame.assets.ImageAsset;

/**
 * Obstacle dans une case du plateau de jeu
 */
public class Obstacle extends Element {

	private static final long serialVersionUID = 5726626684822956694L;

	public enum TypeObstacle implements IImagedObject {
		ROCHER(new ImageAsset("rock0.png")), FORET(new ImageAsset("foret.png")), EAU(new ImageAsset("eau0.png"));
		public static TypeObstacle getObstacleAlea() {
			return values()[(int) (Math.random() * values().length)];
		}

		private final ImageAsset IMAGE;

		TypeObstacle(ImageAsset image) {
			IMAGE = image;
		}

		@Override
		public ImageAsset getImage() {
			return IMAGE;
		}
	}

	private TypeObstacle TYPE;

	public Obstacle(TypeObstacle type) {
		super(type);
		TYPE = type;
	}

	public Obstacle() {
		this(TypeObstacle.getObstacleAlea());
	}

	public String toString() {
		return "" + TYPE;
	}

	@Override
	public Obstacle clone() {
		return new Obstacle(TYPE);
	}
}