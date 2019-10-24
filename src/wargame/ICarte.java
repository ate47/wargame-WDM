package wargame;

import java.awt.Graphics;

public interface ICarte {
	boolean actionHeros(Position pos, Position pos2);

	boolean deplaceSoldat(Position pos, Soldat soldat);

	Element getElement(Position pos);

	void jouerSoldats(PanneauJeu pj);

	void mort(Soldat perso);

	void toutDessiner(Graphics g);

	/**
	 * Trouve aléatoirement un héros sur la carte
	 * 
	 * @return le héros trouvé
	 */
	Heros trouveHeros();

	/**
	 * Trouve un héros choisi aléatoirement parmi les 8 positions adjacentes de pos
	 * 
	 * @param pos la position ou chercher
	 * @return le héros trouvé
	 */
	Heros trouveHeros(Position pos);

	/**
	 * Trouve aléatoirement une position vide sur la carte
	 * 
	 * @return la position vide
	 */
	Position trouvePositionVide();

	/**
	 * Trouve une position vide choisie aléatoirement parmi les 8 positions
	 * adjacentes de pos
	 * 
	 * @param pos position ou chercher
	 * @return la position
	 */
	Position trouvePositionVide(Position pos);
}