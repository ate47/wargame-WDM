package wargame;

import java.util.ArrayList;
import java.util.List;

import wargame.IType.Faction;

public class Carte implements ICarte {
	public static class Case implements ICase {
		private boolean visible, visite;
		private Element e;
		
		public boolean isVisible() {
			return visible;
		}
		
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		
		public boolean isVisite() {
			return visite;
		}
		
		public void setVisite(boolean visite) {
			this.visite = visite;
		}
		
		public Element getElement() {
			return e;
		}
		
		public void setElement(Element e) {
			this.e = e;
		}
	}
	
	private PanneauJeu panneau;
	private int sx, sy;
	private List<Soldat> soldatJoueur = new ArrayList<>();
	private List<Soldat> soldatEnAttente = new ArrayList<>();
	private Faction factionJoueur;
	private Case[][] carte;
	private ICase hoveredCase;
	
	public Carte(int sx, int sy, Faction factionJoueur) {
		this.sx = sx;
		this.sy = sy;
		this.carte = new Case[sx][sy];
		for (int i = 0; i < carte.length; i++)
			for (int j = 0; j < carte[i].length; j++)
				carte[i][j] = new Case();
		this.panneau = new PanneauJeu(this);
		this.factionJoueur = factionJoueur;
	}
	
	@Override
	public boolean joue(Position pos1, Position pos2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Element getElement(int posX, int posY) {
		return getCase(posX, posY).getElement();
	}
	
	@Override
	public Case getCase(int posX, int posY) {
		if (posX < 0 || posX >= carte.length || posY < 0 || posY >= carte[posX].length)
			return null;
		return carte[posX][posY];
	}
	
	@Override
	public void genererCarte() {
		int i;
		
		for (i = 0; i < IConfig.NB_OBSTACLES; i++)
			ajouteElement(new Obstacle(trouvePositionVide()));
		
		for (i = 0; i < IConfig.NB_HEROS; i++)
			ajouteElement(new Soldat(trouvePositionVide(), Faction.BLANC.getRandomElement()));
		
		for (i = 0; i < IConfig.NB_MONSTRES; i++)
			ajouteElement(new Soldat(trouvePositionVide(), Faction.VERT.getRandomElement()));
		
		
		jouerSoldats();
	}
	
	@Override
	public void jouerSoldats() {
		Case c;
		int i, j, k, x, y, portee, decalage;
		for (i = 0; i < carte.length; i++)
			for (j = 0; j < carte[i].length; j++)
				carte[i][j].setVisible(false);
		// 0 le BrG
		for (Soldat s : soldatEnAttente)
			s.joueTour();
		
		for (Soldat s : soldatJoueur) {
			x = s.getPosition().getX();
			y = s.getPosition().getY();
			portee = s.getType().getPorteeVisuelle();
			decalage = 2 * (y % 2) - 1;
			for (i = 0; i <= portee; i++)
				for (j = -portee; j <= portee; j++) {
					c = getCase(x + decalage * (i - portee / 2), y + j);
					if (c != null) {
						c.setVisible(true);
						c.setVisite(true);
					}
				}
			for (i = portee / 2 + 2, k = portee * 2 - 3; k > 2; k -= 4, i++)
				for (j = -k / 2; j <= k / 2; j++) {
					c = getCase(x + decalage * i, y + j);
					if (c != null) {
						c.setVisible(true);
						c.setVisite(true);
					}
				}
			
			for (i = portee / 2 + 1, k = portee * 2 - 1; k > 0; k -= 4, i++)
				for (j = -k / 2; j <= k / 2; j++) {
					c = getCase(x - decalage * i, y + j);
					if (c != null) {
						c.setVisible(true);
						c.setVisite(true);
					}
				}
			
			getCase(x, y).setVisible(true);
		}
		soldatEnAttente.clear();
		panneau.repaint();
	}
	
	@Override
	public void mort(Soldat soldat) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Soldat trouveSoldat(Faction f) {
		return soldatJoueur.get((int) (soldatJoueur.size() * Math.random()));
	}
	
	@Override
	public Soldat trouveSoldat(Position pos, Faction f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Position trouvePositionVide() {
		int x, y;
		Case c;
		
		while (true) {
			x = (int) (Math.random() * sx);
			y = (int) (Math.random() * sy);
			c = getCase(x, y);
			if (c.getElement() == null) {
				return new Position(x, y);
			}
		}
	}
	/**
	 * @param hoveredCase the hoveredCase to set
	 */
	public void setHoveredCase(ICase hoveredCase) {
		this.hoveredCase = hoveredCase;
	}

	/**
	 * @return the hoveredCase
	 */
	public ICase getHoveredCase() {
		return hoveredCase;
	}

	@Override
	public Position trouvePositionVide(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PanneauJeu getPanneau() {
		return panneau;
	}
	
	@Override
	public int getLargeur() {
		return sx;
	}
	
	@Override
	public int getHauteur() {
		return sy;
	}
	
	@Override
	public void ajouteElement(Element e) {
		carte[e.getPosition().getX()][e.getPosition().getY()].setElement(e);
		if (e instanceof Soldat) {
			Soldat s = (Soldat) e;
			if (((Soldat) e).getType().getFaction() == factionJoueur)
				soldatJoueur.add(s);
		}
	}
	
}
