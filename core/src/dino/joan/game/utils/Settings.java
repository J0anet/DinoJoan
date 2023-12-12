package dino.joan.game.utils;

public class Settings {
    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 280;
    public static final int GAME_HEIGHT = 135;

    // Propietats del ninja
    public static final float DINO_VELOCITY = 75;
    public static final int DINO_WIDTH = 20;
    public static final int DINO_HEIGHT = 25;
    public static final float DINO_STARTX = 20;
    public static final float DINO_STARTY = GAME_HEIGHT/2 - DINO_HEIGHT/2;

    // Valors d'amplada y alçada del botó
    public static final int BUTTON_WIDTH = 25;
    public static final int BUTTON_HEIGHT = 25;

    // TODO Valors d'amplada, alçada i velocitat dels bonus (pez1 i pez2)
    public static final int PEIX1_WIDTH = 15;
    public static final int PEIX1_HEIGHT = 15;
    public static final int PEIX2_WIDTH = 30;
    public static final int PEIX2_HEIGHT = 15;
    public static final int PEIX1_SPEED = -100;
    public static final int PEIX2_SPEED = -175;

    // Rang de valors per canviar la mida del shuriken
    public static final float MAX_ROC = 0.6f;
    public static final float MIN_ROC = 0.2f;

    // Configuració Scrollable
    public static final int ROC_SPEED = -150;
    public static final int ROC_GAP = 75;
    public static final int BG_SPEED = -100;

    // TODO Propietats per al bonus
    public static final int BONUS1_INCREASE = 100; // s'incrementa en 100 cada cop que toca un peix 1
    public static final int BONUS2_INCREASE = 150; // s'incrementa en 150 cada cop que toca un peix 2

}

