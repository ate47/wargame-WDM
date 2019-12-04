package wargame;

import java.io.Serializable;

import wargame.IType.Faction;

public class Config implements IConfig, Serializable {

	private static final long serialVersionUID = 9194042623694545614L;
	private boolean showFps = false;
	private int largeurCarte = 25;
	private int hauteurCarte = 30;
	private float pourcentageObstacle = .25F;
	private int vieParRegen = 7;
	private Faction factionJoueur = Faction.VERT;

	@Override
	public boolean isShowingFPS() {
		return showFps;
	}

	@Override
	public int getNombreObstacle() {
		return (int) (getLargeurCarte() * getHauteurCarte() * getPourcentageObstacle());
	}

	@Override
	public boolean isShowFps() {
		return showFps;
	}

	@Override
	public void setShowFps(boolean showFps) {
		this.showFps = showFps;
	}

	@Override
	public int getLargeurCarte() {
		return largeurCarte;
	}

	@Override
	public void setLargeurCarte(int largeurCarte) {
		this.largeurCarte = largeurCarte;
	}

	@Override
	public int getHauteurCarte() {
		return hauteurCarte;
	}

	@Override
	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	@Override
	public float getPourcentageObstacle() {
		return pourcentageObstacle;
	}

	@Override
	public void setPourcentageObstacle(float pourcentageObstacle) {
		this.pourcentageObstacle = pourcentageObstacle;
	}

	@Override
	public int getVieParRegen() {
		return vieParRegen;
	}

	@Override
	public void setVieParRegen(int vieParRegen) {
		this.vieParRegen = vieParRegen;
	}

	@Override
	public Faction getFactionJoueur() {
		return factionJoueur;
	}

	@Override
	public void setFactionJoueur(Faction factionJoueur) {
		this.factionJoueur = factionJoueur;
	}

}
