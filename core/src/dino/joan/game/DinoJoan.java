package dino.joan.game;
/**
 * @autor Joan Enajas Cortes.
 * Joc. EAC4. M08
 */


import com.badlogic.gdx.Game;
import dino.joan.game.helpers.AssetManager;
import dino.joan.game.screens.SplashScreen;


public class DinoJoan extends Game {

	@Override
	public void create () {
		// A l'iniciar el joc carreguem els recursos
		AssetManager.load();
		// I definim la pantalla d'splash com a pantalla
		setScreen(new SplashScreen(this));

	}

	// Cridem per descartar els recursos carregats.
	@Override
	public void dispose() {
		super.dispose();
		AssetManager.dispose();
	}
}
