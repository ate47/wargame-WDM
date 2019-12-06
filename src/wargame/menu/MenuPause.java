package wargame.menu;

import wargame.Wargame;

public class MenuPause extends PanelMenu {

	private static final long serialVersionUID = 4990177808655267291L;

	public MenuPause(Wargame jeu) {
		super(jeu, 3);
	}

	@Override
	public void init() {
		addButton("Reprendre", () -> jeu.showMenu(jeu.getPanneau()));
		addButton("Sauvegarder", () -> jeu.showMenu(new MenuSauvegarde(jeu)));
		addButton("Retour au menu", () -> jeu.showMenu(jeu.getMenu()));
	}

}
