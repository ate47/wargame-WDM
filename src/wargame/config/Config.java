package wargame.config;

import java.io.Serializable;

import wargame.IType.Faction;

public class Config implements IConfig, Serializable {

	private static final long serialVersionUID = 9194042623694545614L;
	private static final int vieParRegen = 70;
	private boolean showFps = false;
	private int largeurCarte = 25;
	private int hauteurCarte = 30;
	private float pourcentageObstacle = .25F;
	private Faction factionJoueur = Faction.BLANC;
	private Difficulty difficulty = Difficulty.NORMAL;
	private MapSize mapSize = MapSize.NORMAL;

	public Config() {}

	private Config(Config old) {
		this.showFps = old.showFps;
		this.largeurCarte = old.largeurCarte;
		this.hauteurCarte = old.hauteurCarte;
		this.pourcentageObstacle = old.pourcentageObstacle;
		this.factionJoueur = old.factionJoueur;
		this.difficulty = old.difficulty;
		this.mapSize = old.mapSize;
	}

	@Override
	public Config clone() {
		return new Config(this);
	}

	@Override
	public Difficulty getDifficulty() {
		return difficulty;
	}

	@Override
	public Faction getFactionJoueur() {
		return factionJoueur;
	}

	@Override
	public int getHauteurCarte() {
		return (int) (hauteurCarte * mapSize.getFactor());
	}

	@Override
	public int getLargeurCarte() {
		return (int) (largeurCarte * mapSize.getFactor());
	}

	@Override
	public int getNombreObstacle() {
		return (int) (getLargeurCarte() * getHauteurCarte() * getPourcentageObstacle());
	}

	@Override
	public float getPourcentageObstacle() {
		return pourcentageObstacle;
	}

	@Override
	public int getVieParRegen() {
		return vieParRegen;
	}

	@Override
	public boolean isShowFps() {
		return showFps;
	}

	@Override
	public boolean isShowingFPS() {
		return showFps;
	}

	@Override
	public void setDifficulty(Difficulty d) {
		difficulty = d;
	}

	@Override
	public void setFactionJoueur(Faction factionJoueur) {
		this.factionJoueur = factionJoueur;
	}

	@Override
	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	@Override
	public void setLargeurCarte(int largeurCarte) {
		this.largeurCarte = largeurCarte;
	}

	@Override
	public void setPourcentageObstacle(float pourcentageObstacle) {
		this.pourcentageObstacle = pourcentageObstacle;
	}

	@Override
	public void setShowFps(boolean showFps) {
		this.showFps = showFps;
	}

	@Override
	public MapSize getMapSize() {
		return mapSize;
	}

	@Override
	public void setMapSize(MapSize mapSize) {
		this.mapSize = mapSize;
	}
}
