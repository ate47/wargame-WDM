package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import wargame.Wargame;

public class PanneauRestauration extends PanelMenu {

	private static final long serialVersionUID = 3092784374487373890L;

	public PanneauRestauration(Wargame jeu) {
		super(jeu, 1);
		addButton("Retour", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jeu.getFrame().setContentPane(jeu.getMenu());
				jeu.getFrame().pack();

			}
		});
		setPreferredSize(jeu.getFrame().getSize());
	}
}
