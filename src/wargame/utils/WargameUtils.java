package wargame.utils;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Méthodes utiles pour le jeu
 */
public class WargameUtils {

	/**
	 * dessine la chaine s centrée en (x,y)
	 * 
	 * @param g
	 *            le graphics où dessiner
	 * @param x
	 *            coord x où dessiner
	 * @param y
	 *            coord y où dessiner
	 * @param s
	 *            la chaine à dessiner
	 */
	public static void drawCenter(Graphics g, int x, int y, String s) {
		Rectangle2D text = g.getFontMetrics(g.getFont()).getStringBounds(s, g);
		g.drawString(s, x - (int) (text.getWidth() / 2), y - 2 + (int) (text.getHeight() / 2));
	}

	/**
	 * recherche un element dans un tableau modulo sa taille
	 * 
	 * @param array
	 *            le tableau où chercher
	 * @param seed
	 *            l'indice modulo la taille du tableau dans le tableau
	 * @return un element du tableau
	 * @param <T>
	 *            le type du tableau
	 */
	public static <T> T getFromSeed(T[] array, int seed) {
		int index = seed % array.length;

		if (index < 0)
			index += array.length;

		return array[index % array.length];
	}

	/**
	 * Vrai si la souris est dans un hexagone de taille (x, y, w, h) faux sinon
	 * 
	 * @param mouseX
	 *            la position de la souris X
	 * @param mouseY
	 *            la position de la souris Y
	 * @param x
	 *            coord x
	 * @param y
	 *            coord y
	 * @param w
	 *            largeur
	 * @param h
	 *            hauteur
	 * @return vrai si la souris est dans l'hexagone, faux sinon
	 */
	public static boolean isInHexa(int mouseX, int mouseY, int x, int y, int w, int h) {
		if (mouseX > x && mouseY > y && mouseX < x + w && mouseY < y + h) {
			int rMouseY = mouseY - y;
			if (rMouseY < w * 2 / 3)
				if (rMouseY > w / 3)
					return true;
				else
					rMouseY = w / 3 - rMouseY;
			int rMouseX = Math.abs(mouseX - x - w / 2);
			return rMouseY < -h * rMouseX / w + h;
		}
		return false;
	}

	/**
	 * Lire un {@link Serializable} depuis un fichier
	 * 
	 * @param file
	 *            le fichier où lire
	 * @return l'objet ou null si il y a eu une erreur
	 */
	public static Object readObjectFromFile(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			ObjectInputStream ois = new ObjectInputStream(in);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Ecrire un {@link Serializable} dans un fichier
	 * 
	 * @param file
	 *            le fichier où écrire
	 * @param obj
	 *            l'objet à écrire
	 */
	public static boolean saveObjectToFile(File file, Object obj) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private WargameUtils() {
	}

}
