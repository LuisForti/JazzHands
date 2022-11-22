package com.jazzhands.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class Menu implements Screen {
    //Variável para definir o estado do menu
    private String estado;

    //Matriz com as informações das músicas (arquivo, batidas por minuto (BPM) e número da música)
    private String[][] listaDasMusicas = {
            {"Musicas\\100years.mp3", "34", "1"}, //Disponível em: https://www.youtube.com/watch?v=-kWFRyQ5VU8&list=PLAdwl9xZlNTB4pHCmi81eG2U_CB93Q5eF&index=6
            {"Musicas\\deNada.mp3", "29", "2"}, //Disponível em: https://www.youtube.com/watch?v=HCywogc4NqA
            {"Musicas\\dragonsQuest.mp3", "24", "3"}, //Disponível em: https://www.youtube.com/watch?v=J4L1dVYmotg
            {"Musicas\\everythingGoesOn.mp3", "29", "4"}, //Disponível em: https://www.youtube.com/watch?v=sgWAeJyM6QI
            {"Musicas\\goodEnough.mp3", "33", "5"}, //Disponível em: https://www.youtube.com/watch?v=thZqzBtnXCY&t=27s
            {"Musicas\\hisTheme.mp3", "33", "6"}, //Disponível em: https://www.youtube.com/watch?v=IkOK8tdEsFY
            {"Musicas\\karma.mp3", "29", "7"}, //Disponível em: https://www.youtube.com/watch?v=wsFB9Z7DfFc
            {"Musicas\\piratasDoCaribe.mp3", "30", "8"}, //Disponível em: https://www.youtube.com/watch?v=ZLZ5I3-9B6U
            {"Musicas\\pokemonTema.mp3", "26", "9"}, //Disponível em: https://www.youtube.com/watch?v=La0SnKkNaZw
            {"Musicas\\stillDancing.mp3", "30", "10"}, //Disponível em: https://www.youtube.com/watch?v=AlYdp8P1s6c
            {"Musicas\\wellerman.mp3", "35", "11"} //Disponível em: https://www.youtube.com/watch?v=XBz9-2G2FoU
    };

    //Variável que define a música que o jogador está observando
    private int musicaEscolhida = -1;
    //Variáveis para medirem o tempo
    private int frame = 0;
    private int ultimaMudanca = -60;
    //Variável para ver se o jogador está pronto para navegar no menu
    private boolean estaPronto = false;

    //Objeto que fará os anúncios
    Music nomeMusica;

    //Ao abrir a tela, define estado como menu
    @java.lang.Override
    public void show() {
        estado = "menu";
    }

    @java.lang.Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    //Método para processar a movimentação do jogador
    public void render(String posicao) {
        //A cada 1/60 segundos aumenta em 1
        frame += 1;

        //Se o jogador já se posicionou para escolher a música
        if(estaPronto) {
            //Se a última mudança de música do jogador foi a mais de 1 segundo
            if (ultimaMudanca + 60 <= frame) {
                //Verifica qual posição ele está
                switch (posicao) {
                    //Caso para frente
                    case "frente":
                        //Avança uma música
                        musicaEscolhida++;
                        //Se tiver saído do limite de músicas, volta para dentro
                        if (musicaEscolhida >= listaDasMusicas.length)
                            musicaEscolhida = listaDasMusicas.length - 1;

                        //Diz o nome da música
                        dizerNome();
                        //Define ultimaMudanca como o momento atual
                        ultimaMudanca = frame;
                        //Vibra o celular
                        Gdx.input.vibrate(200);

                        break;
                    //Caso para trás
                    case "tras":
                        //Volta uma música
                        musicaEscolhida--;
                        //Se tiver saído do limite de músicas, volta para dentro
                        if (musicaEscolhida < 0)
                            musicaEscolhida = 0;

                        //Diz o nome da música
                        dizerNome();
                        //Define ultimaMudanca como o momento atual
                        ultimaMudanca = frame;
                        //Vibra o celular
                        Gdx.input.vibrate(200);
                        break;
                    //Caso para a esquerda ou a direita
                    case "esquerda":
                    case "direita":
                        //Para o áudio ativo e libera, se tiver um
                        if(nomeMusica != null) {
                            nomeMusica.stop();
                            nomeMusica.dispose();
                        }

                        //Se musicaEscolhida for menor que 0, ou seja, o jogador escolheu a música sem avançar ou voltar
                        if(musicaEscolhida < 0)
                            //Pega a primeira música
                            musicaEscolhida = 0;

                        //Limpa nomeMusica e define estaPronto como falso, para o jogador ter tempo de se posicionar novamente quando voltar ao menu
                        nomeMusica = null;
                        estaPronto = false;
                        //Define o estado como jogando
                        estado = "jogando";
                        break;
                    //Caso não seja nenhuma das posições anteriores, não faz nada
                    default:
                        break;
                }
            }
        }
        else
        {
            //Caso não tenha se posicionado
            if(nomeMusica == null)
            {
                //Avisa o jogador para posicionar o celular na posição neutra (para cima)
                nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\AntesDeQualquerCoisa.mp3"));
                nomeMusica.play();
            }
            //Caso ele se posicione
            if(posicao == "cima") {
                //Para nomeMusica e libera o espaço alocado ao áudio
                nomeMusica.stop();
                nomeMusica.dispose();

                //Define que o jogador está pronto para escolher uma música
                estaPronto = true;
                //Passa ao jogador as instruções de como operar o menu
                nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Menu.mp3"));
                nomeMusica.play();
            }
        }
    }

    //Método para anunciar a música atual
    private void dizerNome()
    {
        //Se tiver um áudio tocando
        if(nomeMusica.isPlaying()) {
            //Para ele e libera o espaço alocado ao áudio
            nomeMusica.stop();
            nomeMusica.dispose();
        }

        //Diz a descrição da música atual
        nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Musica " + (listaDasMusicas[musicaEscolhida][2]) + ".mp3"));
        nomeMusica.play();
    }

    //Método para pegar o estado do menu
    public String pegarEstado()
    {
        return estado;
    }

    //Método para pegar a música atual
    public String[] pegarMusica()
    {
        return listaDasMusicas[musicaEscolhida]; //Wellerman - Alexander Nakarada. Disponível em: https://www.youtube.com/watch?v=XBz9-2G2FoU
    }

    //Métodos obrigatórios de Screen

    @java.lang.Override
    public void resize(int width, int height) {

    }

    @java.lang.Override
    public void pause() {

    }

    @java.lang.Override
    public void resume() {

    }

    @java.lang.Override
    public void hide() {

    }

    @java.lang.Override
    public void dispose() {

    }
}
