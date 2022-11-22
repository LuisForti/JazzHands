package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class JogoTcc extends ApplicationAdapter implements Screen {
    Music musica;

    //Variáveis para controle do tempo
    int frame = 0;
    int ritmo = 0;

    //Variáveis para controle do movimento
    double proximaBatida = 0;
    int batidaAtual = -1;
    String[] movimentacao = {"", "", ""};

    //Variáveis para controle da pontuação
    int batidasFalhas = 0;
    int pontuacao = 0;
    int multiplicador = 1;

    //Variável para controle do estado do jogo
    String estado = "jogando";


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    //Para iniciar o jogo
    public void iniciar(String[] enderecoMusica)
    {
        //Áudio que anuncia que será dita a ordem dos movimentos
        musica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\MovimentosSao.mp3"));
        musica.play();
        while (musica.isPlaying())
            ;
        //Libera o espaço alocado ao áudio
        musica.dispose();

        //Loop para pegar os três movimentos desta tentativa
        for(int i = 0; i < 3; i++) {
            //Aleatoriza um número de 0 a 4
            switch ((int) (Math.random() * 5)) {
                //Define qual será o movimento e o áudio à ser tocado baseado no número
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

            //Verifica baseado em quantos movimentos já foram escolhidos
            switch (i) {
                //Caso seja o segundo movimento
                case 1:
                    //Verifica se não foi escolhido anteriormente e se não é oposto ao movimento anterior
                    //Essa validação é necessária pois o acelerômetro não funciona adequadamente em movimentos muito bruscos,
                    //o que ocorreria em movimentos muito amplos
                    if (movimentacao[i] == movimentacao[0] ||
                            (movimentacao[i] == "direita" && movimentacao[0] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[0] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[0] == "tras") || (movimentacao[i] == "tras" && movimentacao[0] == "frente")) {
                        //Define que deve escolher outro movimento
                        i--;
                        musica = null;
                    }
                    break;
                //Caso seja o terceiro movimento
                case 2:
                    //Verifica se não foi escolhido anteriormente e se não é oposto à um dos movimentos anteriores
                    //Essa validação é necessária pois o acelerômetro não funciona adequadamente em movimentos muito bruscos,
                    //o que ocorreria em movimentos muito amplos
                    if (movimentacao[i] == movimentacao[0] || movimentacao[i] == movimentacao[1] ||
                            (movimentacao[i] == "direita" && movimentacao[0] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[0] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[0] == "tras") || (movimentacao[i] == "tras" && movimentacao[0] == "frente") ||
                            (movimentacao[i] == "direita" && movimentacao[1] == "esquerda") || (movimentacao[i] == "esquerda" && movimentacao[1] == "direita")
                            || (movimentacao[i] == "frente" && movimentacao[1] == "tras") || (movimentacao[i] == "tras" && movimentacao[1] == "frente")) {
                        //Define que deve escolher outro movimento
                        i--;
                        musica = null;
                    }
                    break;
                default:
                    break;
            }
            //Se algum movimento foi definido
            if (musica != null) {
                //Avisa o usuário qual é
                musica.play();
                //Espera o áudio encerrar
                while (musica.isPlaying())
                    ;
                //Libera o espaço alocado ao áudio
                musica.dispose();
            }
        }

        //Pega o endereço da música
        musica = Gdx.audio.newMusic(Gdx.files.internal(enderecoMusica[0]));
        //Abaixa o volume, para ficar condizente com o volume dos outros áudios
        musica.setVolume(0.15F);

        //Pega o BPM da música
        ritmo = Integer.parseInt(enderecoMusica[1]);
        //Define quando vai ser a primeira batida
        proximaBatida = ritmo;
    }

    public void render(String posicao) {
        // Processamento da música
        if(frame == 0) {
            musica.play();
            estado = "jogando";
        }

        //Se a música acabar, define o estado como ganhou
        if(!musica.isPlaying())
            estado = "ganhou";

        frame += 1;

        //A cada batida
        if (frame >= proximaBatida) {
            //Avança uma posição no vetor da movimentação
            batidaAtual++;
            //Define quando vai ser a próxima batida
            proximaBatida += ritmo;
            //Se não é uma das primeiras 10 batidas (as quais são dedicadas à introdução à música)
            if(batidaAtual > 10)
            {
                if (posicao == movimentacao[batidaAtual % 3])
                {
                    //Caso esteja, aumenta a pontuação de acordo com o multiplicador
                    pontuacao += multiplicador;

                    //Se o jogador errou alguma batida recentemente, zera batidasFalhas
                    if(batidasFalhas > 0) {
                        batidasFalhas = 0;
                    }

                    //Diminui batidasFalhas em 1
                    //Para cada valor negativo desta variável significa uma batida que ele acertou consecutivamente
                    batidasFalhas--;
                    //Se batidasFalhas for divísivel por 10 (10 batidas consecutivas certas)
                    if(batidasFalhas % 10 == 0)
                        //Aumenta o multiplicador em 1, recompensando o jogador por acertar consecutivamente
                        multiplicador++;
                }
                //Caso tenha errado
                else {
                    //Se o jogador acertou alguma nota recentemente, zera batidasFalhas
                    if(batidasFalhas < 0) {
                        batidasFalhas = 0;
                    }

                    //Aumenta batidasFalhas em 1
                    batidasFalhas++;
                    //Reinicia o multiplicador
                    multiplicador = 1;
                }
            }
            //Verifica se o jogador está na posição correta
            if (posicao == movimentacao[batidaAtual % 3]) {
                //Vibra o celular, alertando que ele acertou a batida
                Gdx.input.vibrate(200);
            }

            //Se o jogador errou 5 batidas consecutivas
            if(batidasFalhas >= 5) {
                //Define o estado como perdeu
                estado = "perdeu";
                //Para a música e libera o espaço alocado
                musica.stop();
                musica.dispose();
            }
        }
    }

    //Método para pegar o estado do jogo
    public String pegarEstado()
    {
        return estado;
    }

    //Método para pegar a pontuação
    public int pegarPontuacao() { return pontuacao; }

    @java.lang.Override
    public void hide() {
        musica.stop();
        musica.dispose();
    }
}
