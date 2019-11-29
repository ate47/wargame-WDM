package wargame;

import wargame.Carte.Case;
import wargame.IType.Faction;

public interface ICarte {
	interface ICase {

		boolean isAccessible();

		boolean isVisible();

		void setVisible(boolean visible);

		boolean isVisite();

		void setVisite(boolean visite);

		Element getElement();

		void setElement(Element e);

		int getX();

		int getY();

		void click();
	}

	/**
	 * @param pos la position de l'élement
	 * @return l'élement sur cette position
	 */
	default Element getElement(Position pos) {
		return getElement(pos.getX(), pos.getY());
	}

	Soldat getSoldatClick();

	Case[] getVisibles();

	int nombreVisible(int portee);

	Case[] visible(int x, int y, int portee);

	Case[] visible(int x, int y, int portee, Case[] cases);

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

	/**
	 * génére une nouvelle carte aléatoirement
	 */
	void genererCarte();

	/**
	 * @param hoveredCase the hoveredCase to set
	 */
	void setHoveredCase(ICase hoveredCase);

	/**
	 * @return the hoveredCase
	 */
	ICase getHoveredCase();

	void clickSurPosition(int x, int y);
}