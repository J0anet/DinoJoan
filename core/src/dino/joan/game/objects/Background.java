package dino.joan.game.objects;
import dino.joan.game.utils.Settings;

import com.badlogic.gdx.graphics.g2d.Batch;

import dino.joan.game.helpers.AssetManager;

public class Background extends Scrollable{
    public Background(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.disableBlending();
        batch.draw(AssetManager.background, position.x, position.y, width, height);
        batch.enableBlending();
    }

    // Activem/desactivem l'estat de pause, exercici 4
    public void startPause() {
        velocity = 0.0f;
    }
    public void stopPause() {
        velocity = Settings.BG_SPEED;
    }
}
