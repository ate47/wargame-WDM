package wargame.config;

import wargame.ICase;
import wargame.Soldat;
import wargame.Wargame.FinJeu;

/**
 * Represente une configuration de jeu
 */
public interface IConfig {

	/**
	 * @return un clone profond de la configuration
	 */
	IConfig clone();

	/**
	 * @return la carte de jeu
	 */
	ICase[][] getCarte();

	/**
	 * @return l'état courant
	 */
	FinJeu getCourant();

	/**
	 * @return la difficulté courante
	 */
	Difficulty getDifficulty();

	/**
	 * @return la faction ennemi
	 */
	Faction getFactionEnnemi();

	/**
	 * @return la faction allié
	 */
	Faction getFactionJoueur();

	/**
	 * @return la hauteur de la carte
	 */
	int getHauteurCarte();

	/**
	 * @return la largeur de la carte
	 */
	int getLargeurCarte();

	/**
	 * @return la taille de la carte
	 */
	MapSize getMapSize();

	/**
	 * @return le nombre d'obstacle
	 */
	int getNombreObstacle();

	/**
	 * @return le pourcentage d'obstacle
	 */
	float getPourcentageObstacle();

	/**
	 * @return les soldats ennemis
	 */
	Soldat[] getSoldatEnnemis();

	/**
	 * @return nos soldats
	 */
	Soldat[] getSoldatJoueur();

	/**
	 * @return la vie en plus par regen
	 */
	int getVieParRegen();

	/**
	 * @return si on affiche les FPS
	 */
	boolean isShowFps();

	/**
	 * definir la carte
	 * 
	 * @param carte
	 *            la carte
	 */
	void setCarte(ICase[][] carte);

	/**
	 * definir l'état de jeu
	 * 
	 * @param courant
	 *            l'état de jeu
	 */
	void setCourant(FinJeu courant);

	/**
	 * definir la difficulté
	 * 
	 * @param d
	 *            la difficulté
	 */
	void setDifficulty(Difficulty d);

	/**
	 * definir la faction ennemi
	 * 
	 * @param factionEnnemi
	 *            la factionEnnemi
	 */
	void setFactionEnnemi(Faction factionEnnemi);

	/**
	 * definir la faction joueur
	 * 
	 * @param factionJoueur
	 *            la joueur
	 */
	void setFactionJoueur(Faction factionJoueur);

	/**
	 * définir la hauteur de la carte
	 * 
	 * @param hauteurCarte
	 *            la hauteur de la carte
	 */
	void setHauteurCarte(int hauteurCarte);

	/**
	 * définir la largeur de la carte
	 * 
	 * @param largeurCarte
	 *            la largeur de la carte
	 */
	void setLargeurCarte(int largeurCarte);

	/**
	 * définir la taille de la carte
	 * 
	 * @param mapSize
	 *            la taille de la carte
	 */
	void setMapSize(MapSize mapSize);

	/**
	 * définir le pourcentage d'obstacle
	 * 
	 * @param pourcentageObstacle
	 *            le pourcentage d'obstacle
	 */
	void setPourcentageObstacle(float pourcentageObstacle);

	/**
	 * définir si on affiche les fps
	 * 
	 * @param showFps
	 *            si on affiche les fps
	 */
	void setShowFps(boolean showFps);

	/**
	 * définir les soldats ennemis
	 * 
	 * @param soldatEnnemis
	 *            les soldats ennemis
	 */
	void setSoldatEnnemis(Soldat[] soldatEnnemis);

	/**
	 * définir les soldats joueurs
	 * 
	 * @param soldatEnnemis
	 *            les soldats joueurs
	 */
	void setSoldatJoueur(Soldat[] soldatJoueur);
}