package dino.joan.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
import dino.joan.game.helpers.AssetManager;
import dino.joan.game.utils.Settings;

public class Peix extends Scrollable {

    private Rectangle collisionRect;

    Random r;

    private int tipus;

    public Peix (int tipus, float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        this.tipus = tipus;
        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el rectangle de col·lisions.
        collisionRect.set(position.x, position.y, width, height);

    }

    public void reset() {

        // Posició en x, amplada de la pantalla més amplada del peix
        float newX = Settings.GAME_WIDTH + width;
        super.reset(newX);

        // La posició en y serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);
        collisionRect = new Rectangle();

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (this.tipus == 1){
            batch.draw(AssetManager.pez1, position.x, position.y, width, height);
        } else if (this.tipus == 2){
            batch.draw(AssetManager.pez2, position.x, position.y, width, height);
        }

    }

    // Col·lisions amb el dino retorna true si hi ha col·lisió
    public boolean collides(Dino dino) {

        if (position.x <= dino.getX() + dino.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan el sushi estigui a la mateixa alçada que el ninja
            return (Intersector.overlaps(collisionRect, dino.getCollisionRect()));
        }
        return false;
    }

    public int getTipus(){
        return tipus;
    }

    public void startPause() {
        velocity = 0.0f;
    }

    public void stopPause() {
        if (tipus == 1){
            velocity = Settings.PEIX1_SPEED;
        } else {
            velocity = Settings.PEIX2_SPEED;
        }
    }

}