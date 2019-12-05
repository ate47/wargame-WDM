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

//		 Choisir taille carte (Grand/moyen/petit)
//		 -> augmente nb ennemis/alliés
//		 Choisir pourcentage d'obstacle

		addComponend(new SelectionButtonsPanel<>(Faction.values(), "Faction du joueur", jeu.getConfig()::setFactionJoueur, jeu.getConfig()::getFactionJoueur));
		addComponend(new SelectionButtonsPanel<>(Difficulty.values(), "Difficulté", jeu.getConfig()::setDifficulty, jeu.getConfig()::getDifficulty));
		addComponend(new SelectionButtonsPanel<>(MapSize.values(), "Taille de la carte", jeu.getConfig()::setMapSize, jeu.getConfig()::getMapSize));

		addButton("Lancer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.writeConfig();
				jeu.genererCarte();
				jeu.getFrame().setContentPane(jeu.getPanneau());
				jeu.getFrame().pack();
			}
		});

		addButton("Annuler", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.setConfig(old);
				jeu.getFrame().setContentPane(jeu.getMenu());
				jeu.getFrame().pack();
			}
		});

	}
}
