package wargame.config;

import wargame.ICase;
import wargame.Soldat;
import wargame.Wargame.FinJeu;

public interface IConfig {

	IConfig clone();

	ConfigCase[][] getCarte();

	FinJeu getCourant();

	Difficulty getDifficulty();

	Faction getFactionEnnemi();

	Faction getFactionJoueur();

	int getHauteurCarte();

	int getLargeurCarte();

	MapSize getMapSize();

	int getNombreObstacle();

	float getPourcentageObstacle();

	Soldat[] getSoldatEnnemis();

	Soldat[] getSoldatJoueur();

	int getVieparregen();

	int getVieParRegen();

	boolean isShowFps();

	boolean isShowingFPS();

	void setCarte(ICase[][] carte);

	void setCourant(FinJeu courant);

	void setDifficulty(Difficulty d);

	void setFactionEnnemi(Faction factionEnnemi);

	void setFactionJoueur(Faction factionJoueur);

	void setHauteurCarte(int hauteurCarte);

	void setLargeurCarte(int largeurCarte);

	void setMapSize(MapSize mapSize);

	void setPourcentageObstacle(float pourcentageObstacle);

	void setShowFps(boolean showFps);

	void setSoldatEnnemis(Soldat[] soldatEnnemis);

	void setSoldatJoueur(Soldat[] soldatJoueur);
}