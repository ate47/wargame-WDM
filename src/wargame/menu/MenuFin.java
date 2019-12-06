package wargame.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import wargame.Wargame;

public class MenuFin extends PanelMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791070228793887590L;
	
	//private JLabel finie;
	
	public MenuFin(Wargame jeu, int fin) {
		super(jeu, 4);
		
		switch(fin) {
		case Wargame.GAGNE:
			addLabel("Vous avez gagne !");
			break;
		case Wargame.PERDU:
			addLabel("Vous avez perdu !");
			break;
		default:
			addLabel("Vous etes arrivés là!");
		}
		
	
		
		
		
		addButton("Rejouer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				jeu.getFrame().setContentPane(new MenuNouvellePartie(jeu));
				jeu.getFrame().pack();
				*/
			}
		});
		
		addButton("Menu", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.getFrame().setContentPane(jeu.getMenuJeu());
				jeu.getFrame().pack();
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
