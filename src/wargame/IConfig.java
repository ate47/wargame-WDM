package wargame;

import java.awt.Color;

public interface IConfig {
	int LARGEUR_CARTE = 25;
	int HAUTEUR_CARTE = 30; // en nombre de cases
	int NB_PIX_CASE = 20;
	int NB_HEROS = 6;
	int NB_MONSTRES = 15;
	int NB_OBSTACLES = LARGEUR_CARTE * HAUTEUR_CARTE / 4;
	int VIE_PAR_REGEN = 7;
	Color COULEUR_VIDE = Color.white, COULEUR_INCONNU = Color.lightGray;
	Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black;
	Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink;
	Color COULEUR_MENU = Color.gray;
}