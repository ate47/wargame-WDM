package wargame;

/**
 * Represente un type de soldat
 */
public interface IType {
	/**
	 * Represente une faction pour un soldat
	 */
	public enum Faction {
		BLANC(TypeBon.values(), IConfig.NB_HEROS), VERT(TypeMauvais.values(), IConfig.NB_MONSTRES);
		public static Faction getRandomFaction() {
			return values()[(int) (Math.random() * values().length)];
		}

		private IType[] values;
		private int nombreGenere;

		Faction(IType[] values, int nombreGenere) {
			this.values = values;
			this.nombreGenere = nombreGenere;
		}

		/**
		 * @return une autre faction que celle-ci aléatoirement
		 */
		public Faction getOthers() {
			int n = values().length;
			return values()[(ordinal() + 1 + (int) (Math.random() * (n - 1))) % n];
		}

		/**
		 * @return un type de soldat de cette faction
		 */
		public IType getRandomElement() {
			return values[(int) (Math.random() * values.length)];
		}

		public int nombreGenere() {
			return nombreGenere;
		}
	}

	/**
	 * Represente un {@link IType} pour la faction {@link Faction#BLANC}
	 */
	public enum TypeBon implements IType {
		HUMAIN(40, 3, 10, 2, "testbon.png"), NAIN(80, 1, 20, 0, "testbon.png"), ELF(70, 5, 10, 6, "testbon.png"),
		HOBBIT(20, 3, 5, 2, "testbon.png");

		private ImageAsset image;
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;

		TypeBon(int points, int portee, int puissance, int tir, String image) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance;
			TIR = tir;

			this.image = new ImageAsset(image);
		}

		@Override
		public Faction getFaction() {
			return Faction.BLANC;
		}

		@Override
		public ImageAsset getImage() {
			return image;
		}

		@Override
		public int getPointsDeVie() {
			return POINTS_DE_VIE;
		}

		@Override
		public int getPorteeVisuelle() {
			return PORTEE_VISUELLE;
		}

		@Override
		public int getPuissance() {
			return PUISSANCE;
		}

		@Override
		public int getTir() {
			return TIR;
		}
		
	}

	/**
	 * Represente un {@link IType} pour la faction {@link Faction#VERT}
	 */
	public enum TypeMauvais implements IType {
		TROLL(100, 1, 30, 0, "testmauvais.png"), ORC(40, 3, 10, 3, "testmauvais.png"),
		GOBELIN(20, 3, 5, 2, "testmauvais.png");

		private ImageAsset image;
		private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;

		TypeMauvais(int points, int portee, int puissance, int tir, String image) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance;
			TIR = tir;

			this.image = new ImageAsset(image);
		}

		@Override
		public Faction getFaction() {
			return Faction.VERT;
		}

		@Override
		public ImageAsset getImage() {
			return image;
		}

		@Override
		public int getPointsDeVie() {
			return POINTS_DE_VIE;
		}

		@Override
		public int getPorteeVisuelle() {
			return PORTEE_VISUELLE;
		}

		@Override
		public int getPuissance() {
			return PUISSANCE;
		}

		@Override
		public int getTir() {
			return TIR;
		}
	}

	/**
	 * @return la faction de ce type
	 */
	Faction getFaction();

	/**
	 * @return la texture de ce soldats
	 */
	ImageAsset getImage();

	/**
	 * @return me nombre de pv de ce type
	 */
	int getPointsDeVie();

	/**
	 * @return la portée visuelle de ce type de soldat
	 */
	int getPorteeVisuelle();

	/**
	 * @return la puissance de ce type de soldat
	 */
	int getPuissance();

	/**
	 * @return la distance de tir de ce type de soldat
	 */
	int getTir();

}
