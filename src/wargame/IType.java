package wargame;

public interface IType {
	
	public enum Faction {
		BLANC(TypeBon.values()), VERT(TypeMauvais.values());
		private IType[] values;
		
		Faction(IType[] values) {
			this.values = values;
		}
		
		public IType getRandomElement() {
			return values[(int) (Math.random() * values.length)];
		}
		
		public static Faction getRandomFaction() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
	
	public enum TypeBon implements IType {
		HUMAIN(40, 3, 10, 2, "testbon.png"),
		NAIN(80, 1, 20, 0, "testbon.png"),
		ELF(70, 5, 10, 6, "testbon.png"),
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
		public int getPoints() {
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
		
		@Override
		public ImageAsset getImage() {
			return image;
		}
	}
	
	public enum TypeMauvais implements IType {
		TROLL(100, 1, 30, 0, "testmauvais.png"),
		ORC(40, 2, 10, 3, "testmauvais.png"),
		GOBELIN(20, 2, 5, 2, "testmauvais.png");
		
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
		public int getPoints() {
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
		
		@Override
		public ImageAsset getImage() {
			return image;
		}
	}
	
	Faction getFaction();
	
	int getPoints();
	
	int getPorteeVisuelle();
	
	int getPuissance();
	
	int getTir();
	
	ImageAsset getImage();
}
