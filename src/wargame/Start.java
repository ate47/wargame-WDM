package wargame;

import javax.swing.JFrame;

import wargame.IType.Faction;

public class Start {

	public static void main(String[] args) {
		JFrame f = new JFrame("Test jeu");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		Carte carte = new Carte(IConfig.LARGEUR_CARTE, IConfig.HAUTEUR_CARTE, Faction.BLANC);
		carte.ajouteElement(new Obstacle(Obstacle.TypeObstacle.ROCHER, new Position(2, 3)));
		carte.ajouteElement(new Obstacle(Obstacle.TypeObstacle.ROCHER, new Position(3, 3)));
		carte.ajouteElement(new Obstacle(Obstacle.TypeObstacle.ROCHER, new Position(1, 3)));
		carte.ajouteElement(new Obstacle(Obstacle.TypeObstacle.ROCHER, new Position(4, 4)));
		carte.ajouteElement(new Soldat(new Position(5, 6), IType.TypeBon.HUMAIN));
		carte.ajouteElement(new Soldat(new Position(10, 15), IType.TypeBon.ELF));
		carte.jouerSoldats();
		PanneauJeu panneau = carte.getPanneau();
		panneau.setPreferredSize(f.getSize());
		f.add(panneau);
		f.pack();
		f.setVisible(true);
	}

}
