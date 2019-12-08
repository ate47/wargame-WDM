package wargame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import wargame.assets.ImageAsset;
import wargame.menu.MenuButton;
import wargame.utils.WargameUtils;

public class PanneauJeu extends JPanel implements ListenerAdapter {
	private static final long serialVersionUID = -8883115886343935124L;
	/**
	 * Image de l'herbe
	 */
	public static final ImageAsset GRASS = new ImageAsset("grass0.png", "grass1.png", "grass2.png", "grass3.png");
	/**
	 * Image quand la souris est sur un hexagone
	 */
	public static final ImageAsset HOVER = new ImageAsset("hover.png");
	/**
	 * Image du brouillard de guerre
	 */
	public static final ImageAsset INVISIBLE = new ImageAsset("brouillard_guerre0.png");
	/**
	 * Image quand une zone est dans le brouillard de guerre visité
	 */
	public static final ImageAsset VISITE = new ImageAsset("visite.png");
	/**
	 * Image quand l'hexagone est hors de la carte
	 */
	public static final ImageAsset INVISIBLE_HL = new ImageAsset("dark_brouillard_guerre.png");
	/**
	 * Image quand un hexagone n'est pas accessible
	 */
	public static final ImageAsset INACCESSIBLE = new ImageAsset("inaccessible.png");
	/**
	 * Image de regeneration
	 */
	public static final ImageAsset OPT_REGEN = new ImageAsset("regen.png");

	/**
	 * Fond du jeu
	 */
	public static final Color BACKGROUND = new Color(0x505050);

	/**
	 * Dessine une fleche sur un {@link Graphics}
	 * 
	 * @param g
	 *            le graphique
	 * @param ox
	 *            coord x de l'origine
	 * @param oy
	 *            coord y de l'origine
	 * @param dx
	 *            coord x de la destination
	 * @param dy
	 *            coord y de la destination
	 * @param taille
	 *            taille des pointes
	 */
	public static void dessinerFleche(Graphics g, int ox, int oy, int dx, int dy, int taille) {
		int x, y;
		double norme, angle;

		x = dx - ox;
		y = dy - oy;
		norme = Math.sqrt(x * x + y * y);
		// != 0 par construction

		angle = (Math.asin(y / norme) < 0 ? -1 : 1) * Math.acos(x / norme);

		g.drawLine(ox, oy, dx, dy);
		g.fillOval(ox - 5, oy - 5, 10, 10);

		x = dx + (int) (Math.cos(angle + 5 * Math.PI / 4) * taille);
		y = dy + (int) (Math.sin(angle + 5 * Math.PI / 4) * taille);

		g.drawLine(dx, dy, x, y);
		x = dx + (int) (Math.cos(angle + 3 * Math.PI / 4) * taille);
		y = dy + (int) (Math.sin(angle + 3 * Math.PI / 4) * taille);

		g.drawLine(dx, dy, x, y);
	}

	private int mouseX, mouseY;
	private Point originDragPoint;
	private float originTranslateX, originTranslateY;
	private float translateX, translateY;
	private float zoom;

	private Wargame carte;

	public PanneauJeu(Wargame jeu) {
		this.carte = jeu;
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setSize(800, 600);
		JButton finTour = new MenuButton("Fin de tour");
		finTour.setPreferredSize(new Dimension(200, 60));
		finTour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jeu.jouerSoldats();
				jeu.jouerIA();
			}
		});
		add(finTour);
	}

	/**
	 * @return retourne la taille d'une unité de mesure
	 */
	public int getUnit() {
		return (int) (Math.min(getWidth(), getHeight()) / getUnitViewCount());
	}

	/**
	 * @return le nombre d'unité visible au maximum sur la frame
	 */
	public float getUnitViewCount() {
		return Math.max(carte.getLargeur(), carte.getHauteur()) / zoom;
	}

	/**
	 * Initialise le panneau
	 */
	public void init() {
		zoom = Math.max(carte.getLargeur(), carte.getHauteur()) / 5;
	}

	/**
	 * Place la camera de jeu sur une position (x, y) (au centre)
	 * 
	 * @param x
	 *            coord x
	 * @param y
	 *            coord y
	 */
	public void lookAt(int x, int y) {
		int unit = getUnit();
		if (y % 2 == 0) {
			translateX = -(x - (float) getWidth() / unit / 2);
			translateY = -0.6666F * (y - (float) getHeight() / unit / 2);
		} else {
			translateX = -0.5000F - (x - (float) getWidth() / unit / 2);
			translateY = -0.6666F * (y - (float) getHeight() / unit / 2);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (originDragPoint != null) {
			int unit = getUnit();
			float newTranslateX = (float) (originTranslateX + (e.getX() - originDragPoint.getX()) / unit);
			float newTranslateY = (float) (originTranslateY + (e.getY() - originDragPoint.getY()) / unit);
			// if (!((newTranslateX <= 1) ^ (-newTranslateX + (float) getWidth() / unit <=
			// carte.getLargeur() + 1.5F)))
			translateX = newTranslateX;

			// if (!((newTranslateY <= 1)
			// ^ (-newTranslateY + (float) getHeight() / unit <= (carte.getHauteur() + 2) *
			// 0.6666F)))
			translateY = newTranslateY;

		}
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Clique droit
		if (e.getButton() == MouseEvent.BUTTON3) {
			originTranslateX = translateX;
			originTranslateY = translateY;
			originDragPoint = e.getPoint();
			return;
		}
		originDragPoint = null;
		// Clique gauche
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (carte.getHoveredCase() != null)
				carte.getHoveredCase().click();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		originDragPoint = null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getX(), e.getY(), (float) -e.getPreciseWheelRotation());
	}

	/**
	 * Zoom a partir d'une position de souris
	 * 
	 * @param mouseX
	 *            posX de la souris à l'écran
	 * @param mouseY
	 *            posY de la souris à l'écran
	 * @param factor
	 *            facteur de zoom
	 */
	public void zoom(int mouseX, int mouseY, float factor) {
		// on cherche le nouveau zoom
		float newZoom = Math.max(zoom + factor / 10F, 1F);
		// mouseX devient le centre de l'écran
		float centerX = mouseX;
		float centerY = mouseY;

		// calcul du centre relatif
		int unit = getUnit();
		float centerOfZoomX = -this.translateX + centerX / unit;
		float centerOfZoomY = -this.translateY + centerY / unit;

		// on installe le nouveau zoom
		zoom = newZoom;

		// et on replace le centre relatif
		int newUnit = getUnit();
		this.translateX = -centerOfZoomX + centerX / newUnit;
		this.translateY = -centerOfZoomY + centerY / newUnit;

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(getFont());
		int unit = getUnit();
		int translateX = (int) (this.translateX * unit);
		int translateY = (int) (this.translateY * unit);
		int translateXStart = -(int) this.translateX - 2;
		int translateXEnd = (int) (translateXStart + getWidth() * 1F / unit) + 4;
		int translateYStart = -(int) (this.translateY / 0.6666F) - 2;
		int translateYEnd = (int) (translateYStart + getHeight() / 0.6666F / unit) + 4;
		boolean dedans = false;
		int x, y;
		int oldX, oldY, newX, newY;

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(unit / 20));
		}
		g.translate(translateX, translateY);
		ICase c;
		Element e;
		carte.setHoveredCase(null);
		for (int i = translateXStart; i < translateXEnd; i++)
			for (int j = translateYStart; j < translateYEnd; j++) {
				c = carte.getCase(i, j);

				if (c == null) {
					continue;
				}
				x = c.getRelativeX(unit);
				y = c.getRelativeY(unit);
				e = c.getElement();
				if (c.isVisible()) {
					if (e != null) { // Obstacle ou soldat
						if (!(e instanceof Obstacle))
							g.drawImage(GRASS.getImageFromPosition(i, j), x, y, unit, unit, this);
						g.drawImage(e.getSkin().getImageFromTime(), x, y, unit, unit, this);
					} else
						g.drawImage(GRASS.getImageFromPosition(i, j), x, y, unit, unit, this);
				} else if (c.isVisite()) {
					if (e != null && e instanceof Obstacle)
						g.drawImage(e.getSkin().getImageFromTime(), x, y, unit, unit, this);
					else
						g.drawImage(GRASS.getImageFromPosition(i, j), x, y, unit, unit, this);
					g.drawImage(VISITE.getImageFromPosition(i, j), x, y, unit, unit, this);
				} else
					g.drawImage(INVISIBLE.getImageFromPosition(i, j), x, y, unit, unit, this);

				if (carte.getSoldatClick() != null && !c.isTirable())
					g.drawImage(INACCESSIBLE.getImageFromPosition(i, j), x, y, unit, unit, this);

				if (!dedans && WargameUtils.isInHexa(mouseX, mouseY, translateX + x, translateY + y, unit, unit)) {
					dedans = true;
					carte.setHoveredCase(c);
					g.drawImage(HOVER.getImages()[0], x, y, unit, unit, this);
				}
			}

		Font old = g.getFont();
		g.setFont(old.deriveFont(unit / 10F - 2F));

		for (ISoldat s : carte.getSoldatJoueur())
			if (!s.estMort()) {
				x = s.getPosition().getX();
				y = s.getPosition().getY();
				if (y % 2 == 0) {
					oldX = x * unit;
					oldY = (int) ((0.6666F * y) * unit);
				} else {
					oldX = (int) ((0.5000F + x) * unit);
					oldY = (int) ((0.6666F * y) * unit);
				}
				switch (s.getProchainMouvement()) {
				case DEPLACEMENT:
					x = s.getNextPosition().getX();
					y = s.getNextPosition().getY();
					if (y % 2 == 0) {
						newX = x * unit;
						newY = (int) ((0.6666F * y) * unit);
					} else {
						newX = (int) ((0.5000F + x) * unit);
						newY = (int) ((0.6666F * y) * unit);
					}
					g.setColor(Color.BLUE);
					dessinerFleche(g, oldX + unit / 2, oldY + unit / 2, newX + unit / 2, newY + unit / 2, unit / 4);
					break;
				case RIEN:
					if (x >= translateXStart && x < translateXEnd && y >= translateYStart && y < translateYEnd
							&& s.getVie() < s.getType().getPointsDeVie())
						g.drawImage(OPT_REGEN.getImageFromPosition(x, y), oldX, oldY, unit, unit, this);
					break;
				case COMBAT:
					x = s.getCible().getPosition().getX();
					y = s.getCible().getPosition().getY();
					if (y % 2 == 0) {
						newX = x * unit;
						newY = (int) ((0.6666F * y) * unit);
					} else {
						newX = (int) ((0.5000F + x) * unit);
						newY = (int) ((0.6666F * y) * unit);
					}
					g.setColor(Color.RED);
					dessinerFleche(g, oldX + unit / 2, oldY + unit / 2, newX + unit / 2, newY + unit / 2, unit / 4);
					break;

				default:
					break;
				}
				g.setColor(Color.GRAY);
				g.fillRect(oldX + unit / 8, oldY + unit / 10, unit * 6 / 8, unit / 10);
				float percentage = (float) s.getVie() / s.getType().getPointsDeVie();
				if (percentage < .25F)
					g.setColor(Color.RED);
				else if (percentage < .50F)
					g.setColor(Color.ORANGE);
				else if (percentage < .75F)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.GREEN);
				g.fillRect(oldX + unit / 7, oldY + unit / 10 + 1, (int) (percentage * unit * 5 / 7), unit / 10 - 2);
				g.setColor(Color.black);
				WargameUtils.drawCenter(g, oldX + unit / 8 + unit * 6 / 16, oldY + unit / 10 + unit / 20,
						s.getVie() + " / " + s.getType().getPointsDeVie());
			}
		for (ISoldat s : carte.getSoldatEnnemis())
			if (!s.estMort() && s.getPosition().isVisible()) {
				x = s.getPosition().getX();
				y = s.getPosition().getY();
				if (y % 2 == 0) {
					oldX = x * unit;
					oldY = (int) ((0.6666F * y) * unit);
				} else {
					oldX = (int) ((0.5000F + x) * unit);
					oldY = (int) ((0.6666F * y) * unit);
				}

				g.setColor(Color.GRAY);
				g.fillRect(oldX + unit / 8, oldY + unit / 10, unit * 6 / 8, unit / 10);
				float percentage = (float) s.getVie() / s.getType().getPointsDeVie();
				if (percentage < .25F)
					g.setColor(Color.RED);
				else if (percentage < .50F)
					g.setColor(Color.ORANGE);
				else if (percentage < .75F)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.GREEN);
				g.fillRect(oldX + unit / 7, oldY + unit / 10 + 1, (int) (percentage * unit * 5 / 7), unit / 10 - 2);
				g.setColor(Color.black);
				WargameUtils.drawCenter(g, oldX + unit / 8 + unit * 6 / 16, oldY + unit / 10 + unit / 20,
						s.getVie() + " / " + s.getType().getPointsDeVie());
			}

		g.setFont(old);
		g.translate(-translateX, -translateY);
	}

	public void onKey(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE:
			carte.showMenu(carte.getMenuPause());
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_KP_UP:
			zoom(mouseX, mouseY, +0.75F);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_KP_DOWN:
			zoom(mouseX, mouseY, -0.75F);
			break;
		default:
			break;
		}
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
}
