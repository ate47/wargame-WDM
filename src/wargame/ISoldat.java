package wargame;

public interface ISoldat {
	enum Faction {
		BLANC, VERT;
	}

	static enum Type {
		HUMAIN(40, 3, 10, 2, Faction.BLANC), 
		NAIN(80, 1, 20, 0, Faction.BLANC), 
		ELF(70, 5, 10, 6, Faction.BLANC),
		HOBBIT(20, 3, 5, 2, Faction.BLANC),


		TROLL(100, 1, 30, 0, Faction.VERT), 
		ORC(40, 2, 10, 3, Faction.VERT), 
		GOBELIN(20, 2, 5, 2, Faction.VERT);
		
		public static Type getTypeAlea() {
			return values()[(int) (Math.random() * values().length)];
		}

		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;
		private final Faction FACTION;

		Type(int points, int portee, int puissance, int tir, Faction faction) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance;
			TIR = tir;
			FACTION = faction;
		}

		public Faction getFaction() {
			return FACTION;
		}

		public int getPoints() {
			return POINTS_DE_VIE;
		}

		public int getPortee() {
			return PORTEE_VISUELLE;
		}

		public int getPuissance() {
			return PUISSANCE;
		}

		public int getTir() {
			return TIR;
		}
	}

	void combat(Soldat soldat);

	int getPoints();

	int getPortee();

	int getTour();

	void joueTour(int tour);

	void seDeplace(Position newPos);
}