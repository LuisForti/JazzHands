package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;

public class Menu implements Screen {
    private String estado;
    private String[][] listaDasMusicas = {
            {"100years.mp3", "34"},
            {"deNada.mp3", "29"},
            {"everythingGoesOn.mp3", "29"},
            {"goodEnough.mp3", "33"},
            {"hisTheme.mp3", "33"},
            {"karma.mp3", "29"},
            {"piratasDoCaribe.mp3", "30"},
            {"pokemonTema.mp3", "26"},
            {"stillDancing.mp3", "30"},
            {"wellerman.mp3", "35"}
    };
    private byte musicaEscolhida = 0;

    @java.lang.Override
    public void show() {
        estado = "menu";
    }

    @java.lang.Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    public void render(String posicao) {
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
