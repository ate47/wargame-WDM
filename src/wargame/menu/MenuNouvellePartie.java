package wargame.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import wargame.IType.Faction;
import wargame.config.Difficulty;
import wargame.config.IConfig;
import wargame.config.MapSize;
import wargame.Wargame;

public class MenuNouvellePartie extends PanelMenu {

	private static final long serialVersionUID = 9098817428626585291L;

	private class FactionSetter implements ActionListener {
		private Faction f;

		public FactionSetter(Faction f) {
			this.f = f;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			factions[jeu.getConfig().getFactionJoueur().ordinal()].setUtilise(false);
			jeu.getConfig().setFactionJoueur(f);
			resizeButtons();
		}
	}

	private MenuButton[] factions;
	protected final Wargame jeu;

	public MenuNouvellePartie(Wargame jeu) {
		super(jeu, 4);
		this.jeu = jeu;
		IConfig old = jeu.getConfig().clone();

//		 Choisir taille carte (Grand/moyen/petit)
//		 -> augmente nb ennemis/alliés
//		 Choisir pourcentage d'obstacle

		Faction[] factions = Faction.values();
		this.factions = new MenuButton[factions.length];

		for (int i = 0; i < factions.length; i++) {
			add(this.factions[i] = new MenuButton("", factions[i].getIcon(), null));
			this.factions[i].addActionListener(new FactionSetter(factions[i]));
		}

		resizeButtons();

		JComboBox<Difficulty> comboDiff = new JComboBox<>(Difficulty.values());
		comboDiff.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				jeu.getConfig().setDifficulty((Difficulty) e.getItem());
			}
		});
		comboDiff.setSelectedItem(jeu.getConfig().getDifficulty());
		addComponend(comboDiff);

		JComboBox<MapSize> comboMapSize = new JComboBox<>(MapSize.values());
		comboMapSize.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				jeu.getConfig().setMapSize((MapSize) e.getItem());
			}
		});
		comboMapSize.setSelectedItem(jeu.getConfig().getMapSize());
		addComponend(comboMapSize);

		addButton("Lancer", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.writeConfig();
				jeu.genererCarte();
				jeu.getFrame().setContentPane(jeu.getPanneau());
				jeu.getFrame().pack();
			}
		});

		addButton("Annuler", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jeu.setConfig(old);
				jeu.getFrame().setContentPane(jeu.getMenu());
				jeu.getFrame().pack();
			}
		});

	}

	private void resizeButtons() {
		int size = getTop() / 2;
		int decalage = size / 10;
		int top = getTop() / 4;
		int left = (getWidth() - size * factions.length - decalage * (factions.length - 1)) / 2;
		for (int i = 0; i < factions.length; i++) {
			factions[i].setBounds(left + (size + decalage) * i, top, size, size);
		}
		factions[jeu.getConfig().getFactionJoueur().ordinal()].setUtilise(true);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		resizeButtons();
	}

}
