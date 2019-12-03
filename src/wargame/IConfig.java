package wargame;

public interface IConfig {
	int LARGEUR_CARTE = 25;
	int HAUTEUR_CARTE = 30; // en nombre de cases
	int NB_PIX_CASE = 20;
	int NB_HEROS = 6;
	int NB_MONSTRES = 15;
	int NB_OBSTACLES = LARGEUR_CARTE * HAUTEUR_CARTE / 4;
	int VIE_PAR_REGEN = 7;

}