package wargame;

import wargame.Wargame.Case;
import wargame.config.Faction;

/**
 * Represente une carte de jeu
 */
public interface ICarte {

	/**
	 * ajoute l'element e sur la carte
	 * 
	 * @param e
	 *            l'element
	 */
	void ajouteElement(int x, int y, Element e);

	/**
	 * click sur une position
	 * 
	 * @param x
	 *            coord x de la position
	 * @param y
	 *            coord y de la position
	 */
	default void clickSurPosition(int x, int y) {
		getCase(x, y).click();
	}

	/**
	 * génére une nouvelle carte aléatoirement
	 */
	void genererCarte();

	/**
	 * retourne la case en position (posX, posY)
	 * 
	 * @param posX
	 *            posX
	 * @param posY
	 *            posY
	 * @return l'element ou null si absent
	 */
	ICase getCase(int posX, int posY);

	/**
	 * retrouve la position d'une case avec sa position relative
	 * 
	 * @param relativePosX
	 *            la position relative X
	 * @param relativePosY
	 *            la position relative X
	 * @param unit
	 *            l'unit� de taille
	 * @return la case ou null si absente
	 */
	ICase getRelativeCase(int relativePosX, int relativePosY, int unit);

	/**
	 * retourne l'élement en position (posX, posY)
	 * 
	 * @param posX
	 *            posX
	 * @param posY
	 *            posY
	 * @return l'element ou null si absent
	 */
	Element getElement(int posX, int posY);

	/**
	 * @param pos
	 *            la position de l'élement
	 * @return l'élement sur cette position
	 */
	default Element getElement(ICase pos) {
		return getElement(pos.getX(), pos.getY());
	}

	/**
	 * @return la hauteur de la carte
	 */
	int getHauteur();

	/**
	 * @return la case en dessous de la souris
	 */
	ICase getHoveredCase();

	/**
	 * @return la largeur de la carte
	 */
	int getLargeur();

	/**
	 * @return le panneau d'affichage
	 */
	PanneauJeu getPanneau();

	/**
	 * @return le soldat cliqué (null si aucun n'a été cliqué)
	 */
	Soldat getSoldatClick();

	/**
	 * @return les cases visibles pour le soldat cliqué
	 */
	Case[] getVisibles();

	/**
	 * @return le tableau des soldats du Joueur.
	 */
	ISoldat[] getSoldatJoueur();

	/**
	 * @return le tableau des soldats de l'ennemi.
	 */
	ISoldat[] getSoldatEnnemis();

	/**
	 * Joue tous les coups de ce tour
	 */
	void jouerSoldats();

	/**
	 * Tue un soldat
	 * 
	 * @param soldat
	 *            le soldat
	 */
	void mort(Soldat soldat);

	/**
	 * retourne le nombre de cases visible pour une certaine portée
	 * 
	 * @param portee
	 *            la portée à regarder
	 * @return le nombre de cases visible pour une certaine portée
	 */
	int nombreVisible(int portee);

	/**
	 * @param hoveredCase
	 *            the hoveredCase to set
	 */
	void setHoveredCase(ICase hoveredCase);

	/**
	 * Trouve aléatoirement une position vide sur la carte
	 * 
	 * @return la position vide
	 */
	ICase trouvePositionVide();

	/**
	 * Trouve une position vide choisie aléatoirement parmi les 8 positions
	 * adjacentes de pos
	 * 
	 * @param pos
	 *            position ou chercher
	 * @return la position
	 */
	ICase trouvePositionVide(ICase pos);

	/**
	 * Trouve aléatoirement un soldat de la faction f
	 * 
	 * @param f
	 *            la faction a trouvé
	 * @return le héros trouvé
	 */
	ISoldat trouveSoldat(Faction f);

	/**
	 * retourne vrai si la travers� entre les deux points est valide (qu'il n'y a
	 * pas d'objet entre le passage)
	 * 
	 * @param pos1
	 *            position 1
	 * @param pos2
	 *            position 2
	 * @return vrai si rien ne bloque le chemin, faux sinon
	 */
	boolean traverseValide(ICase pos1, ICase pos2);

	/**
	 * alloue un tableau de case suffisament grand et appel la méthode
	 * {@link ICarte#visible(int, int, int, Case[])}
	 * 
	 * @param x
	 *            coord x de la position
	 * @param y
	 *            coord y de la position
	 * @param portee
	 *            la portee a regarder
	 * @param colision
	 *            si on calcul les colisions
	 * @return un tableau cases contenant des null si une case est hors limite
	 * @see ICarte#visible(int, int, int, Case[])
	 * @see ICarte#nombreVisible(int)
	 */
	ICase[] visible(int x, int y, int portee, boolean colision);

	/**
	 * cherche les cases visibles depuis une position suivant une certaine portée
	 * 
	 * @param x
	 *            coord x de la position
	 * @param y
	 *            coord y de la position
	 * @param portee
	 *            la portee a regarder
	 * @param cases
	 *            le tableau où stocker les cases, il doit faire au moins la taille
	 *            decrite avec {@link ICarte#nombreVisible(int)}
	 * @param colision
	 *            si on calcul les colisions
	 * @return le tableau cases contenant des null si une case est hors limite
	 * @throws IndexOutOfBoundsException
	 *             si le tableau est trop petit
	 * @see ICarte#visible(int, int, int)
	 * @see ICarte#nombreVisible(int)
	 */
	ICase[] visible(int x, int y, int portee, boolean colision, ICase[] cases);
}