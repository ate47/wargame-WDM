package wargame.config;

import wargame.Element;
import wargame.ICase;
import wargame.Wargame.Case;

/**
 * Case dans une configuration interne
 */
public class ConfigCase implements Cloneable {
	private Element element;
	private int x, y;

	private ConfigCase(ConfigCase old) {
		element = old.element == null ? null : old.element.clone();
		x = old.x;
		y = old.y;
	}

	/**
	 * Convertir une case en case de configuration
	 * 
	 * @param c
	 *            la case à convertir
	 */
	public ConfigCase(ICase c) {
		element = c.getElement();
		x = c.getX();
		y = c.getY();
	}

	/**
	 * @return une nouvelle case sans copie de l'élement
	 */
	public Case toCase() {
		Case c = new Case(x, y);
		c.setElement(element);
		return c;
	}

	/**
	 * @return l'element de la case
	 */
	public Element getElement() {
		return element;
	}

	@Override
	public ConfigCase clone() {
		return new ConfigCase(this);
	}
}
