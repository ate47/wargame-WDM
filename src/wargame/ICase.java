package wargame;

import java.io.Serializable;

/**
 * Represente une case de la carte de jeu
 */
public interface ICase extends Serializable, Cloneable {

	/**
	 * Appelé quand l'utilisateur clique sur cette case
	 */
	void click();

	/**
	 * @return un clone de la case
	 */
	ICase clone();

	/**
	 * @return l'élément sur la case
	 * @see ICarte.ICase#setElement(Element)
	 */
	Element getElement();

	int getRelativeX(int unit);

	int getRelativeY(int unit);

	/**
	 * @return la position x de la case
	 */
	int getX();

	/**
	 * @return la position y de la case
	 */
	int getY();

	/**
	 * @return si la case est accessible
	 */
	boolean isAccessible();

	/**
	 * @return si on peut attaquer cette case
	 */
	boolean isTirable();

	/**
	 * 
	 * @return si la case est visible
	 * @see ICase#setVisible(boolean)
	 */
	boolean isVisible();

	/**
	 * @return si la case a été visité
	 * @see ICase#setVisite(boolean)
	 */
	boolean isVisite();

	/**
	 * definir si la case est accessible
	 * 
	 * @param accessible
	 *            l'accessibilit�
	 */
	void setAccessible(boolean accessible);

	/**
	 * definir l'élement sur la case
	 * 
	 * @param e
	 *            l'element a placer sur la case
	 * @see ICarte.ICase#getElement()
	 */
	void setElement(Element e);

	/**
	 * definir si on peut attaquer cette case
	 * 
	 * @param tirable
	 *            si la case est attaquable
	 */
	void setTirable(boolean tirable);

	/**
	 * 
	 * définir si la case est visible
	 * 
	 * @param visible
	 *            vrai ou faux
	 * @see ICase#isVisible()
	 */
	void setVisible(boolean visible);

	/**
	 * définir si la case est visitée
	 * 
	 * @param visite
	 *            vrai ou faux
	 * @see ICase#isVisite()
	 */
	void setVisite(boolean visite);

	/**
	 * cherche les cases visibles depuis cette position suivant une certaine portée
	 * 
	 * @param portee
	 *            la portée
	 * @param colision
	 *            si on calcul les colisions
	 * @return la teableau des positions visibles
	 */
	ICase[] visible(int portee, boolean colision);
}
