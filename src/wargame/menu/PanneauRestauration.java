package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;
import wargame.config.SavedConfig;

public class PanneauRestauration extends PanelMenu {
	private class SaveActionListener implements ActionListener {
		SavedConfig sc;

		SaveActionListener(SavedConfig sc) {
			this.sc = sc;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			jeu.lancerConfig(sc.getConfig());
		}
	}

	private static final long serialVersionUID = 3092784374487373890L;

	public PanneauRestauration(Wargame jeu) {
		super(jeu, 1 + Wargame.MAX_SAVE);
	}

	@Override
	public void init() {
		SavedConfig sc;
		for (int i = 1; i <= Wargame.MAX_SAVE; i++) {
			sc = jeu.getSave()[i - 1];
			if (sc.isActive())
				addButton("Sauvegarde " + i, new SaveActionListener(sc));
			else
				addButton("Sauvegarde " + i, () -> {}).setEnabled(false);
		}
		addButton("Retour", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(jeu.getMenu());
			}
		});
	}
}
