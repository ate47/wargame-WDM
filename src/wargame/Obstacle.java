package wargame;

public class Obstacle extends Element {

	public enum TypeObstacle {
		ROCHER(new ImageAsset("rock0.png")), FORET(new ImageAsset("foret.png")), EAU(new ImageAsset("eau0.png"));
		public static TypeObstacle getObstacleAlea() {
			return values()[(int) (Math.random() * values().length)];
		}

		private final ImageAsset IMAGE;

		TypeObstacle(ImageAsset image) {
			IMAGE = image;
		}
		public ImageAsset getImage() {
			return IMAGE;
		}
	}

	private TypeObstacle TYPE;

	public Obstacle(TypeObstacle type, Position pos) {
		super(pos, type.IMAGE);
		TYPE = type;
	}

	public String toString() {
		return "" + TYPE;
	}
}