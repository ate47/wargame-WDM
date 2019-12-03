package wargame;

public interface ISoldat {
	enum SoldatProchainMouvement {
		DEPLACEMENT, RIEN
	}

	/**
	 * Attaque un autre soldat
	 * 
	 * @param soldat le soldat
	 */
	void combat(Soldat soldat);

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
	void joueTour();

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
	void seDeplace(Position newPos) throws IllegalMoveException;
	
	void seRegen() throws IllegalMoveException;

	SoldatProchainMouvement getProchainMouvement();
	
	Position getPosition();

	Position getNextPosition();
}