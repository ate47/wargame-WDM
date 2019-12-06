package wargame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JButton;
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
import wargame.menu.MenuButton;
import wargame.menu.MenuFin;
import wargame.menu.MenuJeu;

public class Wargame implements ICarte {
	public class Case implements ICase {
		private boolean visible, visite, accessible;
		private Element e;
		private int x, y;

		public Case(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void click() {
			// Si on n'a toujours pas cliqué
			if (soldat == null) {
				if (e != null && (e instanceof Soldat)) {
					Soldat s = (Soldat) e;
					if (s.getType().getFaction() == factionJoueur) {
						if (s.aJoueCeTour())
							s.annulerTour();
						soldat = s;
						visibles = visible(s.getType().getPorteeVisuelle());
						for (Case v : visibles)
							if (v != null)
								v.accessible = true;
					}
				} else {
					for (Case c : visible(4))
						if (c != null)
							c.setVisible(true);
				}
			} else {
				// On a déjà cliqué

				// la case est vide et accessible
				if (e == null && accessible) {
					// on autorise le deplacement ...
					try {
						soldat.seDeplace(this);
					} catch (IllegalMoveException e1) {
						// (par construction impossible)
						e1.printStackTrace();
					}
					// ...et on retire les cases qu'on avait placé accessible
					for (Case c : visibles)
						if (c != null)
							c.accessible = false;

					soldat = null;
					visibles = null;

					// on reclique sur la même case
				} else if (equals(soldat.getPosition())) {
					for (Case c : visibles)
						if (c != null)
							c.accessible = false;
					soldat = null;
					visibles = null;

					// on clique sur un soldat accessible
				} else if (e != null && e instanceof Soldat && accessible) {
					Soldat s = (Soldat) e;
					if (s.getType().getFaction() == factionEnnemi) {
						try {
							soldat.seBat(s);
						} catch (IllegalMoveException e1) {
							e1.printStackTrace();
						}
						for (Case c : visibles)
							if (c != null)
								c.accessible = false;

						soldat = null;
						s = null;
						visibles = null;
					}
				} else {
					System.err.println("Mauvais emplacement");
					// TODO: afficher erreur
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
			return Wargame.this.visible(getX(), getY(), portee);
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

	public static final File SAVE_FILE = new File("wargame.cfg");
	public static final SoundAsset MUSIQUE_JEU = new SoundAsset("ambiance.wav");
	public static final int PERDU = 1;
	public static final int GAGNE = 2;

	public static final int NON = 3;

	private long lastFPSTime = System.currentTimeMillis();
	private float partialTick = 0F;
	private float fps;
	
	private PanneauJeu panneau;

	private MenuJeu menu;
	private MenuFin menuFin;
	private IConfig config;
	private JFrame frame;
	private int sx, sy;
	private Soldat[] soldatJoueur;
	private Soldat[] soldatEnnemis;
	private Faction factionJoueur;
	private Faction factionEnnemi;
	private Case[][] carte;
	private ICase hoveredCase;
	private Case[] visibles;
	private Soldat soldat;
	private JButton finTour;
	
	
	public Wargame() {
		frame = new GameFrame("Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(800, 600);

		finTour = new MenuButton("Fin de tour");
		finTour.setPreferredSize(new Dimension(200, 60));
		finTour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jouerSoldats();
				jouerIA();
				 /*switch(estfinie()) {
				case PERDU :menuFin = new MenuFin (Wargame.this,PERDU);break;
				case GAGNE :menuFin = new MenuFin(Wargame.this, GAGNE);break;
				case NON : ;
				default : ;	
				}*/
			}
		});

		panneau = new PanneauJeu(this);
		panneau.setPreferredSize(frame.getSize());
		panneau.add(finTour);

		menu = new MenuJeu(this);
		menu.setPreferredSize(frame.getSize());

		frame.setContentPane(menu);

		frame.setIconImage(new ImageAsset("ico.png").getImages()[0]);
	}

	@Override
	public void ajouteElement(int x, int y, Element e) {
		Case c = carte[x][y];
		c.setElement(e);
		e.setPosition(c);
	}

	public int estfinie() {
		if(this.tousMort(soldatJoueur)) {
			return PERDU;
		}
		if(this.tousMort(soldatEnnemis)) {
			return GAGNE;
		}
		return NON;
	}

	@Override
	public void genererCarte() {
		int i, j, k, carre, xmin, ymin, xmax, ymax, nb;
		int cameraX, cameraY;

		// LECTURE DES CONFIGURATIONS

		this.factionJoueur = config.getFactionJoueur();
		factionEnnemi = factionJoueur.getOthers();

		this.sx = config.getLargeurCarte();
		this.sy = config.getHauteurCarte();
		this.carte = new Case[sx][sy];
		for (i = 0; i < carte.length; i++)
			for (j = 0; j < carte[i].length; j++)
				carte[i][j] = this.new Case(i, j);

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
		nb = (int) (factionJoueur.nombreGenere() * config.getMapSize().getFactor());
		soldatJoueur = new Soldat[nb];

		for (i = 0; i < nb; i++)
			trouvePositionVide(xmin, ymin, xmax, ymax)
					.setElement(soldatJoueur[i] = new Soldat(factionJoueur.getRandomElement()));

		// On place la camera sur le milieu de notre zone
		cameraX = (xmax + xmin) / 2;
		cameraY = (ymax + ymin) / 2;

		nb = (int) (factionEnnemi.nombreGenere() * config.getDifficulty().getMultiplicateurEnnemis()
				* config.getMapSize().getFactor());

		// PRODUCTION DES AUTRES SOLDATS
		soldatEnnemis = new Soldat[nb * config.getDifficulty().getAreaCount()];
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
				trouvePositionVide(xmin, ymin, xmax, ymax)
						.setElement(soldatEnnemis[k] = new Soldat(factionEnnemi.getRandomElement()));

		}
		// ON LANCE L'AFFICHAGE

		panneau.init();
		panneau.lookAt(cameraX, cameraY);

		// CALCUL DU BROUILLARD DE GUERRE
		jouerSoldats();
	}

	@Override
	public Case getCase(int posX, int posY) {
		if (posX < 0 || posX >= carte.length || posY < 0 || posY >= carte[posX].length)
			return null;
		return carte[posX][posY];
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
		return sy;
	}

	/**
	 * @return the hoveredCase
	 */
	public ICase getHoveredCase() {
		return hoveredCase;
	}

	@Override
	public int getLargeur() {
		return sx;
	}

	public MenuJeu getMenu() {
		return menu;
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
	public ISoldat[] getSoldatEnnemis() {
		return soldatEnnemis;
	}

	@Override
	public Soldat[] getSoldatJoueur() {
		return soldatJoueur;
	}

	@Override
	public Case[] getVisibles() {
		return visibles;
	}

	@Override
	public void jouerSoldats() {
		int i, j;
		for (i = 0; i < carte.length; i++)
			for (j = 0; j < carte[i].length; j++)
				carte[i][j].setVisible(false);

		// 0 le BrG
		for (Soldat s : soldatJoueur)
			s.joueTour(getConfig());

		for (ISoldat s : soldatJoueur)
			if (!s.estMort())
				for (Case c : visible(s.getPosition().getX(), s.getPosition().getY(), s.getType().getPorteeVisuelle()))
					if (c != null) {
						c.setVisible(true);
						c.setVisite(true);
					}
		panneau.repaint();
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
	 * Supprime un soldat du jeu (vérifier avant si ça vie < 0)
	 * 
	 * @param soldat le soldat en question
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
		if (SAVE_FILE.exists()) {
			System.out.println("Lecture de " + SAVE_FILE.getAbsolutePath() + "... ");
			try (FileInputStream in = new FileInputStream(SAVE_FILE)) {
				ObjectInputStream ois = new ObjectInputStream(in);
				config = (Config) ois.readObject();
				return;
			} catch (IOException | ClassNotFoundException e) {
				if (JOptionPane.showConfirmDialog(null, "Voulez vous ecraser le fichier existant ?\n" + e.getMessage(),
						"Erreur lors de la lecture du fichier", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					System.exit(0);
			}
		}
		config = new Config();
		writeConfig();
	}

	public void setConfig(IConfig config) {
		this.config = config;
	}

	@Override
	public void setHoveredCase(ICase hoveredCase) {
		this.hoveredCase = hoveredCase;
	}

	public void showMenu(JPanel panel) {
		panel.setSize(frame.getRootPane().getSize());
		frame.setContentPane(panel);
	}

	public boolean tousMort(Soldat lesSoldats[]) {
		for(Soldat s : lesSoldats) {
			if (!s.estMort()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Case trouvePositionVide() {
		int x, y;
		Case c;

		while (true) {
			x = (int) (Math.random() * sx);
			y = (int) (Math.random() * sy);
			c = getCase(x, y);
			if (c.getElement() == null) {
				return c;
			}
		}
	}

	@Override
	public Case trouvePositionVide(ICase pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * trouve une position vide dans un rectangle(xmin, ymin, xmax, ymax)
	 * 
	 * @param xmin coord x du haut gauche du rectangle
	 * @param ymin coord y du haut gauche du rectangle
	 * @param xmax coord x du bas droit du rectangle
	 * @param ymax coord y du bas droit du rectangle
	 * @return
	 */
	public Case trouvePositionVide(int xmin, int ymin, int xmax, int ymax) {
		int x, y;
		Case c;

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
		return soldatJoueur[(int) (soldatJoueur.length * Math.random())];
	}

	@Override
	public Soldat trouveSoldat(ICase pos, Faction f) {
		// TODO Auto-generated method stub
		return null;
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
		System.out.println("Ecriture de " + SAVE_FILE.getAbsolutePath() + "... ");
		try (FileOutputStream out = new FileOutputStream(SAVE_FILE)) {
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(config);
		} catch (IOException e) {
			if (JOptionPane.showConfirmDialog(null, "Erreur lors de la sauvegarde, continuer ?\n" + e.getMessage(),
					"Impossible de sauvegarder le jeu", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
				System.exit(0);
		}
	}
	
	private void jouerIA() {
		for (Soldat s : soldatEnnemis)
			s.choixIA();

		for (Soldat s : soldatEnnemis) {
			s.joueTour(config);
		}
		panneau.repaint();
	}
	
	private void startFrame() {
		long debFPSTime = lastFPSTime;
		lastFPSTime = System.currentTimeMillis();
		partialTick = (lastFPSTime - debFPSTime) / 1000F;
		fps = (1 / partialTick);
	}

}
