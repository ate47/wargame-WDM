package wargame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuJeu extends JPanel {
	private static final ImageAsset LOGO = new ImageAsset("logo.png");
	private static final long serialVersionUID = 4058740506271457913L;
	private Wargame jeu;

	public MenuJeu(Wargame jeu) {
		super(null);
		this.jeu = jeu;
		JButton nvPartie = new MenuButton("Nouvelle Partie");
		JButton sauvegarder = new MenuButton("Sauvegarder la partie");
		JButton charger = new MenuButton("Charger une partie");
		JButton quitter = new MenuButton("Quitter le jeu");
		PanneauRestauration restauration = new PanneauRestauration(jeu);

		nvPartie.setBounds((getWidth() - 250) / 2, getHeight() / 3 + 60 * 0, 250, 40);
		sauvegarder.setBounds((getWidth() - 250) / 2, getHeight() / 3 + 60 * 1, 250, 40);
		charger.setBounds((getWidth() - 250) / 2, getHeight() / 3 + 60 * 2, 250, 40);
		quitter.setBounds((getWidth() - 250) / 2, getHeight() / 3 + 60 * 3, 250, 40);

		add(nvPartie);
		add(sauvegarder);
		add(charger);
		add(quitter);

		addComponentListener(new ListenerAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				int sizeX = getWidth() / 3;
				int sizeY = getHeight() / 10;
				int decalage = getHeight() / 50;
				nvPartie.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (sizeY + decalage) * 0, sizeX, sizeY);
				sauvegarder.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (sizeY + decalage) * 1, sizeX, sizeY);
				charger.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (sizeY + decalage) * 2, sizeX, sizeY);
				quitter.setBounds((getWidth() - sizeX) / 2, getHeight() / 3 + (sizeY + decalage) * 3, sizeX, sizeY);
			}

		});

		nvPartie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.genererCarte();
				jeu.getFrame().setContentPane(jeu.getPanneau());
				jeu.getFrame().pack();
			}
		});

		sauvegarder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		charger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jeu.getFrame().setContentPane(restauration);
				jeu.getFrame().pack();
			}
		});

		quitter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jeu.getFakeGameDrawer().paintComponent(g, getWidth(), getHeight());
		int sizeX = getWidth() / 3;
		int sizeY = sizeX * 2 / 5;
		int decalage = getHeight() / 50;
		g.drawImage(LOGO.getImageFromTime(), (getWidth() - sizeX) / 2, getHeight() / 3 - (sizeY + decalage), sizeX,
				sizeY, this);
		repaint();
	}

}
