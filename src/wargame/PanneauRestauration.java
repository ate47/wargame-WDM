package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanneauRestauration extends JPanel {

	private static final long serialVersionUID = 3092784374487373890L;
	private Wargame jeu;

	public PanneauRestauration(Wargame jeu) {
		this.jeu = jeu;
		JButton retour = new MenuButton("retour");
		setPreferredSize(jeu.getFrame().getSize());
		retour.setPreferredSize(new Dimension(200, 60));

		add(retour, BorderLayout.CENTER);

		retour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jeu.getFrame().setContentPane(jeu.getMenu());
				jeu.getFrame().pack();

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jeu.getFakeGameDrawer().paintComponent(g, getWidth(), getHeight());
		repaint();
	}
}
