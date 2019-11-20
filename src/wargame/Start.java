package wargame;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import wargame.IType.Faction;

import java.io.IOException;

public class Start {
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Wargame");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationByPlatform(true);
		f.setSize(800, 600);
		Carte carte = new Carte(IConfig.LARGEUR_CARTE, IConfig.HAUTEUR_CARTE, Faction.BLANC);
		carte.genererCarte();
		PanneauJeu panneau = carte.getPanneau();
		panneau.setPreferredSize(f.getSize());
		f.add(panneau);
		try {
			f.setIconImage(ImageIO.read(Carte.class.getResourceAsStream("ico.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		f.pack();
		f.setVisible(true);
	}
	
}
