package wargame.config;

public enum MapSize {
	LITTLE(0.5F), NORMAL(1F), LARGE(1.5F), HUGE(2F);

	private float factor;

	MapSize(float f) {
		this.factor = f;
	}

	public float getFactor() {
		return factor;
	}
}
