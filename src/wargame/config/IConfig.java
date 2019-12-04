package wargame.config;

import wargame.IType.Faction;

public interface IConfig {
	public MapSize getMapSize();

	public void setMapSize(MapSize mapSize);

	IConfig clone();

	Difficulty getDifficulty();

	Faction getFactionJoueur();

	int getHauteurCarte();

	int getLargeurCarte();

	int getNombreObstacle();

	float getPourcentageObstacle();

	int getVieParRegen();

	boolean isShowFps();

	boolean isShowingFPS();

	void setDifficulty(Difficulty d);

	void setFactionJoueur(Faction factionJoueur);

	void setHauteurCarte(int hauteurCarte);

	void setLargeurCarte(int largeurCarte);

	void setPourcentageObstacle(float pourcentageObstacle);

	void setShowFps(boolean showFps);
}