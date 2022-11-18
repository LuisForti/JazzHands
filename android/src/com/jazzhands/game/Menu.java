package com.jazzhands.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Menu implements Screen {
    //Variável para definir o estado do menu
    private String estado;

    //Matriz com as informações das músicas
    private String[][] listaDasMusicas = {
            {"100years_com_metronomo.mp3", "34", "1"}, //Disponível em: https://www.youtube.com/watch?v=-kWFRyQ5VU8&list=PLAdwl9xZlNTB4pHCmi81eG2U_CB93Q5eF&index=6
            {"deNada_com_metronomo.mp3", "29", "2"}, //Disponível em: https://www.youtube.com/watch?v=HCywogc4NqA
            {"dragonsQuest_com_metronomo.mp3", "24", "3"}, //Disponível em: https://www.youtube.com/watch?v=J4L1dVYmotg
            {"everythingGoesOn_com_metronomo.mp3", "29", "4"}, //Disponível em: https://www.youtube.com/watch?v=sgWAeJyM6QI
            {"goodEnough_com_metronomo.mp3", "33", "5"}, //Disponível em: https://www.youtube.com/watch?v=thZqzBtnXCY&t=27s
            {"hisTheme_com_metronomo.mp3", "33", "6"}, //Disponível em: https://www.youtube.com/watch?v=IkOK8tdEsFY
            {"karma_com_metronomo.mp3", "29", "7"}, //Disponível em: https://www.youtube.com/watch?v=wsFB9Z7DfFc
            {"piratasDoCaribe_com_metronomo.mp3", "30", "8"}, //Disponível em: https://www.youtube.com/watch?v=ZLZ5I3-9B6U
            {"pokemonTema_com_metronomo.mp3", "26", "9"}, //Disponível em: https://www.youtube.com/watch?v=La0SnKkNaZw
            {"stillDancing_com_metronomo.mp3", "30", "10"}, //Disponível em: https://www.youtube.com/watch?v=AlYdp8P1s6c
            {"wellerman_com_metronomo.mp3", "35", "11"} //Disponível em: https://www.youtube.com/watch?v=XBz9-2G2FoU
    };

    //Variável que define a música que o jogador está observando
    private int musicaEscolhida = 0;
    private int frame = 0;
    private int ultimaMudanca = -60;

    @java.lang.Override
    public void show() {
        estado = "menu";
    }

    @java.lang.Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    public void render(String posicao) {
        frame += 1;
        if(ultimaMudanca + 60 <= frame)
        {
            switch (posicao)
            {
                case "frente": musicaEscolhida++; ultimaMudanca = frame; Gdx.input.vibrate(200); break;
                case "tras": musicaEscolhida--; ultimaMudanca = frame; Gdx.input.vibrate(200); break;
                default: break;
            }
            if(musicaEscolhida < 0)
                musicaEscolhida = 0;
            if(musicaEscolhida >= listaDasMusicas.length)
                musicaEscolhida = listaDasMusicas.length-1;
        }
        switch (posicao)
        {
            case "esquerda":
            case "direita":
                estado = "jogando";
                break;
            default:
                estado = "menu";
                break;
        }
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
