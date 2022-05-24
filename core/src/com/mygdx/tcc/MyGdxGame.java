package com.mygdx.tcc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont fonte;
	int frame = 0;
	double giroX, giroY, giroZ;
	byte musicaAtual = 0;
	byte estado = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fonte = new BitmapFont();
		fonte.getData().setScale(8);
		giroX = giroY = giroZ = 0.00;
	}

	@Override
	public void render () {
		frame += 1;

		if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
			giroX = Gdx.input.getAccelerometerX() * 10;
			giroY = Gdx.input.getAccelerometerY() * (-10);
			giroZ = Gdx.input.getAccelerometerZ() * 10;
		}

		if(giroY < 30 && giroY > 0 && estado == 0) {
			if(giroZ > 0) {
				estado = 1;
				musicaAtual++;
			}
			else {
				estado = 1;
				musicaAtual--;
			}
		}

		switch (estado)
		{
			case 1:
			{
				if(giroY > 80 && giroY < 100)
					estado = 0;

				break;
			}
			default: break;
		}

		//if (frame % 60 == 0) {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		fonte.draw(batch, Byte.toString(musicaAtual), 0, 1200);
		fonte.draw(batch, Double.toString(Math.round(giroX * 100) / 100), 0, 1000);
		fonte.draw(batch, Double.toString(Math.round(giroY * 100) / 100), 0, 650);
		fonte.draw(batch, Double.toString(Math.round(giroZ * 100) / 100), 0, 300);
		batch.end();
		//}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
