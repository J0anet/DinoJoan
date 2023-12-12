package dino.joan.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.Random;

import dino.joan.game.helpers.AssetManager;
import dino.joan.game.utils.Methods;
import dino.joan.game.utils.Settings;

public class Roc extends Scrollable{
    private Circle collisionCircle;

    Random r;

    int assetRoc;

    RotateByAction rotateAction = new RotateByAction();

    public Roc(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetRoc = r.nextInt(6);

        setOrigin();

        // Rotacio
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.4f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        this.addAction(repeat);

    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions (punt central del roc i el radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);


    }

    public void reset() {
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ROC, Settings.MAX_ROC);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 100 * newSize;
        float newX = Settings.GAME_WIDTH + width;
        super.reset(newX);
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetRoc = r.nextInt(6);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.roc[assetRoc], position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    // Col·lisins amb el dino, retorna true si hi ha col·lisió
    public boolean collides(Dino dino) {

        if (position.x <= dino.getX() + dino.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan el roc estigui a la mateixa alçada que el dino
            return (Intersector.overlaps(collisionCircle, dino.getCollisionRect()));
        }
        return false;
    }

    // TODO Col·lisions amb el raig, retorna true si hi ha col·lisió
    public boolean collides(Raig raig) {

        if (position.x <= raig.getX() + raig.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que el raig
            return (Intersector.overlaps(collisionCircle,raig.getCollisionRect()));
        }
        return false;
    }

    // TODO Activem/desactivem l'estat de pause
    public void startPause() {
        velocity = 0.0f;
        rotateAction.setAmount(0.0f);
    }
    public void stopPause() {
        velocity = Settings.ROC_SPEED;
        rotateAction.setAmount(-90.0f);
    }

}
