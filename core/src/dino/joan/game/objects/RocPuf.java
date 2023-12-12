package dino.joan.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class RocPuf {
    private float delta;
    private Animation anim;
    private float x;
    private float y;
    private float width;
    private float height;

    public RocPuf(Animation anim, float x, float y, float width, float height, float delta){
        this.anim = anim;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.delta = delta;
    }

    //Control de la finalització de l'animació
    public boolean isFinished(){
        return anim.isAnimationFinished(delta);
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public Animation getAnim() {
        return anim;
    }

    public void setAnim(Animation anim) {
        this.anim = anim;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

