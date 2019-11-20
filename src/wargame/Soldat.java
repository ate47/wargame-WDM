package wargame;

public class Soldat extends Element implements ISoldat {
	private IType type;
	private Position nextPosition;
	private boolean joue;

	public Soldat(IType type) {
		super(type.getImage());
		this.type = type;
	}

	public Soldat(Position pos, IType type) {
		super(pos, type.getImage());
		this.type = type;
	}

	@Override
	public boolean aJoueCeTour() {
		return joue;
	}
	public void annulerTour() {
		joue = false;
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
		joue = false;
		setPosition(nextPosition);
	}

	@Override
	public void seDeplace(Position newPos) throws IllegalMoveException {
		if (joue)
			throw new IllegalMoveException();
		nextPosition = newPos;
		joue = true;
	}

}
