package dino.joan.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import dino.joan.game.DinoJoan;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Deshabilitem els sensors que no anem a fer servir
		config.useAccelerometer = false;
		config.useCompass = false;
		// Impedim que s'apagui la pantalla
		config.useWakelock = true;
		// Posem el mode immersive per ocultar botons software
		config.useImmersiveMode = true;

		// Apliquem la conf.
		initialize(new DinoJoan(), config);
	}
}
