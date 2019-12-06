package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;

public class MenuFin extends PanelMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791070228793887590L;

	// private JLabel finie;

	public MenuFin(Wargame jeu, boolean gagne) {
		super(jeu, 4);

		addLabel(gagne ? "Vous avez gagne !" : "Vous avez perdu !");

		addButton("Rejouer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(new MenuNouvellePartie(jeu));
			}
		});

		addButton("Menu", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(jeu.getMenuJeu());
			}
		});

		addButton("Quitter", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
