package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class JogoTcc extends ApplicationAdapter implements Screen {
    Music musica;

    int frame = 0;
    int ritmo = 0;
    double proximaBatida = 0;
    int batidaAtual = -1;
    String[] movimentacao = {"cima", "cima", "cima"};
    int batidasFalhas = 0;

    int pontuacao = 0;
    int multiplicador = 1;
    String estado = "jogando";


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    public void iniciar(String[] enderecoMusica, Context context)
    {
        musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\MovimentosSao.mp3"));
        musica.play();
        while (musica.isPlaying())
            ;

        for(int i = 0; i < 3; i++) {
            switch ((int) (Math.random() * 5)) {
                case 0:
                    movimentacao[i] = "cima";
                    musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Cima.mp3"));
                    break;
                case 1:
                    movimentacao[i] = "direita";
                    musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Dir.mp3"));
                    break;
                case 2:
                    movimentacao[i] = "frente";
                    musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Frent.mp3"));
                    break;
                case 3:
                    movimentacao[i] = "esquerda";
                    musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Esq.mp3"));
                    break;
                case 4:
                    movimentacao[i] = "tras";
                    musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Tras.mp3"));
                    break;
                default:
                    i--;
                    break;
            }

            switch (i) {
                case 1:
                    if (movimentacao[i] == movimentacao[0] ||
                            (movimentacao[i] == "direita" && movimentacao[0] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[0] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[0] == "tras") || (movimentacao[i] == "tras" && movimentacao[0] == "frente")) {
                        i--;
                        musica = null;
                    }
                    break;
                case 2:
                    if (movimentacao[i] == movimentacao[0] || movimentacao[i] == movimentacao[1] ||
                            (movimentacao[i] == "direita" && movimentacao[0] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[0] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[0] == "tras") || (movimentacao[i] == "tras" && movimentacao[0] == "frente") ||
                            (movimentacao[i] == "direita" && movimentacao[1] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[1] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[1] == "tras") || (movimentacao[i] == "tras" && movimentacao[1] == "frente")) {
                        i--;
                        musica = null;
                    }
                    break;
                default:
                    break;
            }
            if (musica != null) {
                musica.play();
                while (musica.isPlaying())
                    ;
            }
        }

        musica = Gdx.audio.newMusic(Gdx.files.internal(enderecoMusica[0]));
        musica.setVolume(0.15F);

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
            estado = "ganhou";

        frame += 1;

        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame >= proximaBatida) {
            batidaAtual++;
            proximaBatida += ritmo;
            if(batidaAtual > 10)
            {
                if (posicao == movimentacao[batidaAtual % 3])
                {
                    pontuacao += multiplicador;

                    if(batidasFalhas > 0)
                        batidasFalhas = 0;

                    batidasFalhas--;
                    if(batidasFalhas % 10 == 0)
                        multiplicador++;

                    Gdx.input.vibrate(200);
                }
                else {
                    if(batidasFalhas < 0)
                        batidasFalhas = 0;

                    batidasFalhas++;
                    multiplicador = 1;
                }
            }
            else if (posicao == movimentacao[batidaAtual % 3])
                Gdx.input.vibrate(200);

            if(batidasFalhas >= 5) {
                estado = "perdeu";
                musica.stop();
                musica.dispose();
            }
        }
    }

    public String pegarEstado()
    {
        return estado;
    }

    public int pegarPontuacao() { return pontuacao; }

    @java.lang.Override
    public void hide() {
        musica.stop();
        musica.dispose();
    }
}
