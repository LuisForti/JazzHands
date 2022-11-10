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
    Music musica;
    int ritmo = 0;
    double proximaBatida = 0;
    int batidaAtual = 1;
    String[] movimentacao = {"cima", "frente"};
    int pontuacao = 0;

    String estado = "jogando";


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    public void iniciar(String[] enderecoMusica)
    {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.getData().setScale(8);
        musica = Gdx.audio.newMusic(Gdx.files.internal(enderecoMusica[0]));
        ritmo = Integer.parseInt(enderecoMusica[1]);
        proximaBatida = ritmo;
    }

    public void render(String posicao) {
        // Processamento da música
        if(frame == 0) {
            musica.play();
            estado = "jogando";
        }

        if(!musica.isPlaying())
            estado = "acabou";

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        fonte.draw(batch, posicao, 0, 1200);
        fonte.draw(batch, String.valueOf(batidaAtual), 0, 400);
        fonte.draw(batch, String.valueOf(frame), 0, 800);
        batch.end();

        frame += 1;

        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame >= proximaBatida) {
            batidaAtual++;
            proximaBatida += ritmo;
            if (posicao == movimentacao[batidaAtual % 2])
            {
                pontuacao++;
                Gdx.input.vibrate(200);
            }
        }
    }

    public String pegarEstado()
    {
        return estado;
    }

    @java.lang.Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
