package wargame.config;

import wargame.Element;
import wargame.ICase;
import wargame.Wargame.Case;

public class ConfigCase implements Cloneable {
	private Element element;
	private int x, y;

	private ConfigCase(ConfigCase old) {
		element = old.element == null ? null : old.element.clone();
		x = old.x;
		y = old.y;
	}

	public ConfigCase(ICase c) {
		element = c.getElement();
		x = c.getX();
		y = c.getY();
	}

	public Case toCase() {
		Case c = new Case(x, y);
		c.setElement(element);
		return c;
	}

	public Element getElement() {
		return element;
	}

	@Override
	public ConfigCase clone() {
		return new ConfigCase(this);
	}
}
