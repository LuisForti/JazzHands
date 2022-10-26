package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class JogoTcc extends ApplicationAdapter implements Screen {
    SpriteBatch batch;
    BitmapFont fonte;
    double frame = 0;
    String posicao = "";
    Music musicaTeste;
    double[] batidas = {0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.5454,0.5454,0.41,0.272,0.272,0.136,
                        0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.5454,0.5454,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.5454,0.5454,0.41,
                        0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136, 0.5454,0.5454,0.5454,//Depois do 1 seria pausa
                        0.5454,0.2727,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,
                        0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.2727,0.272,0.272,0.5454,0.272,0.272,
                        0.5454,0.5454,0.5454,0.2727,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,
                        0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.2727,0.272,0.272,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.41,0.272,0.272,
                        0.136,0.41,0.272,0.272,0.136,0.41,0.272 ,0.272,0.136,0.5454,0.5454,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.41,0.272,0.272,
                        0.136,0.5454,0.5454,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.41,0.272,0.272,0.136,0.5454,0.5454,0.41,0.272,0.272,0.136,0.41,0.272,
                        0.272,0.136,0.41,0.272,0.272,0.136,0.5454,0.5454,0.5454,//Depois do 1 seria pausa
                        0.5454,0.2727,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,
                        0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.5454,0.272,0.272,0.5454,0.5454,0.2727,
                        0.272,0.272,0.5454,0.272, 0.272,0.5454,0.5454};
    int ritmo = 42;
    double proximaBatida = 0;
    int batidaAtual = 1;
    String[] movimentacao = {"cima", "frente"};
    int pontuacao = 0;


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    public void iniciar(String musica)
    {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.getData().setScale(8);
        musicaTeste = Gdx.audio.newMusic(Gdx.files.internal(musica));
        proximaBatida = batidas[0];
    }

    public void render(float delta, double anguloX,double anguloY, double anguloZ) {
        // Processamento da música
        if(frame == 0)
            musicaTeste.play();

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        fonte.draw(batch, posicao, 0, 1200);
        fonte.draw(batch, String.valueOf(batidaAtual), 0, 400);
        fonte.draw(batch, String.valueOf(frame), 0, 800);
        batch.end();

        frame += 1/60d;

        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame >= proximaBatida) {
            batidaAtual++;
            proximaBatida += batidas[batidaAtual];
            // Para cima
            if (anguloY > 40) {
                posicao = "cima";
            }
            //Para trás ou para frente
            else if (anguloY < 40) {
                if (anguloX < 40 && anguloX > -40) {
                    if (anguloZ > 0)
                        posicao = "frente";
                    else
                        posicao = "trás";
                } else if (anguloX > 40) {
                    posicao = "esquerda";
                } else
                    posicao = "direita";
            }
            if (posicao == movimentacao[batidaAtual % 2])
            {
                pontuacao++;
                Gdx.input.vibrate(200);
            }
        }
    }

    @java.lang.Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
