package dino.joan.game.screens;

import static dino.joan.game.screens.GameScreen.GameState.PAUSE;
import static dino.joan.game.screens.GameScreen.GameState.RUNNING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import dino.joan.game.helpers.AssetManager;
import dino.joan.game.helpers.InputHandler;
import dino.joan.game.objects.Dino;
import dino.joan.game.objects.Peix;
import dino.joan.game.objects.Raig;
import dino.joan.game.objects.Roc;
import dino.joan.game.objects.RocPuf;
import dino.joan.game.objects.ScrollHandler;
import dino.joan.game.utils.Settings;

public class GameScreen implements Screen {
    // Els estats del joc
    // TODO estats del joc FIRE i PAUSE,
    public enum GameState {

        READY, RUNNING, GAMEOVER, FIRE, PAUSE

    }

    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private Dino dino;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;
    private float dinoRunTime = 0;
    private float rocPuffTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;
    private GlyphLayout puntuacio; // TODO Per imprimir en pantalla la puntuació,
    private GlyphLayout puntuacioMax; // TODO Per imprimir en pantalla la puntuació màxima,
    private GlyphLayout missatgeFinal; // TODO Per imprimir en pantalla el missatge final segons la puntuació,

    // TODO Array que contindrà els "rayos" que hi ha a la pantalla,
    private ArrayList<Raig> raigs;
    // TODO Array que contindrà les explosions dels rocs a efectuar,
    private ArrayList<RocPuf> rocsPuff;
    // TODO Variable per a controlar l'animació de l'explosió
    private float roccPuffTime = 0;

    // TODO Contador de punts
    private int punts = 0;

    // TODO Variables per enmagatzemar les preferències i la puntuació màxima
    public static Preferences prefs;
    public static int highScore;

    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        dino= new Dino(Settings.DINO_STARTX, Settings.DINO_STARTY, Settings.DINO_WIDTH, Settings.DINO_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(dino);
        // Donem nom a l'Actor
        dino.setName("dino");

        // TODO Definim l'Actor del botó que dispara rayos i l'afegim
        Image saiButton = new Image(AssetManager.raigButton);
        saiButton.setName("raigButton");
        saiButton.setHeight(Settings.BUTTON_HEIGHT);
        saiButton.setWidth(Settings.BUTTON_WIDTH);
        saiButton.setPosition((Settings.GAME_WIDTH - saiButton.getWidth() - 20), (Settings.GAME_HEIGHT) - saiButton.getHeight() - 10);
        stage.addActor(saiButton);

        // TODO Definim l'actor botó de pausa i l'afegim al grup
        Image pauseButton = new Image(AssetManager.pauseButton);
        pauseButton.setName("pauseButton");
        pauseButton.setHeight(Settings.BUTTON_HEIGHT);
        pauseButton.setWidth(Settings.BUTTON_WIDTH);
        pauseButton.setPosition(Settings.GAME_WIDTH - pauseButton.getWidth() - 20, 10);
        stage.addActor(pauseButton);

        // TODO Inicialitzem els arrays, exercici 2
        raigs = new ArrayList<Raig>();
        rocsPuff = new ArrayList<RocPuf>();

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "llestos?");

        // TODO Inicialitzem el label de la puntuació,
        puntuacio = new GlyphLayout();

        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        // TODO Inicialitzem el label de la puntuació màxima
        puntuacioMax = new GlyphLayout();
        missatgeFinal = new GlyphLayout();
        prefs = Gdx.app.getPreferences("HighScore");
        highScore = prefs.getInteger("HighScore", 0);
        // Text per mostrar la puntuació màxima,
        puntuacioMax.setText(AssetManager.textPuntuacioMax, "High Score: " + highScore);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
            // TODO Afegim PAUSE al render
            case PAUSE:
                updatePause(delta);
                break;

        }

    }
    private void updateReady() {

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();
        punts = 0;

    }

    private void updateRunning(float delta) {
        puntuacio.setText(AssetManager.textPuntuacio, "Punts: " + punts);
        stage.act(delta);
        batch.begin();
        AssetManager.textPuntuacio.draw(batch, puntuacio, 20,  10);
        batch.draw((TextureRegion) AssetManager.dinoRunAnim.getKeyFrame(dinoRunTime, true), dino.getX(), dino.getY(), Settings.DINO_WIDTH, Settings.DINO_HEIGHT);
        batch.end();
        dinoRunTime += delta;


        if (scrollHandler.collides(dino)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.dinoDead.play();
            stage.getRoot().findActor("dino").remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            currentState = GameState.GAMEOVER;
        }

        // TODO controlem la col·lisió amb el bonus
        if (scrollHandler.collides2(dino)){
            Peix peix= scrollHandler.getBonus(dino);
            AssetManager.pezEat.play();
            if (peix!= null){
                if (peix.getTipus() == 1){
                    punts += Settings.BONUS1_INCREASE;
                } else if (peix.getTipus() == 2){
                    punts += Settings.BONUS2_INCREASE;
                }
                scrollHandler.removeBonus(peix);
                peix.remove();
            }
        }

        // TODO Control dels sais disparats,
        if(raigs.size()>0) {
            for (Raig raig : raigs) {
                Roc roc = scrollHandler.collides(raig);
                if (roc != null) {
                    // Si hi ha hagut col·lisió entre roc i raig: reproducció del so i de l'animació. Eliminem el raig i el roc
                    AssetManager.rocPuff.play();
                    rocsPuff.add(new RocPuf(AssetManager.rocPuffAnim, (roc.getX() + roc.getWidth() / 2) - 32, roc.getY() + roc.getHeight() / 2 - 32, 64f, 64f, delta));
                    raigs.remove(raig);
                    raig.remove();
                    scrollHandler.removeRoc(roc);
                    break;
                }
            }
        }

        // TODO Control de la desaparició del roc (puf),
        if(rocsPuff.size()>0){
            for(RocPuf puf : rocsPuff){
                if(!puf.isFinished()) {
                    batch.begin();
                    // Si l'animació no ha finalitzat
                    batch.draw((TextureRegion) puf.getAnim().getKeyFrame(puf.getDelta(), true), puf.getX(), puf.getY(), puf.getWidth(), puf.getHeight());
                    batch.end();
                    puf.setDelta(puf.getDelta()+delta);
                    break;
                }else{
                    //Si l'animació ha finalitzat
                    rocsPuff.remove(puf);
                    break;
                }
            }
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);

        // TODO puntuació màxima i persistència,
        if (punts > highScore){
            prefs.putInteger("HighScore", punts);
            prefs.flush();
            highScore = prefs.getInteger("HighScore", 0);
            puntuacioMax.setText(AssetManager.textPuntuacioMax, "High Score: " + highScore);
        }
        if (punts <= 1000){
            missatgeFinal.setText(AssetManager.font, "MOLT BÉ!!!!!");
        } else if (punts <= 1500){
            missatgeFinal.setText(AssetManager.font,"Ets una mica novato!!");
        } else {
            missatgeFinal.setText(AssetManager.font,"Ets un super PLAYER!!!!!");
        }
        batch.begin();
        AssetManager.textPuntuacioMax.draw(batch, puntuacioMax, (Settings.GAME_WIDTH - puntuacioMax.width) / 2,  puntuacioMax.height + 15);
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 3);
        AssetManager.textPuntuacio.draw(batch, puntuacio, (Settings.GAME_WIDTH - puntuacio.width) / 2,  (Settings.GAME_HEIGHT - puntuacio.height) / 2);
        AssetManager.font.draw(batch, missatgeFinal, (Settings.GAME_WIDTH - missatgeFinal.width) / 2,  (Settings.GAME_HEIGHT - missatgeFinal.height) -40);
        // Si hi ha hagut col·lisió entre shuriken i ninja: Reproduïm la mort del ninja i posem l'estat a GameOver
        batch.draw((TextureRegion) AssetManager.deadAnim.getKeyFrame(explosionTime, false), (dino.getX() + dino.getWidth() / 2) - 12, dino.getY() + dino.getHeight() / 2 - 13, 24, 25);
        batch.end();
        explosionTime += delta;

    }

    private void updatePause(float delta){

        stage.act(delta);
        scrollHandler.setPause(); // TODO Per pausar els nous rocs si es creen després de pausar,
    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Preparats?");

        // Cridem als restart dels elements.
        dino.reset();
        scrollHandler.reset();

        // Eliminem tots els objectes sai de la pantalla
        for(Raig r : raigs) r.remove();
        raigs.clear();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim el dino a l'stage
        stage.addActor(dino);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        AssetManager.music.pause();
        if (getCurrentState() == RUNNING) {
            setCurrentState(PAUSE);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Dino getDino() {
        return dino;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        switch (currentState) {
            // TODO Afegim el pas a l'estat de PAUSE,
            case PAUSE:
                stage.getRoot().findActor("pauseButton").setSize(100,100);
                stage.getRoot().findActor("pauseButton").setPosition((Settings.GAME_WIDTH / 2) - 50,(Settings.GAME_HEIGHT / 2) - 50);

                // Reduïm el volum de la música i ocultem el botó de disparar,
                AssetManager.music.pause();
                stage.getRoot().findActor("saiButton").setVisible(false);

                // Fem que tots els elements entrin en l'estat de pausa
                dino.startPause();
                scrollHandler.setPause();
                for(Raig r: raigs) r.startPause();
                break;
            case READY:
                break;
            case RUNNING:
                if (this.currentState == PAUSE){
                    // TODO Tornem la música al seu volum original al tornar de la pausa,
                    stage.getRoot().findActor("pauseButton").setSize(Settings.BUTTON_WIDTH,Settings.BUTTON_HEIGHT);
                    stage.getRoot().findActor("pauseButton").setPosition(Settings.GAME_WIDTH - Settings.BUTTON_WIDTH - 20, 10);
                    AssetManager.music.play();
                    stage.getRoot().findActor("raigButton").setVisible(true);
                    // TODO Fem que tots els elements surtin de l'estat pause,
                    scrollHandler.stopPause();
                    for(Raig r: raigs) r.stopPause();
                }
                break;
            // TODO Afegim el pas a l'estat de FIRE
            case FIRE:
                // Si s'ha pitjat el botó, creem un objecte raig
                Raig raig = new Raig(dino.getX()+dino.getWidth(), dino.getY()+dino.getHeight()/2, 10, 4, Settings.DINO_VELOCITY*2);
                stage.getRoot().addActor(raig);
                raigs.add(raig);
                AssetManager.lanzamiento_rayo.play();
                // Tornem a deixar l'estat de l'aplicació en RUNNING
                currentState = RUNNING;
                break;
        }

        this.currentState = currentState;
    }
}

