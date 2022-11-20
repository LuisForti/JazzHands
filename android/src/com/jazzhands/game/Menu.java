package com.jazzhands.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

public class Menu implements Screen {
    //Variável para definir o estado do menu
    private String estado;

    //Matriz com as informações das músicas
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
    private int musicaEscolhida = 0;
    private int frame = 0;
    private int ultimaMudanca = -60;
    private boolean estaPronto = false;

    Music nomeMusica;

    @java.lang.Override
    public void show() {
        estado = "menu";
    }

    @java.lang.Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    public void render(String posicao) {
        frame += 1;
        if(estaPronto) {
            if (ultimaMudanca + 60 <= frame) {
                switch (posicao) {
                    case "frente":
                        musicaEscolhida++;
                        if (musicaEscolhida >= listaDasMusicas.length)
                            musicaEscolhida = listaDasMusicas.length - 1;
                        dizerNome();
                        ultimaMudanca = frame;
                        Gdx.input.vibrate(200);

                        break;
                    case "tras":
                        musicaEscolhida--;
                        if (musicaEscolhida < 0)
                            musicaEscolhida = 0;
                        dizerNome();
                        ultimaMudanca = frame;
                        Gdx.input.vibrate(200);
                        break;
                    default:
                        break;
                }
            }
            switch (posicao) {
                case "esquerda":
                case "direita":
                    if(nomeMusica != null)
                        nomeMusica.stop();

                    nomeMusica = null;
                    estado = "jogando";
                    estaPronto = false;
                    break;
                default:
                    estado = "menu";
                    break;
            }
        }
        else
        {
            if(nomeMusica == null)
            {
                nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\AntesDeQualquerCoisa.mp3"));
                nomeMusica.play();
            }
            if(posicao == "cima") {
                nomeMusica.stop();
                estaPronto = true;
                nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Menu.mp3"));
                nomeMusica.play();
            }
        }
    }

    private void dizerNome()
    {
        if(nomeMusica != null)
            nomeMusica.stop();

        nomeMusica = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Musica " + (listaDasMusicas[musicaEscolhida][2]) + ".mp3"));
        nomeMusica.play();
    }

    public String pegarEstado()
    {
        return estado;
    }

    public String[] pegarMusica()
    {
        return listaDasMusicas[musicaEscolhida]; //Wellerman - Alexander Nakarada. Disponível em: https://www.youtube.com/watch?v=XBz9-2G2FoU
    }

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
