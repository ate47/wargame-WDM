package wargame;

/**
 * Represente un type de soldat
 */
public interface IType {
	/**
	 * Represente une faction pour un soldat
	 */
	public enum Faction {
		BLANC(TypeBon.values(), 6, "faction/faction_blanc.png"),
		VERT(TypeMauvais.values(), 15, "faction/faction_vert.png");
		public static Faction getRandomFaction() {
			return values()[(int) (Math.random() * values().length)];
		}

		private IType[] values;
		private ImageAsset icon;
		private int nombreGenere;

		Faction(IType[] values, int nombreGenere, String icon) {
			this.values = values;
			this.nombreGenere = nombreGenere;
			this.icon = new ImageAsset(icon);
		}

		/**
		 * @return l'icone de la faction
		 */
		public ImageAsset getIcon() {
			return icon;
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

		/**
		 * @return le tableau des types de soldats pour cette faction
		 */
		public IType[] getTypes() {
			return values;
		}

		/**
		 * @return le nombre d'élement a généré pour un jeu normal
		 */
		public int nombreGenere() {
			return nombreGenere;
		}
	}

	/**
	 * Represente un {@link IType} pour la faction {@link Faction#BLANC}
	 */
	public enum TypeBon implements IType {
		HUMAIN(40, 3, 10, 2, "soldat/blanc_humain.png"), NAIN(80, 1, 20, 0, "soldat/blanc_nain.png"),
		ELF(70, 5, 10, 6, "soldat/blanc_elf.png"), HOBBIT(20, 3, 5, 2, "soldat/blanc_hobbit.png");

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
		TROLL(100, 1, 30, 0, "soldat/vert_troll.png"), ORC(40, 3, 10, 3, "soldat/vert_orc.png"),
		GOBELIN(20, 3, 5, 2, "soldat/vert_gobelin.png");

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
