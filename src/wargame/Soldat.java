package wargame;

import wargame.ICarte.ICase;

public class Soldat extends Element implements ISoldat {
	public static boolean rechercheSequentielle(ICase[] tableau, Soldat element){
		for(ICase c : tableau) {
			if(c.getElement().equals(element))
				return true;
		}
		return false;
	}
	
	private int vie;
	private IType type;
	private ICase nextPosition;
	private Soldat cible;
	private SoldatProchainMouvement mouvement = SoldatProchainMouvement.RIEN;

	public SoldatProchainMouvement getProchainMouvement() {
		return mouvement;
	}

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
	public void combat(Soldat soldat) {
		int des;
		Wargame.ICase[] visibles;
		
		//coup
		des = (int) (Math.random() * this.getType().getPuissance() );
		
		soldat.setVie(soldat.getVie() - des);
		
		// Riposte
		if(soldat.getVie() > 0) {
			visibles = getPosition().visible(soldat.getType().getTir());
			if(rechercheSequentielle(visibles, this)){
				des = (int) (Math.random() * (soldat.getType().getPuissance() / 2));
				this.setVie(this.getVie() - des);
			}
		}
	}
	
	public void mort() {
		this.getPosition().setElement(null);
		setPosition(null);
		vie = 0;
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
		case COMBAT:
			combat(cible);
		default:
			return;
		}
		mouvement = SoldatProchainMouvement.RIEN;
	}

	@Override
	public void setVie(int vie) {
		this.vie = vie;
	}

	@Override
	public int getVie() {
		return vie;
	}

	@Override
	public void seDeplace(ICase newPos) throws IllegalMoveException {
		if (mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		nextPosition = newPos;
		mouvement = SoldatProchainMouvement.DEPLACEMENT;
	}
	
	@Override
	public void seBat(Soldat enemi) throws IllegalMoveException{
		if(mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		cible = enemi;
		mouvement = SoldatProchainMouvement.COMBAT;
	}

	@Override
	public void seRegen() throws IllegalMoveException {
		if (mouvement != SoldatProchainMouvement.RIEN)
			throw new IllegalMoveException();
		mouvement = SoldatProchainMouvement.RIEN;
	}
	
	@Override
	public ICase getNextPosition() {
		return nextPosition;
	}
	
	@Override
	public Soldat getCible() {
		return cible;
	}
	
	@Override
	public boolean estMort() {
		return vie == 0;
	}

}
