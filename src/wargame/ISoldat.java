package wargame;

import wargame.config.IConfig;

public interface ISoldat {
	/**
	 * Valeur de retour de {@link ISoldat#getProchainMouvement()}
	 */
	enum SoldatProchainMouvement {
		/**
		 * Lors d'un combat
		 */
		COMBAT,
		/**
		 * Lors d'un deplacement
		 */
		DEPLACEMENT,
		/**
		 * Lors d'aucune action (ou regen si la vie n'est pas au maximum)
		 */
		RIEN
	}

	/**
	 * @return si le soldat a déjà été deplacé ce tour
	 */
	boolean aJoueCeTour();

	/**
	 * annule la demande d'action du soldat
	 */
	void annulerTour();

	/**
	 * Attaque un autre soldat
	 * 
	 * @param soldat le soldat
	 */
	void combat(ISoldat soldat);

	/**
	 * @return si le soldat est mort ou vivant
	 */
	boolean estMort();

	/**
	 * @return la cible lors d'une attaque
	 */
	Soldat getCible();

	/**
	 * @return le prochain emplacement pour un deplacement
	 */
	ICase getNextPosition();

	/**
	 * @return la position du soldat
	 */
	ICase getPosition();

	/**
	 * @return obtenir le prochain mouvement du soldat
	 */
	SoldatProchainMouvement getProchainMouvement();

	/**
	 * @return le type du soldat
	 */
	IType getType();

	/**
	 * @return la vie du soldat
	 */
	int getVie();

	/**
	 * Joue le tour du soldat
	 * 
	 * @param cfg la configuration de jeu à suivre
	 */
	void joueTour(IConfig cfg);

	/**
	 * Demande un combat de ce soldat avec une cible pour le prochain tour
	 * 
	 * @param enemi la cible
	 * @throws IllegalMoveException
	 */

	void seBat(Soldat enemi) throws IllegalMoveException;

	/**
	 * demande un deplacement de ce soldat pour le prochain tour
	 * 
	 * @param newPos la nouvelle position
	 * @throws IllegalMoveException si la position n'est pas valide
	 */
	void seDeplace(ICase newPos) throws IllegalMoveException;

	/**
	 * demande de se regen
	 * 
	 * @throws IllegalMoveException
	 */
	void seRegen() throws IllegalMoveException;

	/**
	 * definir la vie du soldat (sans verification des valeurs)
	 * 
	 * @param vie
	 */
	void setVie(int vie);
}