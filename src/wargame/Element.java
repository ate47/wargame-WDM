package wargame;

import wargame.Obstacle.TypeObstacle;
	
public class Element {
	protected Position position;
		
	/*
	 * Constructeur de l'élement qui lui donne aleatoirement des coordonnees
	 */
	public Element() {
		this.position = new Position((int)Math.random()*IConfig.LARGEUR_CARTE
									,(int)Math.random()*IConfig.HAUTEUR_CARTE);
	}
	public Element(Position pos){
		//this();
		this.position = pos;
	}
	/*
	 * SetPosition
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	
	
	/*
	 * getAbscisse
	 */
	public Position getPosition() {
		return position;
	}
}
