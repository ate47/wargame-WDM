package wargame.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JLabel;
import javax.swing.JPanel;

import wargame.ListenerAdapter;
import wargame.assets.IIconObject;

/**
 * Panel de selection d'objet
 *
 * @param <T>
 *            le type de l'objet
 */
public class SelectionButtonsPanel<T extends IIconObject> extends JPanel implements ListenerAdapter {
	private class OnClickListener implements ActionListener {
		T t;

		OnClickListener(T t) {
			this.t = t;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			buttons[getter.get().ordinal()].setUtilise(false);
			setter.accept(t);
			resizeButtons();
		}

	}

	private static final long serialVersionUID = 2208755067890561700L;
	private Consumer<T> setter;
	private Supplier<T> getter;
	private MenuButton[] buttons;
	private JLabel label;

	/**
	 * Construit un selecteur
	 * 
	 * @param array
	 *            le tableau des elements a sauvegardé
	 * @param name
	 *            le nom a affiché
	 * @param setter
	 *            comment definir l'element
	 * @param getter
	 *            comment obtenir l'element courant
	 */
	public SelectionButtonsPanel(T[] array, String name, Consumer<T> setter, Supplier<T> getter) {
		this.buttons = new MenuButton[array.length];
		this.setter = setter;
		this.getter = getter;

		for (int i = 0; i < array.length; i++) {
			add(this.buttons[i] = new MenuButton("", array[i].getIcon(), array[i].getIconHover()));
			this.buttons[i].addActionListener(new OnClickListener(array[i]));
		}

		label = new JLabel(name);
		add(label);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		setOpaque(false);
		resizeButtons();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		resizeButtons();
	}

	private void resizeButtons() {
		int size = 4 * getHeight() / 5;
		int decalage = size / 10;
		int top = getHeight() - size;
		int left = (getWidth() - size * buttons.length - decalage * (buttons.length - 1)) / 2;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setBounds(left + (size + decalage) * i, top, size, size);
		}
		buttons[getter.get().ordinal()].setUtilise(true);
		label.setBounds(0, 0, getWidth(), getHeight() - size - 3);
		addComponentListener(this);
	}

}
