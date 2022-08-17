package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class JogoTcc extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont fonte;
    int frame = 0;
    double anguloX, anguloY, anguloZ;
    String posicao = "";
    Music musicaTeste;
    int[] batidas = {150,180,300};
    int batidaAtual = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.getData().setScale(8);
        anguloX = anguloY = anguloZ = 0.00;
        musicaTeste = Gdx.audio.newMusic(Gdx.files.internal("Drum-Bass-Cartoon-On-On-ft-Daniel-Levi_.mp3")); // Cartoon - On & On (Lyrics) feat. Daniel Levi
    }

    @Override
    public void render() {
        // Ao começar o programa, a música inicia
        if(frame == 0)
            musicaTeste.play();
        frame += 1;
        //Pega o valor do acelerômetro, em m/s^2, e converte para graus
        anguloX = Gdx.input.getAccelerometerX() * 9.18;
        anguloY = Gdx.input.getAccelerometerY() * 9.18;
        anguloZ = Gdx.input.getAccelerometerZ() * 9.18;
        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame == batidas[batidaAtual]) {
            batidaAtual++;
            // Para cima
            if (anguloY > 40) {
                posicao = "cima";
            }
            //Para trás ou para frente
            else if (anguloY < 40) {
                if(anguloX < 40 && anguloX > -40) {
                    if (anguloZ > 0)
                        posicao = "frente";
                    else
                        posicao = "trás";
                }
                else if(anguloX > 40)
                {
                    posicao = "esquerda";
                }
                else
                    posicao = "direita";
            }
        }
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        fonte.draw(batch, posicao, 0, 1200);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
