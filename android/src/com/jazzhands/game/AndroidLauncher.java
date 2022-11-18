package com.jazzhands.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = true;  //Ativa o acelerômetro

		//Desativa o compasso e o giroscópio
		config.useCompass = false;
		config.useGyroscope = true;

		initialize(new Controlador(this), config);
	}
}
