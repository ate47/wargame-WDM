package wargame.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import wargame.ListenerAdapter;
import wargame.Obstacle;
import wargame.PanneauJeu;
import wargame.Wargame;
import wargame.config.Faction;
import wargame.utils.WargameUtils;

/**
 * Dessinateur de faux jeu, les composants doivent être ajouté dans la méthode
 * {@link #init()}
 */
public abstract class PanelMenu extends JPanel implements ListenerAdapter {
	private static final long serialVersionUID = -6212292517967101515L;
	private static final Color SURCOUCHE = new Color(0x66000000, true);
	private static double shiftX, shiftY;
	private static double translateX, translateY;
	static {
		// On place une vitesse de base
		double angle = Math.random() * Math.PI * 2;
		double speed = Math.random() + 0.2;
		shiftX = Math.cos(angle) * speed;
		shiftY = Math.sin(angle) * speed;
	}

	protected final Wargame jeu;
	private JComponent[] components;
	private int buttonCount;

	public PanelMenu(Wargame game, int buttons) {
		super(null);
		this.jeu = game;
		this.components = new JComponent[buttons];
		addComponentListener(this);
		addMouseListener(this);
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

	/**
	 * @return le haut du menu (selon les boutons)
	 */
	protected int getTop() {
		return getHeight() / 3;
	}

	/**
	 * Ajoute un bouton
	 * 
	 * @param text
	 *            le texte sur le bouton
	 * @param listener
	 *            quoi executer quand on clique dessus
	 * @return le bouton
	 */
	protected JButton addButton(String text, Runnable listener) {
		return addButton(text, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listener.run();
			}
		});
	}

	/**
	 * Ajoute un bouton
	 * 
	 * @param text
	 *            le texte sur le bouton
	 * @param listener
	 *            quoi executer quand on clique dessus
	 * @return le bouton
	 */
	protected JButton addButton(String text, ActionListener listener) {
		JButton button = new MenuButton(text);
		button.addActionListener(listener);
		addComponent(button);
		return button;
	}

	/**
	 * Ajoute un label
	 * 
	 * @param text
	 *            le texte du label
	 */
	protected void addLabel(String text) {
		JButton button = new MenuButton(text, null, null);
		addComponent(button);
	}

	/**
	 * Ajoute un component
	 * 
	 * @param text
	 *            le component
	 */
	protected void addComponent(JComponent component) {
		int sizeX = 2 * getWidth() / 5;
		int sizeY = getHeight() / 10;
		int decalage = getHeight() / 50;
		add(components[buttonCount] = component);
		component.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (decalage + sizeY) * buttonCount, sizeX, sizeY);
		buttonCount++;
		repaint();
	}

	/**
	 * dessine le menu
	 * 
	 * @param g
	 *            où
	 * @param width
	 *            largeur
	 * @param height
	 *            hauteur
	 */
	public void paint(Graphics g, int width, int height) {
		float partialSecond = jeu.getPartialTick();
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
		int i, j, x, y, seed;
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

				seed = (i * j) % (5 * 4 * 3 * 2);
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
	public void mouseClicked(MouseEvent e) {
		shiftX = 1 - ((double) e.getX()) * 2 / getWidth();
		shiftY = 1 - ((double) e.getY()) * 2 / getHeight();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paint(g, getWidth(), getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void removeAll() {
		buttonCount = 0;
		super.removeAll();
	}

	/**
	 * Initialise les composants du menu
	 */
	public abstract void init();

}
