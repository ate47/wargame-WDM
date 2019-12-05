package wargame.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import wargame.assets.ImageAsset;
import wargame.utils.WargameUtils;

public class MenuButton extends JButton implements MouseListener {

	private static final long serialVersionUID = 1175305120107911940L;
	private static final Color FOREGROUND_HOVER = new Color(0);
	private static final Color FOREGROUND = new Color(0xDDDDDD);
	private static final Color HOVER = new Color(0x33ffffff, true);
	private static final Color SELECTED = new Color(0x44ffffff, true);
	private static final Color SELECTED_BORDER = new Color(SELECTED.getRGB());
	private static final ImageAsset BUTTON_IMAGE = new ImageAsset("button.png");
	private static final ImageAsset BUTTON_IMAGE_HOVER = new ImageAsset("button_hover.png");
	private boolean mouseIn = false;
	private boolean used = false;
	private ImageAsset image;
	private ImageAsset imageHover;
	private Color text;
	private Color textHover;

	public MenuButton(String texte, ImageAsset image, ImageAsset imageHover, Color text, Color textHover) {
		super(texte);
		this.image = image;
		this.imageHover = imageHover;
		this.text = text;
		this.textHover = textHover;
		setBorderPainted(false);
		addMouseListener(this);
	}

	public MenuButton(String texte, ImageAsset image, ImageAsset imageHover) {
		this(texte, image, imageHover, FOREGROUND, FOREGROUND_HOVER);
	}

	public MenuButton(String texte) {
		this(texte, BUTTON_IMAGE, BUTTON_IMAGE_HOVER);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseIn = false;
		repaint();
	}

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

	public void setUtilise(boolean b) {
		used = b;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (mouseIn) {
			if (imageHover == null) {
				g.drawImage((image).getImageFromTime(), 0, 0, getWidth() - 1, getHeight() - 1, null);
				g.setColor(HOVER);
				g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			} else
				g.drawImage((imageHover).getImageFromTime(), 0, 0, getWidth() - 1, getHeight() - 1, null);
			g.setColor(textHover);
		} else {
			g.drawImage((image).getImageFromTime(), 0, 0, getWidth() - 1, getHeight() - 1, null);
			g.setColor(text);
		}

		if (used) {
			g.setColor(SELECTED);
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
			g.setColor(SELECTED_BORDER);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
		if (getText().isEmpty())
			return;

		Font old = getFont();
		g.setFont(old.deriveFont((float) (getHeight() / 4)));
		WargameUtils.drawCenter(g, getWidth() / 2, getHeight() / 2, getText());
		g.setFont(old);
	}

}
