package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.IConfig;
import wargame.Wargame;

public class MenuNouvellePartie extends PanelMenu {

	private static final long serialVersionUID = 9098817428626585291L;

	public MenuNouvellePartie(Wargame jeu) {
		super(jeu, 3);
		IConfig old = jeu.getConfig().clone();
		
//		 Choisir faction
//		 Choisir diff
//		 -> nb ennemis
//		 -> pdv par tour gagnés
//		 Choisir taille carte (Grand/moyen/petit)
//		-> augmente nb ennemis/alliés
//		 Choisir pourcentage d'obstacle
		
		addButton("Lancer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
