package wargame.config;

public enum Difficulty {

	EASY("Facile", 1, 2F, 0.75F),

	NORMAL("Normal", 1, 1F, 1F),

	HARD("Dur", 2, 0.5F, 1F),

	ULTRA("Extreme", 3, 0.5F, 1.5F);

	private int areaCount;
	private float vie, ennemis;
	private String name;

	Difficulty(String name, int areaCount, float vie, float ennemis) {
		this.name = name;
		this.areaCount = areaCount;
		this.vie = vie;
		this.ennemis = ennemis;
	}

	public String getName() {
		return name;
	}

	public int getAreaCount() {
		return areaCount;
	}

	public float getMultiplicateurVie() {
		return vie;
	}

	public float getMultiplicateurEnnemis() {
		return ennemis;
	}
}
