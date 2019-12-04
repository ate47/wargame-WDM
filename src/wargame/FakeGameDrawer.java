package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import wargame.IType.Faction;
import wargame.utils.WargameUtils;

/**
 * Dessinateur de faux jeu
 */
public class FakeGameDrawer {
	private static final Color SURCOUCHE = new Color(0x44000000, true);
	private double shiftX, shiftY;
	private double translateX, translateY;
	private Wargame game;

	public FakeGameDrawer(Wargame game) {
		this.game = game;
		double angle = Math.random() * Math.PI * 2;
		double speed = Math.random() + 0.2;
		shiftX = Math.cos(angle) * speed;
		shiftY = Math.sin(angle) * speed;
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
		int translateXStart = -(int) this.translateX - 2;
		int translateXEnd = (int) (translateXStart + width * 1F / unit) + 4;
		int translateYStart = -(int) (this.translateY / 0.6666F) - 2;
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

}
