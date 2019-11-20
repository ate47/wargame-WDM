package wargame;

import wargame.Carte.Case;
import wargame.IType.Faction;

public interface ICarte {
	interface ICase {
		public boolean isVisible();

		public void setVisible(boolean visible);

		public boolean isVisite();

		public void setVisite(boolean visite);

		public Element getElement();

		public void setElement(Element e);
	}

	/**
	 * joue le soldat en pos1 en pos2
	 * 
	 * @param pos1 position du joueur
	 * @param pos2 position ou jouer
	 * @return si le coup est valide
	 */
	boolean joue(Position pos1, Position pos2);

	/**
	 * @param pos la position de l'élement
	 * @return l'élement sur cette position
	 */
	default Element getElement(Position pos) {
		return getElement(pos.getX(), pos.getY());
	}

	/**
	 * retourne l'élement en position (posX, posY)
	 * 
	 * @param posX posX
	 * @param posY posY
	 * @return l'element ou null si absent
	 */
	Element getElement(int posX, int posY);

	/**
	 * retourne la case en position (posX, posY)
	 * 
	 * @param posX posX
	 * @param posY posY
	 * @return l'element ou null si absent
	 */
	ICase getCase(int posX, int posY);

	/**
	 * ajoute l'element e sur la carte
	 * 
	 * @param e l'element
	 */
	void ajouteElement(Element e);

	/**
	 * Joue tous les coups de ce tour
	 */
	void jouerSoldats();

	/**
	 * Tue un soldat
	 * 
	 * @param soldat le soldat
	 */
	void mort(Soldat soldat);

	/**
	 * Trouve aléatoirement un soldat de la faction f
	 * 
	 * @param f la faction a trouvé
	 * @return le héros trouvé
	 */
	Soldat trouveSoldat(Faction f);

	/**
	 * Trouve un héros choisi aléatoirement parmi les 8 positions adjacentes de pos
	 * 
	 * @param pos la position ou chercher
	 * @return le héros trouvé
	 */
	Soldat trouveSoldat(Position pos, Faction f);

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

	/**
	 * @return
	 */
	PanneauJeu getPanneau();

	/**
	 * @return la largeur de la carte
	 */
	int getLargeur();

	/**
	 * @return la hauteur de la carte
	 */
	int getHauteur();
}