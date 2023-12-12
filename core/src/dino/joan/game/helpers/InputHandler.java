package dino.joan.game.helpers;

import static com.badlogic.gdx.Input.Keys.PAUSE;
import static jdk.jfr.RecordingState.RUNNING;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import dino.joan.game.objects.Dino;
import dino.joan.game.screens.GameScreen;

public class InputHandler implements InputProcessor {

    // Enter per a la gesitó del moviment d'arrastrar
    int previousY = 0;
    // Objectes necessaris
    private Dino dino;
    private GameScreen screen;
    private Vector2 stageCoord;

    private Stage stage;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        dino = screen.getDino();
        stage = screen.getStage();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (screen.getCurrentState()) {
            case READY:
            case PAUSE:

                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case RUNNING:
                previousY = screenY;

                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
                if (actorHit != null)
                    // Controlem a qui s'ha fet clic i canviem l'estat
                    switch (actorHit.getName()) {
                        case "spacecraft":
                            break;
                        case "raigButton": // TODO Exercici 2
                            screen.setCurrentState(GameScreen.GameState.FIRE);
                            break;
                        case "pauseButton": // TODO Exercici 4
                            screen.setCurrentState(GameScreen.GameState.PAUSE);

                    }
                break;
            // Si l'estat és GameOver tornem a iniciar el joc
            case GAMEOVER:
                screen.reset();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Quan deixem anar el dit acabem un moviment
        // i posem la nau en l'estat normal
        dino.goStraight();
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                dino.goDown();
            } else {
                // En cas contrari cap a dalt
                dino.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
