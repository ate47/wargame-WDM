package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;
import wargame.config.SavedConfig;

public class MenuSauvegarde extends PanelMenu {

	private class SaveActionListener implements ActionListener {
		int index;

		SaveActionListener(int index) {
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			jeu.getSave()[index].setConfig(jeu.getConfig().clone());
			jeu.writeConfig();
			jeu.showMenu(jeu.getMenuPause());
		}
	}

	private static final long serialVersionUID = 3092784374487373890L;

	public MenuSauvegarde(Wargame jeu) {
		super(jeu, 1 + Wargame.MAX_SAVE);
	}

	@Override
	public void init() {
		SavedConfig sc;
		for (int i = 1; i <= Wargame.MAX_SAVE; i++) {
			sc = jeu.getSave()[i - 1];
			addButton("Sauvegarde " + i + (sc.isActive() ? " (Non vide)" : " (Vide)"), new SaveActionListener(i));
		}
		addButton("Retour", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(jeu.getMenuPause());
			}
		});
	}
}
