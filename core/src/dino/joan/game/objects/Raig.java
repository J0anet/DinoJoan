package dino.joan.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import dino.joan.game.helpers.AssetManager;


public class Raig extends Scrollable{

    private Rectangle collisionRect;

    // Variable per a establir l'estat pause i l'animació a l'objecte
    protected boolean pause;
    private Action pauseAction;

    public Raig(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Definim l'estat inicial de la pausa,
        pause = false;

    }

    // Activem/desactivem l'estat de pause, exercici 4
    public void startPause() {
        pause = true;
        pauseAction = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.alpha(0.5f, 0.2f), Actions.alpha(1.0f, 0.2f)));
        this.addAction(pauseAction);
    }

    public void stopPause() {
        pause = false;
        this.removeAction(pauseAction);
    }

    // Si el joc està pausat, no actualitzem l'estat
    @Override
    public void act(float delta) {
        if(!pause){
            super.act(delta);
            collisionRect.set(position.x, position.y, width, 2);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.raig, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
