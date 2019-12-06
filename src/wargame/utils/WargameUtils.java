package wargame.utils;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;

/**
 * Méthodes utiles pour le jeu
 */
public class WargameUtils {

	/**
	 * dessine la chaine s centrée en (x,y)
	 * 
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
	 * 
	 * @param array le tableau où chercher
	 * @param seed  l'indice modulo la taille du tableau dans le tableau
	 * @return un element du tableau
	 * @param <T> le type du tableau
	 */
	public static <T> T getFromSeed(T[] array, int seed) {
		int index = seed % array.length;

		if (index < 0)
			index += array.length;

		return array[index % array.length];
	}

	private static Object arrayCloneSub0(Object array, int size, int dimension) {
		Object[] t = (Object[]) Array.newInstance(array.getClass().getComponentType(), size);
		Object[] sub = (Object[]) array;
		if (dimension == 1) {
			int i;
			for (i = 0; i < t.length; i++)
				t[i] = sub[i];
		} else {
			for (int i = 0; i < dimension; i++)
				t[i] = arrayCloneSub0(sub[i], size, dimension - 1);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] arrayCloneSub(T[] array, int size, int dimension) {
		return array == null ? null : (T[]) arrayCloneSub0(array, size, dimension);
	}

	public static Object readObjectFromFile(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			ObjectInputStream ois = new ObjectInputStream(in);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

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

	private WargameUtils() {}

}
