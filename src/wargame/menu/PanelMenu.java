package wargame.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import wargame.IType.Faction;
import wargame.Obstacle;
import wargame.PanneauJeu;
import wargame.Wargame;
import wargame.utils.WargameUtils;

/**
 * Dessinateur de faux jeu
 */
public class PanelMenu extends JPanel implements ComponentListener {
	private static final long serialVersionUID = -6212292517967101515L;
	private static final Color SURCOUCHE = new Color(0x44000000, true);
	private static double shiftX, shiftY;
	private static double translateX, translateY;
	static {
		double angle = Math.random() * Math.PI * 2;
		double speed = Math.random() + 0.2;
		shiftX = Math.cos(angle) * speed;
		shiftY = Math.sin(angle) * speed;
	}

	private Wargame game;
	private JComponent[] components;
	private int buttonCount;

	public PanelMenu(Wargame game, int buttons) {
		super(null);
		this.game = game;
		this.components = new JComponent[buttons];
		addComponentListener(this);
		setPreferredSize(game.getFrame().getRootPane().getSize());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int sizeX = 2 * getWidth() / 5;
		int sizeY = getHeight() / 10;
		int decalage = getHeight() / 50;
		for (int i = 0; i < buttonCount; i++) {
			components[i].setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (decalage + sizeY) * i, sizeX, sizeY);
		}
	}

	protected int getTop() {
		return getHeight() / 3;
	}

	protected void addButton(String text, ActionListener listener) {
		JButton button = new MenuButton(text);
		button.addActionListener(listener);
		addComponend(button);
	}

	protected void addComponend(JComponent component) {
		int sizeX = 2 * getWidth() / 5;
		int sizeY = getHeight() / 10;
		int decalage = getHeight() / 50;
		add(components[buttonCount] = component);
		component.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (decalage + sizeY) * buttonCount, sizeX, sizeY);
		buttonCount++;
		repaint();
	}

	public void paint(Graphics g, int width, int height) {
		float partialSecond = game.getPartialTick();
		translateX += shiftX * partialSecond;
		translateY += shiftY * partialSecond;
		Shape oldClip = g.getClip();
		g.setClip(0, 0, width, height);
		g.setColor(PanneauJeu.BACKGROUND);
		g.fillRect(0, 0, width, height);

		int unit = (int) (Math.min(width, height) / 10);
		int translateXStart = -(int) PanelMenu.translateX - 2;
		int translateXEnd = (int) (translateXStart + width * 1F / unit) + 4;
		int translateYStart = -(int) (PanelMenu.translateY / 0.6666F) - 2;
		int translateYEnd = (int) (translateYStart + height / 0.6666F / unit) + 4;
		int i, j, x, y;
		g.translate((int) (translateX * unit), (int) (translateY * unit));
		for (i = translateXStart; i < translateXEnd; i++)
			for (j = translateYStart; j < translateYEnd; j++) {
				if (j % 2 == 0) {
					x = i * unit;
					y = (int) ((0.6666F * j) * unit);
				} else {
					x = (int) ((0.5000F + i) * unit);
					y = (int) ((0.6666F * j) * unit);
				}

				int seed = i * j;
				switch (seed % 5) {
				case 1: // Soldat
					g.drawImage(PanneauJeu.GRASS.getImageFromPosition(i, i), x, y, unit, unit, null);
					g.drawImage(WargameUtils
							.getFromSeed(WargameUtils.getFromSeed(Faction.values(), seed + i).getTypes(), seed + j)
							.getImage().getImageFromPosition(i, i), x, y, unit, unit, null);
					break;
				case 0: // Obstacle
					g.drawImage(WargameUtils.getFromSeed(Obstacle.TypeObstacle.values(), seed + j).getImage()
							.getImageFromPosition(i, j), x, y, unit, unit, null);
					break;
				default: // Grass
					g.drawImage(PanneauJeu.GRASS.getImageFromPosition(i, i), x, y, unit, unit, null);
					break;
				}
			}
		g.translate(-(int) (translateX * unit), -(int) (translateY * unit));

		g.setColor(SURCOUCHE);
		g.fillRect(0, 0, width, height);
		g.setClip(oldClip);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(g, getWidth(), getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}

}
