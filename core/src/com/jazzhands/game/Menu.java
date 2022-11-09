package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;

public class Menu implements Screen {
    private String estado;

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
