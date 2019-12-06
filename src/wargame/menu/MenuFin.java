package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import wargame.Wargame;

public class MenuFin extends PanelMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4791070228793887590L;
	
	private JLabel finie;
	
	public MenuFin(Wargame jeu, int fin) {
		super(jeu, 2);
		
		switch(fin) {
		case Wargame.GAGNE:
			finie = new JLabel("Vous avez gagné !");
			break;
		case Wargame.PERDU:
			finie = new JLabel("Perdu !");
			break;
		default:
			finie = new JLabel("Tricheur !");
		}

		addButton("Rejouer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.getFrame().setContentPane(new MenuNouvellePartie(jeu));
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
