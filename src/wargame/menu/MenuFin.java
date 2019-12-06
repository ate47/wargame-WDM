package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;
import wargame.Wargame.FinJeu;

public class MenuFin extends PanelMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791070228793887590L;

	public MenuFin(Wargame jeu) {
		super(jeu, 4);
	}
	@Override
	public void init() {
		addLabel(jeu.getCourant() == FinJeu.GAGNE ? "Vous avez gagne !" : "Vous avez perdu !");

		addButton("Rejouer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(new MenuNouvellePartie(jeu));
			}
		});

		addButton("Menu", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(jeu.getMenu());
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
