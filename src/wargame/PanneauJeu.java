package wargame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;

import wargame.ICarte.ICase;

public class PanneauJeu extends JPanel implements ListenerAdapter, KeyListener {
	private static final long serialVersionUID = -8883115886343935124L;
	public static final ImageAsset GRASS = new ImageAsset("grass0.png", "grass1.png", "grass2.png", "grass3.png");
	public static final ImageAsset HOVER = new ImageAsset("hover.png");
	public static final ImageAsset INVISIBLE = new ImageAsset("brouillard_guerre0.png");
	public static final ImageAsset VISITE = new ImageAsset("visite.png");
	public static final ImageAsset INVISIBLE_HL = new ImageAsset("dark_brouillard_guerre.png");
	public static final ImageAsset INACCESSIBLE = new ImageAsset("inaccessible.png");
	public static final ImageAsset OPT_REGEN = new ImageAsset("regen.png");

	public static final Color BACKGROUND = new Color(0x505050);
	public static void dessinerFleche(Graphics g, int ox, int oy, int dx, int dy, int taille) {
		int x, y;
		double norme, angle;

		x = dx - ox;
		y = dy - oy;
		norme = Math.sqrt(x * x + y * y);
		// != 0 par construction

		angle = (Math.asin(y / norme) < 0 ? -1 : 1) * Math.acos(x / norme);

		g.setColor(Color.RED);
		g.drawLine(ox, oy, dx, dy);
		g.fillOval(ox - 5, oy - 5, 10, 10);

		x = dx + (int) (Math.cos(angle + 5 * Math.PI / 4) * taille);
		y = dy + (int) (Math.sin(angle + 5 * Math.PI / 4) * taille);

		g.drawLine(dx, dy, x, y);
		x = dx + (int) (Math.cos(angle + 3 * Math.PI / 4) * taille);
		y = dy + (int) (Math.sin(angle + 3 * Math.PI / 4) * taille);

		g.drawLine(dx, dy, x, y);
	}
	private float zoom;
	private int mouseX, mouseY;
	private Point originDragPoint;
	private float translateX, translateY;

	private float originTranslateX, originTranslateY;

	private Wargame carte;

	public PanneauJeu(Wargame carte) {
		this.carte = carte;
		zoom = Math.max(carte.getLargeur(), carte.getHauteur()) / 5;
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		setSize(800, 600);
	}

	public int getUnit() {
		return (int) (Math.min(getWidth(), getHeight()) / getUnitViewCount());
	}

	public float getUnitViewCount() {
		return Math.max(carte.getLargeur(), carte.getHauteur()) / zoom;
	}

	public boolean isInHexa(int x, int y, int w, int h) {
		if (mouseX > x && mouseY > y && mouseX < x + w && mouseY < y + h) {
			int rMouseY = mouseY - y;
			if (rMouseY < w * 2 / 3)
				if (rMouseY > w / 3)
					return true;
				else
					rMouseY = w / 3 - rMouseY;
			int rMouseX = Math.abs(mouseX - x - w / 2);
			return rMouseY < -h * rMouseX / w + h;
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(KeyEvent.VK_ESCAPE);
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			carte.getFrame().setContentPane(carte.getMenu());
			carte.getFrame().pack();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
	}

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
			if (!((newTranslateX <= 1) ^ (-newTranslateX + (float) getWidth() / unit <= carte.getLargeur() + 1.5F)))
				translateX = newTranslateX;

			if (!((newTranslateY <= 1)
					^ (-newTranslateY + (float) getHeight() / unit <= (carte.getHauteur() + 2) * 0.6666F)))
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
		if (e.getButton() == MouseEvent.BUTTON3) {
			originTranslateX = translateX;
			originTranslateY = translateY;
			originDragPoint = e.getPoint();
			return;
		}
		originDragPoint = null;
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

	public void zoom(int mouseX, int mouseY, float factor) {
		float newZoom = Math.max(zoom + factor / 10F, 1F);
		float centerX = mouseX;
		float centerY = mouseY;

		int unit = getUnit();
		float centerOfZoomX = -this.translateX + centerX / unit;
		float centerOfZoomY = -this.translateY + centerY / unit;

		zoom = newZoom;
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
				if (j % 2 == 0) {
					x = i * unit;
					y = (int) ((0.6666F * j) * unit);
				} else {
					x = (int) ((0.5000F + i) * unit);
					y = (int) ((0.6666F * j) * unit);
				}

				if (c == null) {
					g.drawImage(INVISIBLE_HL.getImageFromPosition(i, j), x, y, unit, unit, this);
					continue;
				}
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

				if (carte.getSoldatClick() != null && !c.isAccessible())
					g.drawImage(INACCESSIBLE.getImageFromPosition(i, j), x, y, unit, unit, this);

				if (!dedans && isInHexa(translateX + x, translateY + y, unit, unit)) {
					dedans = true;
					carte.setHoveredCase(c);
					g.drawImage(HOVER.getImages()[0], x, y, unit, unit, this);
				}
			}
		for (ISoldat s : carte.getSoldatEnAttente()) {
			switch (s.getProchainMouvement()) {
			case DEPLACEMENT:
				x = s.getPosition().getX();
				y = s.getPosition().getY();
				if (y % 2 == 0) {
					oldX = x * unit;
					oldY = (int) ((0.6666F * y) * unit);
				} else {
					oldX = (int) ((0.5000F + x) * unit);
					oldY = (int) ((0.6666F * y) * unit);
				}
				x = s.getNextPosition().getX();
				y = s.getNextPosition().getY();
				if (y % 2 == 0) {
					newX = x * unit;
					newY = (int) ((0.6666F * y) * unit);
				} else {
					newX = (int) ((0.5000F + x) * unit);
					newY = (int) ((0.6666F * y) * unit);
				}
				g.setColor(Color.red);
				dessinerFleche(g, oldX + unit / 2, oldY + unit / 2, newX + unit / 2, newY + unit / 2, unit / 4);
				break;
			case RIEN:
				x = s.getPosition().getX();
				y = s.getPosition().getY();
				if (x >= translateXStart && x < translateXEnd && y >= translateYStart && y < translateYEnd)
					g.drawImage(OPT_REGEN.getImageFromPosition(x, y), x, y, unit, unit, this);
				break;
			case COMBAT:
				x = s.getPosition().getX();
				y = s.getPosition().getY();
				if (y % 2 == 0) {
					oldX = x * unit;
					oldY = (int) ((0.6666F * y) * unit);
				} else {
					oldX = (int) ((0.5000F + x) * unit);
					oldY = (int) ((0.6666F * y) * unit);
				}
				x = s.getCible().getPosition().getX();
				y = s.getCible().getPosition().getY();
				if (y % 2 == 0) {
					newX = x * unit;
					newY = (int) ((0.6666F * y) * unit);
				} else {
					newX = (int) ((0.5000F + x) * unit);
					newY = (int) ((0.6666F * y) * unit);
				}
				g.setColor(Color.red);
				dessinerFleche(g, oldX + unit / 2, oldY + unit / 2, newX + unit / 2, newY + unit / 2, unit / 4);
				break;
				
			default:
				break;
			}

		}
		g.translate(-translateX, -translateY);
		repaint();
	}
}
