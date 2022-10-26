package com.jazzhands.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class Controlador extends Game {
    Menu telaMenu;
    JogoTcc telaJogo;
    String estado;
    double anguloX, anguloY, anguloZ;

    @Override
    public void create() {
        trocarParaJogo();
        estado = "jogando";
    }

    @Override
    public void render()
    {
        //Pega o valor do acelerômetro, em m/s^2, e converte para graus
        anguloX = Gdx.input.getAccelerometerX() * 9.18;
        anguloY = Gdx.input.getAccelerometerY() * 9.18;
        anguloZ = Gdx.input.getAccelerometerZ() * 9.18;
        switch (estado)
        {
            case "menu": telaMenu.render(60, anguloX, anguloY, anguloZ); break;
            case "jogando": telaJogo.render(60, anguloX, anguloY, anguloZ); break;
            default: break;
        }
    }

    private void trocarParaJogo()
    {
        estado = "jogando";
        telaJogo = new JogoTcc();
        setScreen(telaJogo);
        telaJogo.iniciar("TheFatRat - Unity (320 kbps) (mp3cut.net).mp3"); // Cartoon - On & On (Lyrics) feat. Daniel Levi
    }
}
