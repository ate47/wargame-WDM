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
		super(jeu, 4);
		PanneauRestauration restauration = new PanneauRestauration(jeu);
		
		addButton("Nouvelle Partie", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.getFrame().setContentPane(new MenuNouvellePartie(jeu));
				jeu.getFrame().pack();
			}
		});
		addButton("Sauvegarder la partie", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.getFrame().setContentPane(new MenuFin(jeu, false));
				jeu.getFrame().pack();
			}
		});
		addButton("Charger une partie", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jeu.getFrame().setContentPane(restauration);
				jeu.getFrame().pack();
			}
		});
		addButton("Quitter le jeu", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
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

}
