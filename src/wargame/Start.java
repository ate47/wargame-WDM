package wargame;

import wargame.IType.Faction;

public class Start {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Wargame jeu = new Wargame(IConfig.LARGEUR_CARTE, IConfig.HAUTEUR_CARTE, Faction.VERT);
		jeu.getFrame().setVisible(true);
	}
	
	
}
