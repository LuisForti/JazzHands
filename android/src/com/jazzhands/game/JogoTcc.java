package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.jazzhands.game.BD.Create;
import com.jazzhands.game.BD.Pontuacao;
import com.jazzhands.game.BD.Read;

public class JogoTcc extends ApplicationAdapter implements Screen {
    Music musica;
    int idMusica = 1;

    int frame = 0;
    int ritmo = 0;
    double proximaBatida = 0;
    int batidaAtual = 0;
    String[] movimentacao = {"cima", "frente"};
    int batidasFalhas = 0;

    int pontuacao = 0;
    int maiorPontuacao;
    String estado = "jogando";

    Context contexto;


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    public void iniciar(String[] enderecoMusica, Context context)
    {
        musica = Gdx.audio.newMusic(Gdx.files.internal(enderecoMusica[0]));
        idMusica = Integer.parseInt(enderecoMusica[2]);

        ritmo = Integer.parseInt(enderecoMusica[1]);
        proximaBatida = ritmo;
        contexto = context;
    }

    public void render(String posicao) {
        // Processamento da música
        if(frame == 0) {
            musica.play();
            estado = "jogando";
        }

        if(!musica.isPlaying())
            estado = "ganhou";

        frame += 1;

        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame >= proximaBatida) {
            batidaAtual++;
            proximaBatida += ritmo;
            if (posicao == movimentacao[batidaAtual % 2])
            {
                if(batidaAtual > 10) {
                    pontuacao++;
                    batidasFalhas = 0;
                }
                Gdx.input.vibrate(200);
            }
            else
                batidasFalhas++;
            if(batidasFalhas >= 10) {
                estado = "perdeu";
                musica.stop();
                pegarPontuacaoMaxima();
            }
        }
    }

    private void pegarPontuacaoMaxima()
    {
        Create c = new Create(contexto);
        c.createTable();

        Read r = new Read(contexto);
        Pontuacao p1 = r.getPontuacao(idMusica);
        maiorPontuacao = p1.getPontos();
        System.out.println(maiorPontuacao);
    }

    public String pegarEstado()
    {
        return estado;
    }

    @java.lang.Override
    public void hide() {}

    @Override
    public void dispose() {
        musica.stop();
    }
}
