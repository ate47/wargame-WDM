package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;
import wargame.config.Difficulty;
import wargame.config.Faction;
import wargame.config.IConfig;
import wargame.config.MapSize;

public class MenuNouvellePartie extends PanelMenu {

	private static final long serialVersionUID = 9098817428626585291L;

	protected final Wargame jeu;

	public MenuNouvellePartie(Wargame jeu) {
		super(jeu, 5);
		this.jeu = jeu;
		IConfig old = jeu.getConfig().clone();

		addComponent(new SelectionButtonsPanel<>(Faction.values(), "Faction du joueur", jeu.getConfig()::setFactionJoueur, jeu.getConfig()::getFactionJoueur));
		addComponent(new SelectionButtonsPanel<>(Difficulty.values(), "Difficulté", jeu.getConfig()::setDifficulty, jeu.getConfig()::getDifficulty));
		addComponent(new SelectionButtonsPanel<>(MapSize.values(), "Taille de la carte", jeu.getConfig()::setMapSize, jeu.getConfig()::getMapSize));

		addButton("Lancer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.writeConfig();
				jeu.genererCarte();
				jeu.showMenu(jeu.getPanneau());
			}
		});

		addButton("Annuler", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.setConfig(old);
				jeu.showMenu(jeu.getMenu());
			}
		});

	}
}
