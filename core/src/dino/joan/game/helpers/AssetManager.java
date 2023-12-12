package dino.joan.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {
    // Sprite Sheet
    public static Texture sheet;

    // Dino i fons
    public static TextureRegion dino, dinoDown, dinoUp, background;
    public static TextureRegion[] dinoRun;
    public static Animation dinoRunAnim;

    // ROC
    public static TextureRegion[] roc;
    public static Animation rocAnim;

    // TODO roc puff
    public static TextureRegion[] rocPuffTexture;
    public static Animation rocPuffAnim;

    // Explosió
    public static TextureRegion[] dead;
    public static Animation deadAnim;

    // TODO raig
    public static TextureRegion raigButton;
    public static TextureRegion raig;
    public static Sound lanzamiento_rayo;

    // TODO Bonus,
    public static TextureRegion pez1;
    public static TextureRegion pez2;

    // TODO Pause, exercici 4
    public static TextureRegion pauseButton;

    // Sons
    public static Sound dinoDead;
    public static Sound rocPuff;
    public static Music music;
    public static Sound pezEat;

    // Font
    public static BitmapFont font;
    public static BitmapFont textPuntuacio;
    public static BitmapFont textPuntuacioMax;

    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Sprites del dino
        dino = new TextureRegion(sheet, 0, 0, 79, 100);
        dino.flip(false, true);

        dinoUp = new TextureRegion(sheet, 0, 0, 79, 100);
        dinoUp.flip(false, true);

        dinoDown = new TextureRegion(sheet, 0, 0, 79, 100);
        dinoDown.flip(false, true);

        // Creem els 10 estats de run
        dinoRun = new TextureRegion[10];
        // Animación run
        for (int i = 0; i < dinoRun.length; i++) {
            dinoRun[i] = new TextureRegion(sheet, i * 79, 0, 79, 100);
            dinoRun[i].flip(false, true);
        }
        // Finalment creem l'animació
        dinoRunAnim = new Animation(0.07f, dinoRun);
        dinoRunAnim.setPlayMode(Animation.PlayMode.LOOP);

        // Carreguem els 6 estats del shuriken
        roc = new TextureRegion[6];
        for (int i = 0; i < roc.length; i++) {
            roc[i] = new TextureRegion(sheet, i * 100, 200, 100, 100);
        }
        // Creem l'animació del shuriken i fem que s'executi contínuament en sentit anti-horari
        rocAnim = new Animation(0.05f, roc);
        rocAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // TODO Carreguem els  estats del roc puff
        rocPuffTexture = new TextureRegion[5];
        for (int i = 0; i < rocPuffTexture.length; i++) {
            rocPuffTexture[i] = new TextureRegion(sheet, i * 95, 300, 80, 95);
        }

        // TODO Creem l'animació del shuriken puff, exercici 2
        rocPuffAnim = new Animation(0.05f, rocPuffTexture);

        // Creem els 10 estats de la mort del ninja
        dead = new TextureRegion[10];
        for (int i = 0; i < dead.length; i++) {
            dead[i] = new TextureRegion(sheet, i * 97, 100, 97, 100);
            dead[i].flip(false, true);
        }
        // Finalment creem l'animació de la mort del ninja
        deadAnim = new Animation(0.06f, dead);

        // TODO raig i botó de disparar
        raig = new TextureRegion(sheet, 600, 200, 300, 100);
        raigButton = new TextureRegion(sheet, 0, 530, 100, 100);

        // TODO Bonus 1 i bonus 2, exercici 3
        pez1 = new TextureRegion(sheet, 400, 530, 100, 100);
        pez1.flip(false, true);
        pez2 = new TextureRegion(sheet, 200, 530, 200, 100);
        pez2.flip(false, true);

        // TODO Pause
        pauseButton = new TextureRegion(sheet, 0, 630, 100, 100);

        /************************** Fons de pantalla ********************************/
        background = new TextureRegion(sheet, 0, 380, 625, 150);

        /******************************* Sounds *************************************/
        // Mort del dino.
        dinoDead = Gdx.audio.newSound(Gdx.files.internal("sounds/ninja_dead.mp3"));

        // Llençament sai, exercici 2
        lanzamiento_rayo = Gdx.audio.newSound(Gdx.files.internal("sounds/lanzamiento_rayo.mp3"));

        // Destrucció shuriken, exercici 2
        rocPuff = Gdx.audio.newSound(Gdx.files.internal("sounds/roc_puff.mp3"));

        // So al capturar sushi, exercici 3, bonus
        pezEat = Gdx.audio.newSound(Gdx.files.internal("sounds/mordisco.mp3"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/the_spanish_dino_c64_style.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);
        textPuntuacio = new BitmapFont(fontFile, true);
        textPuntuacio.getData().setScale(0.4f);
        textPuntuacioMax = new BitmapFont(fontFile, true);
        textPuntuacioMax.getData().setScale(0.4f);

    }

    public static void dispose() {

        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
       dinoDead.dispose();
        lanzamiento_rayo.dispose();
        rocPuff.dispose();
        music.dispose();

    }
}

