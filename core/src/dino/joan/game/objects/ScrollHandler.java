package dino.joan.game.objects;
import static dino.joan.game.helpers.AssetManager.raig;

import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.ArrayList;
import java.util.Random;
import dino.joan.game.utils.Methods;
import dino.joan.game.utils.Settings;


public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // ROCS
    int numRocs = 3;
    private ArrayList<Roc> rocs;

    // Bonus,
    int numBonus = 2; // número de bonus que hi haurà, com a màxim, en la pantalla
    private ArrayList<Peix> bonus;

    // Variable per a calcular el temps de creació entre un roc i el següent
    private float time;
    private float timeBonus;

    private boolean pause = false;

    // Objecte Random
    Random r;
    Random r2;


    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();
        r2 = new Random();

        // Creem l'ArrayList de shurikens i bonus.
        rocs = new ArrayList<Roc>();
        bonus = new ArrayList<Peix>();

        // Establim els estats inicials
        time = 0;
        timeBonus = 0;

        newRoc();
        newBonus();

    }

    public void newRoc(){
        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ROC, Settings.MAX_ROC) * 100;

        // Afegim el roc a l'Array i al grup
        Roc roc = new Roc(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ROC_SPEED);
        rocs.add(roc);
        addActor(roc);
    }

    public void removeRoc(Roc roc){
        rocs.remove(roc);
        roc.remove();
    }

    // TODO creem els bonus segons el resultat del número random
    public void newBonus(){
        int tipus;
        Peix peix = null;
        // Afegim el sushi a l'Array i al grup
        if (r2.nextInt(10) < 8){
            tipus = 1;
            peix = new Peix(tipus, Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - Settings.PEIX1_HEIGHT), Settings.PEIX1_WIDTH, Settings.PEIX1_HEIGHT, Settings.PEIX1_SPEED);
        }else {
            tipus = 2;
            peix = new Peix(tipus, Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - Settings.PEIX2_HEIGHT), Settings.PEIX2_WIDTH, Settings.PEIX2_HEIGHT, Settings.PEIX2_SPEED);
        }
        bonus.add(peix);
        addActor(peix);
    }

    // TODO per eliminar el bonus quan hi ha col·lisió amb l'actor principal
    public void removeBonus(Peix peix){
        bonus.remove(peix);
        peix.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < rocs.size(); i++) {

            Roc roc = rocs.get(i);
            if (roc.isLeftOfScreen()) {
                roc.reset();
            }
        }

        for (int i = 0; i < bonus.size(); i++){
            Peix peix = bonus.get(i);
            if (peix.isLeftOfScreen()){
                peix.reset();
            }
        }

        if(time > 1f && rocs.size() < numRocs){
            newRoc();
            time = 0;
        }else time += delta;

        if(timeBonus > 1f && bonus.size() < numBonus){
            newBonus();
            timeBonus = 0;
        }else timeBonus += delta;
    }

    public boolean collides(Dino dino) {

        // Comprovem les col·lisions entre cada roc i el dino
        for (Roc roc: rocs) {
            if (roc.collides(dino)) {
                return true;
            }
        }
        return false;
    }

    public boolean collides2(Dino dino){
        // Comprovem les col·lisions entre cada bonus i el dino
        for (Peix peix : bonus) {
            if (peix.collides(dino)) {
                return true;
            }
        }
        return false;
    }

    public Peix getBonus(Dino dino){
        for (Peix peix : bonus) {
            if (peix.collides(dino)) {
                return peix;
            }
        }
        return null;
    }

    // TODO Col·lisions entre cada roc i el raig,
    public Roc collides(Raig raig) {

        for (Roc roc : rocs) {
            if (roc.collides(raig)) {
                return roc;
            }
        }
        return null;
    }

    public void reset() {

        // Posem el primer roc fora de la pantalla per la dreta
        rocs.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'asteroids.
        for (int i = 1; i < rocs.size(); i++) {

            rocs.get(i).reset(rocs.get(i - 1).getTailX() + Settings.ROC_GAP);

        }

        // TODO Posem el primer bonus fora de la pantalla per la dreta
        bonus.get(0).reset(Settings.GAME_WIDTH);
        // TODO Calculem les noves posicions de la resta de bonus,
        for (int i = 1; i < bonus.size(); i++) {
            bonus.get(i).reset(bonus.get(i - 1).getTailX() + Settings.ROC_GAP);
        }
    }

    // TODO Activem/desactivem l'estat de pause a tots els elements, exercici 4
    public void setPause(){
        pause = true;
        bg.startPause();
        bg_back.startPause();
        for (Roc roc : rocs){
            roc.startPause();
        }
        for (Peix peix : bonus){
            peix.startPause();
        }
    }
    public void stopPause(){
        pause = false;
        bg.stopPause();
        bg_back.stopPause();
        for (Roc roc: rocs){
            roc.stopPause();
        }
        for (Peix peix : bonus){
            peix.stopPause();
        }
    }

}
