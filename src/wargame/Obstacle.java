package wargame;

import wargame.assets.IImagedObject;
import wargame.assets.ImageAsset;

/**
 * Obstacle dans une case du plateau de jeu
 */
public class Obstacle extends Element {

	private static final long serialVersionUID = 5726626684822956694L;

	/**
	 * Le type de l'obstacle
	 */
	public enum TypeObstacle implements IImagedObject {
		/**
		 * Une montagne
		 */
		ROCHER(new ImageAsset("rock0.png")),
		/**
		 * Forêt
		 */
		FORET(new ImageAsset("foret.png")),
		/**
		 * Ocean / eau
		 */
		EAU(new ImageAsset("eau0.png"));
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

	/**
	 * Construit un obstacle avec un type
	 * 
	 * @param type
	 *            le type de l'obstacle
	 */
	public Obstacle(TypeObstacle type) {
		super(type);
		TYPE = type;
	}

	/**
	 * Construit un obstacle aleatoire
	 */
	public Obstacle() {
		this(TypeObstacle.getObstacleAlea());
	}

	@Override
	public String toString() {
		return "" + TYPE;
	}

	@Override
	public Obstacle clone() {
		return new Obstacle(TYPE);
	}
}