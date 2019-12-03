package wargame;

import wargame.ICarte.ICase;

public class Soldat extends Element implements ISoldat {
	public static boolean rechercheSequentielle(ICase[] tableau, Soldat element) {
		for (ICase c : tableau) {
			if (element.equals(c.getElement()))
				return true;
		}
		return false;
	}

	private int vie;
	private IType type;
	private ICase nextPosition;
	private Soldat cible;

	private SoldatProchainMouvement mouvement = SoldatProchainMouvement.RIEN;

	public Soldat(IType type) {
		super(type.getImage());
		this.type = type;
		vie = type.getPointsDeVie();
	}

	@Override
	public boolean aJoueCeTour() {
		return mouvement != SoldatProchainMouvement.RIEN;
	}

	public void annulerTour() {
		mouvement = SoldatProchainMouvement.RIEN;
	}

	@Override
	public void combat(ISoldat soldat) {
		int des;
		
		// coup
		des = (int) (Math.random() * this.getType().getPuissance());

		soldat.setVie(Math.max(0, soldat.getVie() - des));

		// Riposte
		if (!soldat.estMort()) {
			des = 0;
			if (rechercheSequentielle(getPosition().visible(1), this))
				des = (int) (Math.random() * soldat.getType().getPuissance());
			else if (rechercheSequentielle(getPosition().visible(soldat.getType().getPorteeVisuelle()), this))
				des = (int) (Math.random() * soldat.getType().getTir());
			this.setVie(Math.max(0, this.getVie() - des));
		}
	}

	@Override
	public boolean estMort() {
		return vie == 0;
	}

	@Override
	public Soldat getCible() {
		return cible;
	}

	@Override
	public ICase getNextPosition() {
		return nextPosition;
	}

	public SoldatProchainMouvement getProchainMouvement() {
		return mouvement;
	}

	@Override
	public IType getType() {
		return type;
	}

	@Override
	public int getVie() {
		return vie;
	}

	@Override
	public void joueTour() {
		switch (mouvement) {
		case DEPLACEMENT:
			getPosition().setElement(null);
			setPosition(nextPosition);
			getPosition().setElement(this);
			break;
		case COMBAT:
			combat(cible);
			break;
		case RIEN:
		default:
			setVie(Math.min(getVie() + IConfig.VIE_PAR_REGEN, getType().getPointsDeVie()));
			break;
		}
		mouvement = SoldatProchainMouvement.RIEN;
	}

	public void mort() {
		this.getPosition().setElement(null);
		setPosition(null);
		vie = 0;
	}

	@Override
	public void seBat(Soldat enemi) throws IllegalMoveException {
		if (mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		cible = enemi;
		mouvement = SoldatProchainMouvement.COMBAT;
	}

	@Override
	public void seDeplace(ICase newPos) throws IllegalMoveException {
		if (mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		nextPosition = newPos;
		mouvement = SoldatProchainMouvement.DEPLACEMENT;
	}

	@Override
	public void seRegen() throws IllegalMoveException {
		if (mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		mouvement = SoldatProchainMouvement.RIEN;
	}

	@Override
	public void setVie(int vie) {
		this.vie = vie;
		if (this.vie == 0)
			mort();
	}

}
