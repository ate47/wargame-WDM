package wargame.config;

import wargame.Element;
import wargame.ICase;
import wargame.Wargame;
import wargame.Wargame.Case;

public class ConfigCase {
	private Element element;
	private int x, y;

	public ConfigCase(ICase c) {
		element = c.getElement();
		x = c.getX();
		y = c.getY();
	}

	public Case toCase(Wargame jeu) {
		Case c = new Case(x, y);
		c.setElement(element);
		return c;
	}
}
