package com.mygdx.tcc;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.tcc.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = true;  //Ativa o acelerômetro e o giroscópio
		config.useGyroscope = true;

		//Desativa o compasso
		config.useCompass = false;

		initialize(new MyGdxGame(), config);
	}
}
