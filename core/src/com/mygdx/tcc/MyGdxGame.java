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
	double anguloX, anguloY, anguloZ;
	double giroX, giroY, giroZ;
	double angulo;
	byte musicaAtual = 0;
	byte estado = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fonte = new BitmapFont();
		fonte.getData().setScale(8);
		anguloX = anguloY = anguloZ = 0.00;
		giroX = giroY = giroZ = 0.00;
		angulo = 0;
	}

	@Override
	public void render () {
		frame += 1;

		//Pega o valor do acelerômetro, em m/s^2, e converte para graus
		anguloX = Gdx.input.getAccelerometerX() * 9.18;
		anguloY = Gdx.input.getAccelerometerY() * 9.18;
		anguloZ = Gdx.input.getAccelerometerZ() * 9.18;

		giroX = Gdx.input.getGyroscopeX();
		giroY = Gdx.input.getGyroscopeY();
		giroZ = Gdx.input.getGyroscopeZ();

		angulo = 0.98 * (angulo + giroY * 1/10) + 0.02 * anguloY;

		if(angulo < 60 && angulo > 0 && estado == 0) {
			if(anguloZ > 0) {
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
				if(angulo > 60)
					estado = 0;

				break;
			}
			default: break;
		}

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		fonte.draw(batch, Byte.toString(musicaAtual), 0, 1200);
		fonte.draw(batch, Double.toString(Math.round(angulo * 100) / 100), 0, 1400);
		fonte.draw(batch, Double.toString(Math.round(anguloX * 100) / 100), 0, 1000);
		fonte.draw(batch, Double.toString(Math.round(anguloY * 100.000) / 100.000), 0, 650);
		fonte.draw(batch, Double.toString(Math.round(anguloZ * 100) / 100), 0, 300);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
