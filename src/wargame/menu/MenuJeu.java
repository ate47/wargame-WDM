package wargame.menu;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;
import wargame.assets.ImageAsset;

public class MenuJeu extends PanelMenu {
	private static final ImageAsset LOGO = new ImageAsset("logo.png");
	private static final long serialVersionUID = 4058740506271457913L;

	public MenuJeu(Wargame jeu) {
		super(jeu, 3);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int sizeX = getWidth() / 3;
		int sizeY = sizeX * 2 / 5;
		int decalage = getHeight() / 50;
		g.drawImage(LOGO.getImageFromTime(), (getWidth() - sizeX) / 2, getHeight() / 3 - (sizeY + decalage), sizeX,
				sizeY, this);
	}

	@Override
	public void init() {
		PanneauRestauration restauration = new PanneauRestauration(jeu);

		addButton("Nouvelle Partie", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(new MenuNouvellePartie(jeu));
			}
		});
		addButton("Charger une partie", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.showMenu(restauration);
			}
		});
		addButton("Quitter le jeu", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

}
