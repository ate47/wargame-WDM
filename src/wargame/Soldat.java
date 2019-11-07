package wargame;

public class Soldat extends Element implements ISoldat{
	
	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#combat(wargame.Soldat)
	 */
	public void combat(Soldat soldat) {
		
	}
	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#getPoints()
	 */
	public int getPoints() {
		
		
		return 1;	
	}

	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#getPortee()
	 */
	public int getPortee() {
		
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#getTour()
	 */
	public int getTour() {
		
		return 1;
	}
	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#joueTour(int)
	 */
	public void joueTour(int tour) {
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see wargame.ISoldat#seDeplace(wargame.Position)
	 */
	public void seDeplace(Position newPos) {
		
		
	}

}
