package dino.joan.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import dino.joan.game.helpers.AssetManager;
import dino.joan.game.utils.Settings;

public class Dino extends Actor {
    // Distintes posicions del ninja, recta, pujant i baixant
    public static final int DINO_STRAIGHT = 0;
    public static final int DINO_UP = 1;
    public static final int DINO_DOWN = 2;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;

    // Variables per a establir l'estat de pausa i l'animació a l'objecte dino
    private Action pauseAction;
    private boolean pause;

    public Dino(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la spacecraft a l'estat normal
        direction = DINO_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestió de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);


    }

    public void act(float delta) {
        super.act(delta);

        // Movem el Dino depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case DINO_UP:
                if (this.position.y - Settings.DINO_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.DINO_VELOCITY * delta;
                }
                break;
            case DINO_DOWN:
                if (this.position.y + height + Settings.DINO_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.DINO_VELOCITY * delta;
                }
                break;
            case DINO_STRAIGHT:
                break;
        }

        collisionRect.set(position.x, position.y, width, height);
        setBounds(position.x, position.y, width, height);


    }

    public void startPause() {
        pause = true;
        pauseAction = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pauseAction);
    }
    public void stopPause() {
        pause = false;
        this.removeAction(pauseAction);
    }


    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = DINO_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = DINO_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = DINO_STRAIGHT;
    }

    // Obtenim el TextureRegion depenent de la posició de la spacecraft
    public TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case DINO_STRAIGHT:
                return AssetManager.dino;
            case DINO_UP:
                return AssetManager.dinoUp;
            case DINO_DOWN:
                return AssetManager.dinoDown;
            default:
                return AssetManager.dino;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.DINO_STARTX;
        position.y = Settings.DINO_STARTY;
        direction = DINO_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
