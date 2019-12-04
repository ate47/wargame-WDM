package wargame;

import wargame.IType.Faction;

public interface IConfig {
	Faction getFactionJoueur();

	int getHauteurCarte();

	int getLargeurCarte();

	int getNombreObstacle();

	float getPourcentageObstacle();

	int getVieParRegen();

	boolean isShowFps();

	boolean isShowingFPS();

	void setFactionJoueur(Faction factionJoueur);

	void setHauteurCarte(int hauteurCarte);

	void setLargeurCarte(int largeurCarte);

	void setPourcentageObstacle(float pourcentageObstacle);

	void setShowFps(boolean showFps);

	void setVieParRegen(int vieParRegen);
	
	IConfig clone();
}