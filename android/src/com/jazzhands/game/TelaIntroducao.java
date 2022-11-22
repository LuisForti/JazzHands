package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class TelaIntroducao extends ApplicationAdapter implements Screen {
    String estado;
    Music audio;

    //Variáveis de controle do tempo
    int ultimoAudio = -300;
    int frame = 0;

    //Ao abrir a tela, define o estado como introducao
    @Override
    public void show() {
        estado = "introducao";
    }

    @Override
    public void render(float delta) { /*Método obrigatório de Screen*/}

    //Método que processa a posição do jogador
    public void render(String posicao) {
        //Se áudio for nulo (primeira vez que renderiza)
        if(audio == null)
        {
            //Introduz ao jogador
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Introducao.mp3"));
            audio.play();
            //Para esperar o áudio tocar
            while (audio.isPlaying())
                ;
            //Libera o espaço alocado ao áudio
            audio.dispose();

            //Pergunta se o jogador quer fazer a introdução
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ComecarIntroducao.mp3"));
            audio.play();
            while (audio.isPlaying())
                ;
        }
        else {
            //Verifica se o jogador quer fazer a introdução
            if (estado == "introducao") {
                switch (posicao) {
                    //Caso queira
                    case "frente":
                        estado = "introduzindoC";
                        break;
                    //Caso não
                    case "tras":
                        audio.dispose();
                        estado = "menu";
                        break;
                    default:
                        break;
                }
            }
            else {
                //Aumenta frame em 1
                frame++;
                //Verifica em qual parte da introdução o jogador está
                switch (estado) {
                    //Caso esteja sendo introduzido ao movimento para cima
                    case "introduzindoC":
                        //A cada 5 segundos
                        if (frame >= ultimoAudio + 300) {
                            //Libera o espaço alocado ao áudio
                            audio.dispose();
                            //Pede para o jogador se posicionar para cima
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\IntroducaoCima.mp3"));
                            audio.play();

                            ultimoAudio = frame;
                        }
                        //Se estiver apontando para cima
                        if (posicao == "cima") {
                            //Para e libera o áudio, caso esteja ativo
                            if (audio.isPlaying()) {
                                audio.stop();
                            }

                            //Libera o espaço alocado ao áudio
                            audio.dispose();
                            //Explica o que significa esta posição
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ExplicacaoCima.mp3"));
                            audio.play();
                            while (audio.isPlaying())
                                ;

                            ultimoAudio = frame - 300;
                            //Avança para o próximo estágio da introdução
                            estado = "introduzindoE";
                        }
                        break;
                    //Mesmo princípio do anterior, mas para a esquerda
                    case "introduzindoE":
                        if (frame >= ultimoAudio + 300) {
                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\IntroducaoEsquerda.mp3"));
                            audio.play();
                            ultimoAudio = frame;
                        }
                        if (posicao == "esquerda") {
                            if (audio.isPlaying()) {
                                audio.stop();
                            }

                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ExplicacaoEsquerda.mp3"));
                            audio.play();
                            while (audio.isPlaying())
                                ;

                            ultimoAudio = frame - 300;
                            estado = "introduzindoD";
                        }
                        break;
                    //Mesmo princípio, mas para a direita
                    case "introduzindoD":
                        if (frame >= ultimoAudio + 300) {
                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\IntroducaoDireita.mp3"));
                            audio.play();
                            ultimoAudio = frame;
                        }
                        if (posicao == "direita") {
                            if (audio.isPlaying()) {
                                audio.stop();
                            }

                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ExplicacaoDireita.mp3"));
                            audio.play();
                            while (audio.isPlaying())
                                ;

                            ultimoAudio = frame - 300;
                            estado = "introduzindoF";
                        }
                        break;
                    //Para frente
                    case "introduzindoF":
                        if (frame >= ultimoAudio + 300) {
                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\IntroducaoFrente.mp3"));
                            audio.play();
                            ultimoAudio = frame;
                        }
                        if (posicao == "frente") {
                            if (audio.isPlaying()) {
                                audio.stop();
                            }

                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ExplicacaoFrente.mp3"));
                            audio.play();
                            while (audio.isPlaying())
                                ;

                            ultimoAudio = frame - 300;
                            estado = "introduzindoT";
                        }
                        break;
                    //Para trás
                    case "introduzindoT":
                        if (frame >= ultimoAudio + 300) {
                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\IntroducaoTras.mp3"));
                            audio.play();
                            ultimoAudio = frame;
                        }
                        if (posicao == "tras") {
                            if (audio.isPlaying()) {
                                audio.stop();
                            }

                            audio.dispose();
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\ExplicacaoTras.mp3"));
                            audio.play();
                            while (audio.isPlaying())
                                ;

                            ultimoAudio = frame - 480;
                            estado = "introduzindoR";
                        }
                        break;
                    //Caso o jogador tenha passado por toda a introdução
                    case "introduzindoR":
                        //A cada 8 segundos
                        if (frame >= ultimoAudio + 480) {
                            //Libera o espaço alocado ao áudio
                            audio.dispose();
                            //Pergunta se o jogador quer repetir a introdução
                            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\RepetirIntroducao.mp3"));
                            audio.play();
                            ultimoAudio = frame;
                        }
                        //Verifica a posição do jogador
                        switch (posicao) {
                            //Caso não queira repetir a introdução
                            case "esquerda":
                                //Caso tenha um áudio ativo, para ele
                                if (audio.isPlaying()) {
                                    audio.stop();
                                }
                                //Libera o espaço alocado ao áudio
                                audio.dispose();
                                //Troca o estado para menu
                                estado = "menu";
                                break;
                            //Caso queira repetir a introdução
                            case "direita":
                                //Caso tenha um áudio ativo, para ele
                                if (audio.isPlaying()) {
                                    audio.stop();
                                }

                                //Libera o espaço alocado ao áudio
                                audio.dispose();
                                //Reinicia as variáveis de tempo
                                ultimoAudio = -300;
                                frame = 0;
                                //Retorna para a primeira etapa da introdução
                                estado = "introduzindoC";
                                break;
                        }
                }
            }
        }
    }

    //Método para pegar o estado do jogo
    public String pegarEstado()
    {
        return estado;
    }

    @Override
    public void hide() {

    }
}
