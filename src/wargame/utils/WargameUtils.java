package wargame.utils;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * Méthodes utiles pour le jeu
 */
public class WargameUtils {

	/**
	 * dessine la chaine s centrée en (x,y)
	 * @param g le graphics où dessiner
	 * @param x coord x où dessiner
	 * @param y coord y où dessiner
	 * @param s la chaine à dessiner
	 */
	public static void drawCenter(Graphics g, int x, int y, String s) {
		Rectangle2D text = g.getFontMetrics(g.getFont()).getStringBounds(s, g);
		g.drawString(s, x - (int) (text.getWidth() / 2), y - 2 + (int) (text.getHeight() / 2));
	}
	
	/**
	 * recherche un element dans un tableau modulo sa taille
	 * @param array le tableau où chercher
	 * @param seed l'indice modulo la taille du tableau dans le tableau
	 * @return un element du tableau
	 * @param <T> le type du tableau
	 */
	public static <T> T getFromSeed(T[] array, int seed) {
		int index = seed % array.length;
		
		if (index < 0)
			index += array.length;
		
		return array[index % array.length];
	}
	
	private WargameUtils() {
	}
	
}
