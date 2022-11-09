package com.jazzhands.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class Controlador extends Game {
    Menu telaMenu;
    JogoTcc telaJogo;
    String estado;
    double anguloX, anguloY, anguloZ;
    String posicao;

    @Override
    public void create() {
        trocarParaMenu();
    }

    @Override
    public void render()
    {
        //Pega o valor do acelerômetro, em m/s^2, e converte para graus
        anguloX = Gdx.input.getAccelerometerX() * 9.18;
        anguloY = Gdx.input.getAccelerometerY() * 9.18;
        anguloZ = Gdx.input.getAccelerometerZ() * 9.18;
        // Para cima
        if (anguloY > 40) {
            posicao = "cima";
        }
        //Para trás ou para frente
        else if (anguloY < 40) {
            if (anguloX < 40 && anguloX > -40) {
                if (anguloZ > 0)
                    posicao = "frente";
                else
                    posicao = "trás";
            } else if (anguloX > 40) {
                posicao = "esquerda";
            } else
                posicao = "direita";
        }
        switch (estado)
        {
            case "menu":
                telaMenu.render(posicao);
                switch (telaMenu.pegarEstado())
                {
                    case "jogando": trocarParaJogo();
                    default: break;
                }
                break;
            case "jogando": telaJogo.render(posicao); break;
            default: break;
        }
    }

    private void trocarParaMenu()
    {
        estado = "menu";
        telaMenu = new Menu();
        setScreen(telaMenu);
    }

    private void trocarParaJogo()
    {
        estado = "jogando";
        telaJogo = new JogoTcc();
        setScreen(telaJogo);
        telaJogo.iniciar("TheFatRat - Unity (320 kbps) (mp3cut.net).mp3");
    }
}
