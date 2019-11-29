package wargame;

import java.util.ArrayList;
import java.util.List;

import wargame.IType.Faction;

public class Carte implements ICarte {
	public class Case extends Position implements ICase {
		private boolean visible, visite, accessible;
		private Element e;

		public Case(int x, int y) {
			super(x, y);
		}

		public Element getElement() {
			return e;
		}

		public boolean isVisible() {
			return visible;
		}

		public boolean isVisite() {
			return visite;
		}

		public boolean isAccessible() {
			return accessible;
		}

		public void setAccessible(boolean accessible) {
			this.accessible = accessible;
		}

		public void setElement(Element e) {
			this.e = e;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public void setVisite(boolean visite) {
			this.visite = visite;
		}

		@Override
		public String toString() {
			return "Case [visible=" + visible + ", visite=" + visite + ", e=" + e + "]";
		}

		@Override
		public void click() {
			if (soldat == null) {
				if (e != null && e instanceof Soldat) {
					Soldat s = (Soldat) e;
					if (s.getType().getFaction() == factionJoueur && !s.aJoueCeTour()) {
						soldat = s;
						visibles = visible(getX(), getY(), s.getType().getTir());
						for (Case v : visibles)
							if (v != null)
								v.accessible = true;
					}
				} else {
					for (Case c : visible(getX(), getY(), 4))
						if (c != null)
							c.setVisible(true);
				}
			} else if (e == null && accessible) {
				try {
					soldat.seDeplace(this);
				} catch (IllegalMoveException e1) {}
				for (Case c : visibles)
					if (c != null)
						c.accessible = false;
				soldat = null;
				visibles = null;
			} else if (e != null && e.getPosition().equals(soldat.getPosition())) {
				for (Case c : visibles)
					if (c != null)
						c.accessible = false;
				soldat = null;
				visibles = null;
			} else {
				System.err.println("Mauvais emplacement");
				// TODO: afficher erreur
			}
		}

	}

	private PanneauJeu panneau;
	private int sx, sy;
	private List<Soldat> soldatJoueur = new ArrayList<>();
	private List<Soldat> soldatEnAttente = new ArrayList<>();
	private Faction factionJoueur;
	private Case[][] carte;
	private ICase hoveredCase;
	private Case[] visibles;
	private Soldat soldat;

	public Carte(int sx, int sy, Faction factionJoueur) {
		this.sx = sx;
		this.sy = sy;
		this.carte = new Case[sx][sy];
		for (int i = 0; i < carte.length; i++)
			for (int j = 0; j < carte[i].length; j++)
				carte[i][j] = this.new Case(i, j);
		this.panneau = new PanneauJeu(this);
		this.factionJoueur = factionJoueur;
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

	@Override
	public Soldat getSoldatClick() {
		return soldat;
	}

	@Override
	public Case[] getVisibles() {
		return visibles;
	}

	@Override
	public void genererCarte() {
		int i, carre, xmin, ymin, xmax, ymax;

		for (i = 0; i < IConfig.NB_OBSTACLES; i++)
			ajouteElement(new Obstacle(trouvePositionVide()));

		carre = (int) (Math.random() * 4);
		switch (carre) {
		case 0:
			xmin = 0;
			ymin = 0;
			xmax = getLargeur() / 2;
			ymax = getHauteur() / 2;
			break;
		case 1:
			xmin = getLargeur() / 2;
			;
			ymin = 0;
			xmax = getLargeur();
			ymax = getHauteur() / 2;
			break;
		case 2:
			xmin = 0;
			ymin = getHauteur() / 2;
			xmax = getLargeur() / 2;
			ymax = getHauteur();
			break;
		default:
			xmin = getLargeur() / 2 + 1;
			ymin = getHauteur() / 2 + 1;
			xmax = getLargeur();
			ymax = getHauteur();
			break;

		}
		for (i = 0; i < IConfig.NB_HEROS; i++)
			ajouteElement(new Soldat(trouvePositionVide(xmin, ymin, xmax, ymax), Faction.BLANC.getRandomElement()));

		carre = ((int) (Math.random() * 3) + 1 + carre) % 4; // eviter le meme carre
		switch (carre) {
		case 0:
			xmin = 0;
			ymin = 0;
			xmax = getLargeur() / 2;
			ymax = getHauteur() / 2;
			break;
		case 1:
			xmin = getLargeur() / 2;
			;
			ymin = 0;
			xmax = getLargeur();
			ymax = getHauteur() / 2;
			break;
		case 2:
			xmin = 0;
			ymin = getHauteur() / 2;
			xmax = getLargeur() / 2;
			ymax = getHauteur();
			break;
		default:
			xmin = getLargeur() / 2 + 1;
			ymin = getHauteur() / 2 + 1;
			xmax = getLargeur();
			ymax = getHauteur();
			break;

		}
		for (i = 0; i < IConfig.NB_MONSTRES; i++)
			ajouteElement(new Soldat(trouvePositionVide(xmin, ymin, xmax, ymax), Faction.VERT.getRandomElement()));

		jouerSoldats();
	}

	@Override
	public Case getCase(int posX, int posY) {
		if (posX < 0 || posX >= carte.length || posY < 0 || posY >= carte[posX].length)
			return null;
		return carte[posX][posY];
	}

	@Override
	public Element getElement(int posX, int posY) {
		return getCase(posX, posY).getElement();
	}

	@Override
	public int getHauteur() {
		return sy;
	}

	/**
	 * @return the hoveredCase
	 */
	public ICase getHoveredCase() {
		return hoveredCase;
	}

	@Override
	public int getLargeur() {
		return sx;
	}

	@Override
	public PanneauJeu getPanneau() {
		return panneau;
	}

	public int nombreVisible(int portee) {
		return 3 * portee * (portee + 1) + 1;
	}

	public Case[] visible(int x, int y, int portee) {
		Case[] cases = new Case[nombreVisible(portee)];
		return visible(x, y, portee, cases);
	}

	public Case[] visible(int x, int y, int portee, Case[] cases) {
		int i, j, k, l = 0, decalage;
		decalage = 2 * ((y + portee + 1) % 2) - 1;
		
		// milieu
		for (i = 0; i <= portee; i++)
			for (j = -portee; j <= portee; j++)
				cases[l++] = getCase(x + decalage * (i - portee / 2), y + j);
		
		// droite
		for (i = portee / 2 + 2, k = portee * 2 - 3; k > 2; k -= 4, i++)
			for (j = -k / 2; j <= k / 2; j++)
				cases[l++] = getCase(x + decalage * i, y + j);

		// gauche
		for (i = portee / 2 + 1, k = portee * 2 - 1; k > 0; k -= 4, i++)
			for (j = -k / 2; j <= k / 2; j++)
				cases[l++] = getCase(x - decalage * i, y + j);

		return cases;
	}

	@Override
	public void jouerSoldats() {
		int i, j;
		for (i = 0; i < carte.length; i++)
			for (j = 0; j < carte[i].length; j++)
				carte[i][j].setVisible(false);
		// 0 le BrG
		for (Soldat s : soldatEnAttente)
			s.joueTour();

		for (Soldat s : soldatJoueur) {
			for (Case c : visible(s.getPosition().getX(), s.getPosition().getY(), s.getType().getPorteeVisuelle()))
				if (c != null) {
					c.setVisible(true);
					c.setVisite(true);
				}

		}
		soldatEnAttente.clear();
		panneau.repaint();
	}

	@Override
	public void mort(Soldat soldat) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param hoveredCase the hoveredCase to set
	 */
	public void setHoveredCase(ICase hoveredCase) {
		this.hoveredCase = hoveredCase;
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

	public Position trouvePositionVide(int xmin, int ymin, int xmax, int ymax) {
		int x, y;
		Case c;

		while (true) {
			x = (int) (Math.random() * (xmax - xmin)) + xmin;
			y = (int) (Math.random() * (ymax - ymin)) + ymin;

			c = getCase(x, y);
			if (c.getElement() == null) {
				return new Position(x, y);
			}
		}
	}

	@Override
	public Position trouvePositionVide(Position pos) {
		// TODO Auto-generated method stub
		return null;
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
	public void clickSurPosition(int x, int y) {
		getCase(x, y).click();
	}

}
