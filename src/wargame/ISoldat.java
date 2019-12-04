package wargame;

import wargame.ICarte.ICase;
import wargame.config.IConfig;

public interface ISoldat {
	enum SoldatProchainMouvement {
		COMBAT, DEPLACEMENT, RIEN
	}

	/**
	 * Attaque un autre soldat
	 * 
	 * @param soldat le soldat
	 */
	void combat(ISoldat soldat);

	/**
	 * @return si le soldat a déjà été deplacé ce tour
	 */
	boolean aJoueCeTour();

	/**
	 * @return le type du soldat
	 */
	IType getType();

	/**
	 * joue le tour du soldat
	 */
	void joueTour(IConfig cfg);

	/**
	 * annule la demande d'action du soldat
	 */
	void annulerTour();

	/**
	 * demande un deplacement de ce soldat pour le prochain tour
	 * 
	 * @param newPos la nouvelle position
	 * @throws IllegalMoveException si la position n'est pas valide
	 */
	void seDeplace(ICase newPos) throws IllegalMoveException;
	
	/**
	 * Demande un combat de ce soldat avec une cible pour le prochain tour 
	 * @param enemi la cible
	 * @throws IllegalMoveException 
	 */
	
	void seBat(Soldat enemi)throws IllegalMoveException;
	
	/*
	 * Demande que l'unité ce soigne pour le prochain tour
	 * @throws IllegalMoveException
	 */
	
	void seRegen() throws IllegalMoveException;
	

	void setVie(int vie);


	int getVie();


	SoldatProchainMouvement getProchainMouvement();
	
	ICase getPosition();

	ICase getNextPosition();
	
	Soldat getCible();
	
	boolean estMort();
}