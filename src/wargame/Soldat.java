package wargame;

public class Soldat extends Element implements ISoldat {
	private int vie;
	private IType type;
	private Position nextPosition;
	private SoldatProchainMouvement mouvement = SoldatProchainMouvement.RIEN;

	public SoldatProchainMouvement getProchainMouvement() {
		return mouvement;
	}

	public Soldat(IType type) {
		super(type.getImage());
		this.type = type;
		vie = type.getPointsDeVie();
	}

	public Soldat(Position pos, IType type) {
		super(pos, type.getImage());
		this.type = type;
	}

	@Override
	public boolean aJoueCeTour() {
		return mouvement != SoldatProchainMouvement.RIEN;
	}

	public void annulerTour() {
		mouvement = SoldatProchainMouvement.RIEN;
	}

	@Override
	public void combat(Soldat soldat) {

	}

	@Override
	public IType getType() {
		return type;
	}

	@Override
	public void joueTour() {
		switch (mouvement) {
		case DEPLACEMENT:
			setPosition(nextPosition);
			break;
		case RIEN:
			setVie(Math.min(getVie() + IConfig.VIE_PAR_REGEN, getType().getPointsDeVie()));
			break;
		default:
			return;
		}
		mouvement = SoldatProchainMouvement.RIEN;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getVie() {
		return vie;
	}

	@Override
	public void seDeplace(Position newPos) throws IllegalMoveException {
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
	public Position getNextPosition() {
		return nextPosition;
	}

}
