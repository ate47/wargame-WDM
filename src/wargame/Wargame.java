package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Timer;

import wargame.assets.ImageAsset;
import wargame.assets.SoundAsset;
import wargame.config.Config;
import wargame.config.Faction;
import wargame.config.IConfig;
import wargame.config.SavedConfig;
import wargame.menu.MenuFin;
import wargame.menu.MenuJeu;
import wargame.menu.MenuPause;
import wargame.menu.PanelMenu;
import wargame.utils.WargameUtils;

public class Wargame implements ICarte, KeyEventDispatcher {
	public enum FinJeu {
		GAGNE, PERDU, EN_COURS
	}

	public static class Case implements ICase, Serializable {
		private static final long serialVersionUID = 4868404777608031114L;
		private boolean visible, visite, accessible;
		private Element e;
		private int x, y;

		@Override
		public Case clone() {
			return new Case(this);
		}

		private Case(Case old) {
			visible = old.visible;
			visite = old.visite;
			accessible = old.accessible;
			if (old.e != null) {
				ICase oldPos = old.e.getPosition();
				old.e.setPosition(null);
				e = old.e.clone();
				old.e.setPosition(oldPos);
				e.setPosition(this);
			}
			x = old.x;
			y = old.y;
		}

		public Case(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void click() {
			// Si on n'a toujours pas cliqué
			if (instance.soldat == null) {
				if (e != null && (e instanceof Soldat)) {
					Soldat s = (Soldat) e;
					if (s.getType().getFaction() == instance.config.getFactionJoueur()) {
						if (s.aJoueCeTour())
							s.annulerTour();
						instance.soldat = s;
						instance.visibles = visible(s.getType().getPorteeVisuelle());
						for (Case v : instance.visibles)
							if (v != null)
								v.accessible = true;
					}
				}
			} else {
				// On a déjà cliqué

				// la case est vide et accessible
				if (e == null && accessible) {
					// on autorise le deplacement ...
					try {
						instance.soldat.seDeplace(this);
					} catch (IllegalMoveException e1) {
						// (par construction impossible)
						e1.printStackTrace();
					}
					// ...et on retire les cases qu'on avait placé accessible
					for (Case c : instance.visibles)
						if (c != null)
							c.accessible = false;

					instance.soldat = null;
					instance.visibles = null;

					// on reclique sur la même case
				} else if (equals(instance.soldat.getPosition())) {
					for (Case c : instance.visibles)
						if (c != null)
							c.accessible = false;
					instance.soldat = null;
					instance.visibles = null;

					// on clique sur un soldat accessible
				} else if (e != null && e instanceof Soldat && accessible) {
					Soldat s = (Soldat) e;
					if (s.getType().getFaction() == instance.config.getFactionEnnemi()) {
						try {
							instance.soldat.seBat(s);
						} catch (IllegalMoveException e1) {
							e1.printStackTrace();
						}
						for (Case c : instance.visibles)
							if (c != null)
								c.accessible = false;

						instance.soldat = null;
						s = null;
						instance.visibles = null;
					}
				}
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Case other = (Case) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public Element getElement() {
			return e;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public boolean isAccessible() {
			return accessible;
		}

		@Override
		public boolean isVisible() {
			return visible;
		}

		@Override
		public boolean isVisite() {
			return visite;
		}

		public void setAccessible(boolean accessible) {
			this.accessible = accessible;
		}

		@Override
		public void setElement(Element e) {
			this.e = e;
			if (e != null)
				e.setPosition(this);
		}

		@Override
		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		@Override
		public void setVisite(boolean visite) {
			this.visite = visite;
		}

		@Override
		public String toString() {
			return "Case [visible=" + visible + ", visite=" + visite + ", e=" + e + "]";
		}

		@Override
		public Case[] visible(int portee) {
			return instance.visible(getX(), getY(), portee);
		}
	}

	/**
	 * Frame du jeu avec calcul temps entre frame
	 */
	public class GameFrame extends JFrame {
		private static final long serialVersionUID = 909975060986688125L;

		public GameFrame(String name) {
			super(name);
		}

		protected JRootPane createRootPane() {
			return new JRootPane() {
				private static final long serialVersionUID = -6357175102764074486L;

				{
					setOpaque(false);
				}

				@Override
				public void paint(Graphics g) {
					startFrame();
					super.paint(g);
					if (config.isShowingFPS()) {
						g.setColor(Color.white);
						g.drawString("Fps: " + getFps(), 0, g.getFont().getSize());
					}
				}
			};
		}
	}

	public static final Random RANDOM = new Random();
	public static final int MAX_SAVE = 4;
	public static final SoundAsset MUSIQUE_JEU = new SoundAsset("ambiance.wav");
	private static Wargame instance;
	// AFFICHAGE
	private long lastFPSTime = System.currentTimeMillis();
	private float partialTick = 0F;
	private float fps;

	// MENU
	private JFrame frame;
	private PanneauJeu panneau;
	private MenuJeu menu;
	private MenuFin menuFin;
	private MenuPause menuPause;

	// CONFIGURATION DE JEU
	private IConfig config;
	private SavedConfig[] save;

	// CALCUL
	private Case[] visibles;
	private Soldat soldat;
	private ICase hoveredCase;

	public Wargame() {
		instance = this;
		frame = new GameFrame("Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(800, 600);

		panneau = new PanneauJeu(this);
		panneau.setPreferredSize(frame.getSize());
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

		menu = new MenuJeu(this);
		menu.setPreferredSize(frame.getSize());

		menuPause = new MenuPause(this);
		
		menuFin = new MenuFin(this);

		showMenu(menu);

		frame.setIconImage(new ImageAsset("ico.png").getImages()[0]);
	}

	@Override
	public void ajouteElement(int x, int y, Element e) {
		ICase c = config.getCarte()[x][y];
		c.setElement(e);
		e.setPosition(c);
	}

	@Override
	public void genererCarte() {
		int i, j, k, carre, xmin, ymin, xmax, ymax, nb;
		int cameraX, cameraY;

		// LECTURE DES CONFIGURATIONS

		config.setCourant(FinJeu.EN_COURS);

		config.setCarte(new Case[config.getLargeurCarte()][config.getHauteurCarte()]);
		for (i = 0; i < config.getCarte().length; i++)
			for (j = 0; j < config.getCarte()[i].length; j++)
				config.getCarte()[i][j] = new Case(i, j);

		// PRODUCTION DES OBSTACLES

		nb = config.getNombreObstacle();
		for (i = 0; i < nb; i++)
			trouvePositionVide().setElement(new Obstacle());

		carre = (int) (Math.random() * 4);
		switch (carre) {
		case 0:
			xmin = 0;
			ymin = 0;
			xmax = getLargeur() / 2;
			ymax = getHauteur() / 2;
			break;
		case 1:
			xmin = getLargeur() / 2;
			;
			ymin = 0;
			xmax = getLargeur();
			ymax = getHauteur() / 2;
			break;
		case 2:
			xmin = 0;
			ymin = getHauteur() / 2;
			xmax = getLargeur() / 2;
			ymax = getHauteur();
			break;
		default:
			xmin = getLargeur() / 2 + 1;
			ymin = getHauteur() / 2 + 1;
			xmax = getLargeur();
			ymax = getHauteur();
			break;

		}

		// PRODUCTION DES SOLDATS
		nb = (int) (config.getFactionJoueur().nombreGenere() * config.getMapSize().getFactor());
		config.setSoldatJoueur(new Soldat[nb]);

		for (i = 0; i < nb; i++)
			trouvePositionVide(xmin, ymin, xmax, ymax)
					.setElement(config.getSoldatJoueur()[i] = new Soldat(config.getFactionJoueur().getRandomElement()));

		// On place la camera sur le milieu de notre zone
		cameraX = (xmax + xmin) / 2;
		cameraY = (ymax + ymin) / 2;

		nb = (int) (config.getFactionEnnemi().nombreGenere() * config.getDifficulty().getMultiplicateurEnnemis()
				* config.getMapSize().getFactor());

		// PRODUCTION DES AUTRES SOLDATS
		config.setSoldatEnnemis(new Soldat[nb * config.getDifficulty().getAreaCount()]);
		k = 0;

		carre = (carre + 1 + RANDOM.nextInt(4 - config.getDifficulty().getAreaCount())) % 4;

		for (j = 0; j < config.getDifficulty().getAreaCount(); j++) {
			switch (carre) {
			case 0:
				xmin = 0;
				ymin = 0;
				xmax = getLargeur() / 2;
				ymax = getHauteur() / 2;
				break;
			case 1:
				xmin = getLargeur() / 2;
				;
				ymin = 0;
				xmax = getLargeur();
				ymax = getHauteur() / 2;
				break;
			case 2:
				xmin = 0;
				ymin = getHauteur() / 2;
				xmax = getLargeur() / 2;
				ymax = getHauteur();
				break;
			default:
				xmin = getLargeur() / 2 + 1;
				ymin = getHauteur() / 2 + 1;
				xmax = getLargeur();
				ymax = getHauteur();
				break;

			}

			carre = (carre + 1) % 4;

			for (i = 0; i < nb; i++, k++)
				trouvePositionVide(xmin, ymin, xmax, ymax).setElement(
						config.getSoldatEnnemis()[k] = new Soldat(config.getFactionEnnemi().getRandomElement()));

		}

		// ON LANCE L'AFFICHAGE

		panneau.init();
		panneau.lookAt(cameraX, cameraY);

		// CALCUL DU BROUILLARD DE GUERRE
		jouerSoldats();
	}

	@Override
	public ICase getCase(int posX, int posY) {
		if (posX < 0 || posX >= config.getLargeurCarte() || posY < 0 || posY >= config.getHauteurCarte())
			return null;
		return config.getCarte()[posX][posY];
	}

	public IConfig getConfig() {
		return config;
	}

	@Override
	public Element getElement(int posX, int posY) {
		return getCase(posX, posY).getElement();
	}

	public float getFps() {
		return fps;
	}

	/**
	 * @return la fenetre du jeu
	 */
	public JFrame getFrame() {
		return frame;
	}

	@Override
	public int getHauteur() {
		return config.getHauteurCarte();
	}

	/**
	 * @return the hoveredCase
	 */
	public ICase getHoveredCase() {
		return hoveredCase;
	}

	@Override
	public int getLargeur() {
		return config.getLargeurCarte();
	}

	public MenuJeu getMenu() {
		return menu;
	}

	public MenuFin getMenuFin() {
		return menuFin;
	}

	public SavedConfig[] getSave() {
		return save;
	}

	public MenuPause getMenuPause() {
		return menuPause;
	}

	@Override
	public PanneauJeu getPanneau() {
		return panneau;
	}

	public float getPartialTick() {
		return partialTick;
	}

	@Override
	public Soldat getSoldatClick() {
		return soldat;
	}

	@Override
	public Soldat[] getSoldatEnnemis() {
		return config.getSoldatEnnemis();
	}

	@Override
	public Soldat[] getSoldatJoueur() {
		return config.getSoldatJoueur();
	}

	public File getConfigFile(int number) {
		File wargame = new File("wargame");
		if (!wargame.exists())
			wargame.mkdirs();
		return new File(wargame, "save" + number + ".cfg");
	}

	@Override
	public Case[] getVisibles() {
		return visibles;
	}

	@Override
	public void jouerSoldats() {
		int i, j, nombreSoldat = 0;
		for (i = 0; i < config.getCarte().length; i++)
			for (j = 0; j < config.getCarte()[i].length; j++)
				config.getCarte()[i][j].setVisible(false);

		// 0 le BrG
		for (ISoldat s : getSoldatJoueur()) {
			s.joueTour(getConfig());
			if (!s.estMort())
				nombreSoldat++;
		}

		if (nombreSoldat == 0) {
			config.setCourant(FinJeu.PERDU);
			showMenu(menuFin);
		}

		for (ISoldat s : getSoldatJoueur())
			if (!s.estMort())
				for (Case c : visible(s.getPosition().getX(), s.getPosition().getY(), s.getType().getPorteeVisuelle()))
					if (c != null) {
						c.setVisible(true);
						c.setVisite(true);
					}
		panneau.repaint();
	}

	public FinJeu getCourant() {
		return config.getCourant();
	}

	public void lancerJeu() {
		readConfig();

		frame.pack();
		frame.setVisible(true);
		MUSIQUE_JEU.loop();

		/* sync des fps */
		new Timer(1000 / 60, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
			}
		}).start();
	}

	/**
	 * Lance la configuration de jeu
	 * 
	 * @param cfg
	 *            la config de jeu
	 */
	public void lancerConfig(IConfig cfg) {
		config = cfg.clone();

		// ON LANCE L'AFFICHAGE

		panneau.init();
		panneau.setZoom(1);
		panneau.lookAt(config.getLargeurCarte() / 2, config.getHauteurCarte() / 2);
		showMenu(panneau);

		// CALCUL DU BROUILLARD DE GUERRE
		jouerSoldats();
	}

	/**
	 * Supprime un soldat du jeu (vérifier avant si ça vie < 0)
	 * 
	 * @param soldat
	 *            le soldat en question
	 */
	@Override
	public void mort(Soldat soldat) {
		soldat.mort();
	}

	@Override
	public int nombreVisible(int portee) {
		return 3 * portee * (portee + 1) + 1;
	}

	public void readConfig() {
		save = new SavedConfig[MAX_SAVE];
		File cf;
		for (int i = 0; i < MAX_SAVE; i++) {
			save[i] = new SavedConfig();
			cf = getConfigFile(i + 1);

			if (cf.exists()) {
//				System.out.println("Lecture de " + cf.getAbsolutePath() + "... ");
				Object o = WargameUtils.readObjectFromFile(cf);
				if (o == null) {
					if (JOptionPane.showConfirmDialog(null,
							"Erreur lors de la lecture de " + cf.getAbsolutePath()
									+ " Voulez vous ecraser le fichier existant ?",
							"Erreur lors de la lecture du fichier", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						System.exit(0);
				} else {
					save[i] = (SavedConfig) o;
				}
			}
		}
		cf = getConfigFile(0);

		if (cf.exists()) {
//			System.out.println("Lecture de " + cf.getAbsolutePath() + "... ");
			Object o = WargameUtils.readObjectFromFile(cf);
			if (o == null) {
				if (JOptionPane.showConfirmDialog(null,
						"Erreur lors de la lecture de " + cf.getAbsolutePath()
								+ " Voulez vous ecraser le fichier existant ?",
						"Erreur lors de la lecture du fichier", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					System.exit(0);
				else
					config = new Config();
			} else {
				config = (IConfig) o;
			}
		} else
			config = new Config();
		writeConfig();
	}

	@Override
	public void setHoveredCase(ICase hoveredCase) {
		this.hoveredCase = hoveredCase;
	}

	public void showMenu(JPanel panel) {
		panel.setSize(frame.getRootPane().getSize());
		if (panel instanceof PanelMenu) {
			PanelMenu pnl = (PanelMenu) panel;
			pnl.removeAll();
			pnl.init();
		}
		frame.setContentPane(panel);
		frame.repaint();
	}

	public boolean tousMort(Soldat lesSoldats[]) {
		for (Soldat s : lesSoldats) {
			if (!s.estMort()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ICase trouvePositionVide() {
		int x, y;
		ICase c;

		while (true) {
			x = (int) (Math.random() * getLargeur());
			y = (int) (Math.random() * getHauteur());
			c = getCase(x, y);
			if (c.getElement() == null) {
				return c;
			}
		}
	}

	@Override
	public ICase trouvePositionVide(ICase pos) {
		ICase[] autre = pos.visible(1);
		ICase c;
		while (true) {
			c = autre[(int) (Math.random() * autre.length)];
			if (c != null && c.getElement() == null)
				return c;
		}
	}

	/**
	 * trouve une position vide dans un rectangle(xmin, ymin, xmax, ymax)
	 * 
	 * @param xmin
	 *            coord x du haut gauche du rectangle
	 * @param ymin
	 *            coord y du haut gauche du rectangle
	 * @param xmax
	 *            coord x du bas droit du rectangle
	 * @param ymax
	 *            coord y du bas droit du rectangle
	 * @return
	 */
	public ICase trouvePositionVide(int xmin, int ymin, int xmax, int ymax) {
		int x, y;
		ICase c;

		while (true) {
			x = (int) (Math.random() * (xmax - xmin)) + xmin;
			y = (int) (Math.random() * (ymax - ymin)) + ymin;

			c = getCase(x, y);
			if (c.getElement() == null) {
				return c;
			}
		}
	}

	@Override
	public ISoldat trouveSoldat(Faction f) {
		while (true) {
			ISoldat s = getSoldatJoueur()[(int) (getSoldatJoueur().length * Math.random())];
			if (!s.estMort())
				return s;
		}
	}

	public Case[] visible(int x, int y, int portee) {
		Case[] cases = new Case[nombreVisible(portee)];
		return (Case[]) visible(x, y, portee, cases);
	}

	@Override
	public ICase[] visible(int x, int y, int portee, ICase[] cases) {
		int i, j, k, l = 0, decalage;
		decalage = 2 * ((y + portee + 1) % 2) - 1;

		// milieu
		for (i = 0; i <= portee; i++)
			for (j = -portee; j <= portee; j++)
				cases[l++] = getCase(x + decalage * (i - portee / 2), y + j);

		// droite
		for (i = portee / 2 + 1 + (portee % 2), k = portee * 2 - 3; k > 0; k -= 4, i++)
			for (j = -k / 2; j <= k / 2; j++)
				cases[l++] = getCase(x + decalage * i, y + j);

		// gauche
		for (i = portee / 2 + 1, k = portee * 2 - 1; k > 0; k -= 4, i++)
			for (j = -k / 2; j <= k / 2; j++)
				cases[l++] = getCase(x - decalage * i, y + j);

		return cases;
	}

	public void writeConfig() {
		File cf;
//		config.setCarte(config.getCarte() == null ? new Case[config.getLargeurCarte()][config.getHauteurCarte()]
//				: config.getCarte());
		cf = getConfigFile(0);
//		System.out.println("Ecriture de " + cf.getAbsolutePath() + "... ");
		if (!WargameUtils.saveObjectToFile(cf, config) && JOptionPane.showConfirmDialog(null,
				"Erreur lors de la sauvegarde de " + cf.getAbsolutePath() + ", continuer ?",
				"Impossible de sauvegarder le jeu", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			System.exit(0);
		for (int i = 0; i < MAX_SAVE; i++) {
			cf = getConfigFile(i + 1);
//			System.out.println("Ecriture de " + cf.getAbsolutePath() + "... ");
			if (!WargameUtils.saveObjectToFile(cf, save[i]) && JOptionPane.showConfirmDialog(null,
					"Erreur lors de la sauvegarde de " + cf.getAbsolutePath() + ", continuer ?",
					"Impossible de sauvegarder le jeu", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
				System.exit(0);
		}
	}

	public void jouerIA() {
		int nombreIA = 0;
		for (ISoldat s : getSoldatEnnemis())
			s.choixIA();

		for (ISoldat s : getSoldatEnnemis()) {
			s.joueTour(config);
			if (!s.estMort())
				nombreIA++;
		}

		if (nombreIA == 0) {
			config.setCourant(FinJeu.GAGNE);

			showMenu(getMenuFin());
		}

		panneau.repaint();
	}

	private void startFrame() {
		long debFPSTime = lastFPSTime;
		lastFPSTime = System.currentTimeMillis();
		partialTick = (lastFPSTime - debFPSTime) / 1000F;
		fps = (1 / partialTick);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (frame.getContentPane() == panneau)
			panneau.onKey(e.getKeyCode());
		return false;
	}

	public void setConfig(IConfig cfg) {
		this.config = cfg;
	}

}
