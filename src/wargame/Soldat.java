package wargame;

import wargame.ICarte.ICase;
import wargame.config.IConfig;

public class Soldat extends Element implements ISoldat {
	public static boolean rechercheSequentielle(ICase[] tableau, Soldat element) {
		for (ICase c : tableau) {
			if (c != null && element.equals(c.getElement()))
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
		if (soldat.estMort())
			return;

		// Coup
		des = (int) (Math.random() * this.getType().getPuissance());

		soldat.setVie(Math.max(0, soldat.getVie() - des));
		
		// Riposte
		if (!soldat.estMort()) {
			des = 0;
			if (rechercheSequentielle(soldat.getPosition().visible(1), this))
				des = (int) (Math.random() * soldat.getType().getPuissance());
			else if (rechercheSequentielle(soldat.getPosition().visible(soldat.getType().getPorteeVisuelle()), this))
				des = (int) (Math.random() * soldat.getType().getTir());
			this.setVie(Math.max(0, this.getVie() - des));
		}
		

	}

	@Override
	public boolean estMort() {
		return vie <= 0 || getPosition() == null;
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
	public void joueTour(IConfig cfg) {
		switch (mouvement) {
		case DEPLACEMENT:
			if (nextPosition.getElement() == null) {
				getPosition().setElement(null);
				setPosition(nextPosition);
				getPosition().setElement(this);
			}
			break;
		case COMBAT:
			combat(cible);
			break;
		case RIEN:
		default:
			setVie(Math.min(getVie() + (int) (cfg.getVieParRegen() * cfg.getDifficulty().getMultiplicateurVie()),
					getType().getPointsDeVie()));
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

	public void choixIA() {
		ICase[] vision;
		Element e;
		boolean chercherpos = true;
		ICase pos;
		int test;

		if (estMort())
			return;

		// cases ciblables
			vision = this.getPosition().visible(this.getType().getPorteeVisuelle());

		// L'IA cherche un ennemis à taper dans sa vision
		for (ICase c : vision) {
			if (c != null) {
				e = c.getElement();
				if (e instanceof Soldat) {
					if (((Soldat) e).getType().getFaction() != this.getType().getFaction()) {
						try {
							this.seBat((Soldat) e);
						} catch (IllegalMoveException e1) {
							e1.printStackTrace();
						}
						return;
					}

				}
			}
		}

		/*
		 * l'IA se soigne si elle est en danger
		 */
		if (this.getVie() < this.getType().getPointsDeVie() * 0.25F) {
			try {
				this.seRegen();
			} catch (IllegalMoveException e1) {
				e1.printStackTrace();
			}
			return;
		}

		/*
		 * Cherche une vide pour se déplacer
		 */

		while (chercherpos) {
			pos = vision[(int) (Math.random() * vision.length)];
			if(pos != null && pos.getElement() == null) {
				try {
					seDeplace(pos);
					chercherpos = false;
				} catch (IllegalMoveException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
