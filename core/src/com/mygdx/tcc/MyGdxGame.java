package com.mygdx.tcc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
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

		//Pega o valor do acelerômetro, em m/s^2, e converte para graus
		giroX = Gdx.input.getAccelerometerX() * 9.18;
		giroY = Gdx.input.getAccelerometerY() * -9.18;
		giroZ = Gdx.input.getAccelerometerZ() * 9.18;

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

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		fonte.draw(batch, Byte.toString(musicaAtual), 0, 1200);
		fonte.draw(batch, Double.toString(Math.round(giroX * 100) / 100), 0, 1000);
		fonte.draw(batch, Double.toString(Math.round(giroY * 100) / 100), 0, 650);
		fonte.draw(batch, Double.toString(Math.round(giroZ * 100) / 100), 0, 300);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
