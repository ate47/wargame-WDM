package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;

import wargame.ICarte.ICase;

public class PanneauJeu extends JPanel implements ListenerAdapter {
	private static final long serialVersionUID = -8883115886343935124L;
	public static final ImageAsset GRASS = new ImageAsset("grass0.png", "grass1.png", "grass2.png", "grass3.png");
	public static final ImageAsset HOVER = new ImageAsset("hover.png");
	public static final ImageAsset INVISIBLE = new ImageAsset("brouillard_guerre0.png");
	public static final ImageAsset VISITE = new ImageAsset("visite.png");
	private float zoom;
	private int mouseX, mouseY;
	private Point originDragPoint;
	private float translateX, translateY;
	private float originTranslateX, originTranslateY;
	private ICarte carte;

	public PanneauJeu(ICarte carte) {
		this.carte = carte;
		zoom = Math.max(carte.getLargeur(), carte.getHauteur()) / 5;
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
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

	public float getUnitViewCount() {
		return Math.max(carte.getLargeur(), carte.getHauteur()) / zoom;
	}

	public int getUnit() {
		return (int) (Math.min(getWidth(), getHeight()) / getUnitViewCount());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (originDragPoint != null) {
			int unit = getUnit();
			translateX = (float) (originTranslateX + (e.getX() - originDragPoint.getX()) / unit);
			translateY = (float) (originTranslateY + (e.getY() - originDragPoint.getY()) / unit);
			repaint();
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
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		originDragPoint = null;
	}

	public void zoom(int mouseX, int mouseY, float factor) {
		float newZoom = Math.max(zoom + factor / 10F, 1);

		zoom = newZoom;

		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getX(), e.getY(), (float) -e.getPreciseWheelRotation());
	}

	public void lookAt(int x, int y) {
		// TODO
	}
	
	private static final Color BACKGROUND = new Color(0x505050);

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(getFont());
		int unit = getUnit();
		int translateX = (int) (this.translateX * unit);
		int translateY = (int) (this.translateY * unit);
		g.translate(translateX, translateY);
		boolean dedans = false;
		int id = 0, jd = 0;
		for (int i = 0; i < carte.getLargeur(); i++)
			for (int j = 0; j < carte.getHauteur(); j++) {
				int x;
				int y;
				ICase c = carte.getCase(i, j);
				Element e = c.getElement();
				if (j % 2 == 0) {
					x = i * unit;
					y = (int) ((0.6666F * j) * unit);
				} else {
					x = (int) ((0.5000F + i) * unit);
					y = (int) ((0.6666F * j) * unit);
				}
				if (c.isVisible()) {
					g.drawImage(GRASS.getImageFromPosition(i, j), x, y, unit - 2, unit - 2, this);
					if (e != null)
						g.drawImage(e.getSkin().getImageFromTime(), x, y, unit - 2, unit - 2, this);
				} else if (c.isVisite()) {
					if (e != null && e instanceof Obstacle)
						g.drawImage(e.getSkin().getImageFromTime(), x, y, unit - 2, unit - 2, this);
					else
						g.drawImage(GRASS.getImageFromPosition(i, j), x, y, unit - 2, unit - 2, this);
					g.drawImage(VISITE.getImageFromPosition(i, j), x, y, unit - 2, unit - 2, this);
				} else
					g.drawImage(INVISIBLE.getImageFromPosition(i, j), x, y, unit - 2, unit - 2, this);
				if (!dedans && isInHexa(translateX + x, translateY + y, unit - 2, unit - 2)) {
					dedans = true;
					id = i;
					jd = j;
					g.drawImage(HOVER.getImages()[0], x, y, unit - 2, unit - 2, this);
				}
			}
		g.translate(-translateX, -translateY);
		if (dedans) {
			g.setColor(Color.WHITE);
			g.drawString("(" + id + "," + jd + ")", mouseX + 10, mouseY - 10);
		}
		repaint();
		g.setColor(Color.WHITE);
		g.drawString(translateX + " " + translateY + " " + getUnit(), 0, 30);
	}
}
