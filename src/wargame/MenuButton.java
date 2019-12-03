package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import wargame.utils.WargameUtils;

public class MenuButton extends JButton implements MouseListener {

	private static final long serialVersionUID = 1175305120107911940L;
	private static final Color FOREGROUND_HOVER = new Color(0);
	private static final Color FOREGROUND = new Color(0xDDDDDD);
	private static final ImageAsset BUTTON_IMAGE = new ImageAsset("button.png");
	private static final ImageAsset BUTTON_IMAGE_HOVER = new ImageAsset("button_hover.png");
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
		if (mouseIn) {
			g.drawImage((BUTTON_IMAGE_HOVER).getImageFromTime(), 0, 0, getWidth() - 1, getHeight() - 1, null);
			g.setColor(FOREGROUND_HOVER);
		} else {
			g.drawImage((BUTTON_IMAGE).getImageFromTime(), 0, 0, getWidth() - 1, getHeight() - 1, null);
			g.setColor(FOREGROUND);
		}
		Font old = getFont();
		g.setFont(old.deriveFont((float) (getHeight() / 4)));
		WargameUtils.drawCenter(g, getWidth() / 2, getHeight() / 2, getText());
		g.setFont(old);
	}
}
