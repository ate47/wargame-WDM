package wargame.config;

import java.io.Serializable;

import wargame.ICase;
import wargame.Soldat;
import wargame.Wargame.FinJeu;

public class Config implements IConfig, Serializable {
	private static final long serialVersionUID = 9194042623694545614L;
	private static final int vieParRegen = 70;
	private boolean showFps = false;
	private int largeurCarte = 25;
	private int hauteurCarte = 30;
	private float pourcentageObstacle = .25F;
	private Faction factionJoueur = Faction.BLANC;
	private Difficulty difficulty = Difficulty.NORMAL;
	private MapSize mapSize = MapSize.NORMAL;

	private Faction factionEnnemi = Faction.VERT;
	private FinJeu courant = FinJeu.EN_COURS;
	private Soldat[] soldatJoueur;
	private Soldat[] soldatEnnemis;
	private ICase[][] carte;

	public Config() {
		soldatJoueur = new Soldat[factionJoueur.nombreGenere()];
		soldatEnnemis = new Soldat[factionEnnemi.nombreGenere()];
		carte = new ICase[largeurCarte][hauteurCarte];
	}

	private Config(Config old) {
		this.showFps = old.showFps;
		this.largeurCarte = old.largeurCarte;
		this.hauteurCarte = old.hauteurCarte;
		this.pourcentageObstacle = old.pourcentageObstacle;
		this.factionJoueur = old.factionJoueur;
		this.difficulty = old.difficulty;
		this.mapSize = old.mapSize;
		this.factionEnnemi = old.factionEnnemi;
		this.courant = old.courant;
		int i, nb = 0;
		for (i = 0; i < old.soldatJoueur.length; i++)
			if (old.soldatJoueur[i] != null && !old.soldatJoueur[i].estMort())
				nb++;
		this.soldatJoueur = new Soldat[nb];
		nb = 0;
		for (i = 0; i < old.soldatEnnemis.length; i++)
			if (old.soldatEnnemis[i] != null && !old.soldatEnnemis[i].estMort())
				nb++;
		this.soldatEnnemis = new Soldat[nb];
		int ij = 0, ie = 0;
		ICase c;
		Soldat s;
		this.carte = new ICase[old.carte.length][old.carte[0].length];
		int j;
		for (i = 0; i < carte.length; i++)
			for (j = 0; j < carte[i].length; j++) {
				c = (carte[i][j] = (old.carte[i][j] == null ? null : old.carte[i][j].clone()));
				if (c != null && c.getElement() instanceof Soldat)
					if ((s = ((Soldat) c.getElement())).getType().getFaction() == factionJoueur)
						soldatJoueur[ij++] = s;
					else
						soldatEnnemis[ie++] = s;
			}
	}

	@Override
	public Config clone() {
		return new Config(this);
	}

	@Override
	public ICase[][] getCarte() {
		return carte;
	}

	public FinJeu getCourant() {
		return courant;
	}

	@Override
	public Difficulty getDifficulty() {
		return difficulty;
	}

	public Faction getFactionEnnemi() {
		return factionEnnemi;
	}

	@Override
	public Faction getFactionJoueur() {
		return factionJoueur;
	}

	@Override
	public int getHauteurCarte() {
		return (int) (hauteurCarte * mapSize.getFactor());
	}

	@Override
	public int getLargeurCarte() {
		return (int) (largeurCarte * mapSize.getFactor());
	}

	@Override
	public MapSize getMapSize() {
		return mapSize;
	}

	@Override
	public int getNombreObstacle() {
		return (int) (getLargeurCarte() * getHauteurCarte() * getPourcentageObstacle());
	}

	@Override
	public float getPourcentageObstacle() {
		return pourcentageObstacle;
	}

	public Soldat[] getSoldatEnnemis() {
		return soldatEnnemis;
	}

	public Soldat[] getSoldatJoueur() {
		return soldatJoueur;
	}

	@Override
	public int getVieparregen() {
		return vieParRegen;
	}

	@Override
	public int getVieParRegen() {
		return vieParRegen;
	}

	@Override
	public boolean isShowFps() {
		return showFps;
	}

	@Override
	public boolean isShowingFPS() {
		return showFps;
	}

	@Override
	public void setCarte(ICase[][] carte) {
		this.carte = carte;
	}

	public void setCourant(FinJeu courant) {
		this.courant = courant;
	}

	@Override
	public void setDifficulty(Difficulty d) {
		difficulty = d;
	}

	public void setFactionEnnemi(Faction factionEnnemi) {
		this.factionEnnemi = factionEnnemi;
	}

	@Override
	public void setFactionJoueur(Faction factionJoueur) {
		this.factionJoueur = factionJoueur;
		setFactionEnnemi(factionJoueur.getOthers());
	}

	@Override
	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	@Override
	public void setLargeurCarte(int largeurCarte) {
		this.largeurCarte = largeurCarte;
	}

	@Override
	public void setMapSize(MapSize mapSize) {
		this.mapSize = mapSize;
	}

	@Override
	public void setPourcentageObstacle(float pourcentageObstacle) {
		this.pourcentageObstacle = pourcentageObstacle;
	}

	@Override
	public void setShowFps(boolean showFps) {
		this.showFps = showFps;
	}

	public void setSoldatEnnemis(Soldat[] soldatEnnemis) {
		this.soldatEnnemis = soldatEnnemis;
	}

	public void setSoldatJoueur(Soldat[] soldatJoueur) {
		this.soldatJoueur = soldatJoueur;
	}
}
