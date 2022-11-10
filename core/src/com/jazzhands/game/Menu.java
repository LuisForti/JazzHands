package com.jazzhands.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Menu implements Screen {
    //Variável para definir o estado do menu
    private String estado;

    //Matriz com as informações das músicas
    private String[][] listaDasMusicas = {
            {"100years.mp3", "34"}, //Disponível em: https://www.youtube.com/watch?v=-kWFRyQ5VU8&list=PLAdwl9xZlNTB4pHCmi81eG2U_CB93Q5eF&index=6
            {"deNada.mp3", "29"}, //Disponível em: https://www.youtube.com/watch?v=HCywogc4NqA
            {"dragonsquest.mp3", "24"}, //Disponível em: https://www.youtube.com/watch?v=J4L1dVYmotg
            {"everythingGoesOn.mp3", "29"}, //Disponível em: https://www.youtube.com/watch?v=sgWAeJyM6QI
            {"goodEnough.mp3", "33"}, //Disponível em: https://www.youtube.com/watch?v=thZqzBtnXCY&t=27s
            {"hisTheme.mp3", "33"}, //Disponível em: https://www.youtube.com/watch?v=IkOK8tdEsFY
            {"karma.mp3", "29"}, //Disponível em: https://www.youtube.com/watch?v=wsFB9Z7DfFc
            {"piratasDoCaribe.mp3", "30"}, //Disponível em: https://www.youtube.com/watch?v=ZLZ5I3-9B6U
            {"pokemonTema.mp3", "26"}, //Disponível em: https://www.youtube.com/watch?v=La0SnKkNaZw
            {"stillDancing.mp3", "30"}, //Disponível em: https://www.youtube.com/watch?v=AlYdp8P1s6c
            {"wellerman.mp3", "35"} //Disponível em: https://www.youtube.com/watch?v=XBz9-2G2FoU
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
