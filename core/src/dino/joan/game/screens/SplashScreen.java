package dino.joan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import dino.joan.game.DinoJoan;
import dino.joan.game.helpers.AssetManager;
import dino.joan.game.utils.Settings;

public class SplashScreen implements Screen {
    private Stage stage;
    private DinoJoan game;
    private Batch batch;

    private Label.LabelStyle textStyle;
    private Label textLbl;

    private float dinoRunTime = 0;
    private int posX = 0 - Settings.DINO_WIDTH;

    // TODO Persistència puntuació màxima
    private GlyphLayout puntuacioMax; // Per imprimir en pantalla la puntuació màxima,
    public static Preferences prefs;
    public static int highScore;

    public SplashScreen(DinoJoan game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);
        batch = stage.getBatch();

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        // TODO Inicialitzem el label de la puntuació màxima i les prefs per a la SplashScreen
        puntuacioMax = new GlyphLayout();
        prefs = Gdx.app.getPreferences("HighScore");
        highScore = prefs.getInteger("HighScore", 0);
        // TODO Text per mostrar la puntuació màxima
        puntuacioMax.setText(AssetManager.textPuntuacioMax, "High Score: " + highScore);

        // Creem l'estil de l'etiqueta i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Ninja GusF", textStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 3);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
        stage.addActor(container);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);
        batch.begin();
        AssetManager.textPuntuacioMax.draw(batch, puntuacioMax, (Settings.GAME_WIDTH - puntuacioMax.width) / 2,  puntuacioMax.height + 15);
        batch.draw((TextureRegion) AssetManager.dinoRunAnim.getKeyFrame(dinoRunTime, true), posX, Settings.GAME_HEIGHT / 2, Settings.DINO_WIDTH, Settings.DINO_HEIGHT);
        batch.end();
        if (posX > Settings.GAME_WIDTH){
            posX = 0 - Settings.DINO_WIDTH;
        } else {
            posX += 1;
        }
        dinoRunTime += delta;
        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

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
}