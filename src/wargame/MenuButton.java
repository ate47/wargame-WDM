package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class MenuButton extends JButton implements MouseListener {
	public static void drawCenter(Graphics g, int x, int y, String s) {
		Rectangle2D text = g.getFontMetrics(g.getFont()).getStringBounds(s, g);
		g.drawString(s, x - (int) (text.getWidth() / 2), y - 6 + (int) (text.getHeight() / 2));
	}
	private static final long serialVersionUID = 1175305120107911940L;
	private static final Color BACKGROUND = new Color(0x444444);
	private static final Color FOREGROUND = new Color(0xDDDDDD);
	private boolean mouseIn = false;

	public MenuButton(String texte) {
		super(texte);
		setBorderPainted(false);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseIn = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseIn = false;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Color a = BACKGROUND;
		Color b = FOREGROUND;
		if (!mouseIn)
			g.setColor(a);
		else
			g.setColor(b);
		
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		
		if (mouseIn)
			g.setColor(a);
		else
			g.setColor(b);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setFont(g.getFont().deriveFont((float) (getHeight() / 4)));
		drawCenter(g, getWidth() / 2, getHeight() / 2, getText());
	}
}
