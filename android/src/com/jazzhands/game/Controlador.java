package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Controlador extends Game {
    //Objetos das telas
    Menu telaMenu;
    JogoTcc telaJogo;
    TelaPontuacao telaPontuacao;

    //Variável que define o que está acontecendo
    String estado;

    //Variáveis para controle da posição do celular
    double anguloX, anguloY, anguloZ;
    String posicao;
    Context contexto;

    //Ao abrir o jogo irá chamar o método de ir para o menu
    public Controlador(Context context) {
        contexto = context;
    }

    @Override
    public void create()
    {
        trocarParaMenu();
    }

    //A cada 1/60 segundos
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
        else
        {
            if(anguloY < -60) {
                posicao = "baixo";
            }
                //Caso esteja inclinado, verifica se é pra frente ou pra trás
            else
            {
                if(anguloX > 60)
                {
                    posicao = "esquerda";
                }
                else
                {
                    if(anguloX < -60)
                    {
                        posicao = "direita";
                    }
                    else
                    {
                        if(anguloZ > 0)
                            posicao = "frente";
                        else
                            posicao = "tras";
                    }
                }
            }
        }

        //Controle do que renderizar do jogo
        switch (estado)
        {
            //Caso esteja no menu
            case "menu":
                //Processa
                telaMenu.render(posicao);
                //Verifica o estado do menu
                switch (telaMenu.pegarEstado())
                {
                    //Caso o jogador tenha escolhido uma música, troca para o jogo
                    case "jogando": trocarParaJogo(); break;
                    //Caso não tenha feito nada, sai do break
                    default: break;
                }
                break;
            //Caso esteja no jogo
            case "jogando":
                //Verifica o estado do jogo
                switch (telaJogo.pegarEstado()) {
                    //Caso ainda esteja jogando, processa o jogo
                    case "jogando": telaJogo.render(posicao); break;
                    //Caso tenha acabado a música, troca para o menu
                    case "perdeu":
                    case "ganhou": trocarParaPontuacao(); break;
                }
                break;
            case "analisandoPontuacao": {
                telaPontuacao.render(posicao);
                if (telaPontuacao.pegarEstado() == "menu")
                    trocarParaMenu();
                break;
            }
        }
    }

    //Método para trocar para o menu
    private void trocarParaMenu()
    {
        //Define o estado do jogo
        estado = "menu";
        //Cria uma instância de menu e abre ela
        if(telaMenu == null)
            telaMenu = new Menu();
        
        setScreen(telaMenu);
    }

    //Método para trocar para o jogo
    private void trocarParaJogo()
    {
        if(telaJogo != null) {
            telaJogo.hide();
        }

        telaJogo = null;
        //Define o estado do jogo
        estado = "jogando";
        //Cria uma instância do jogo e abre ela
        telaJogo = new JogoTcc();
        setScreen(telaJogo);
        //Passa para o jogo as informações da música escolhida
        telaJogo.iniciar(telaMenu.pegarMusica(), contexto);
    }

    private void trocarParaPontuacao()
    {
        if(telaPontuacao != null) {
            telaPontuacao.hide();
        }

        telaPontuacao = null;
        telaPontuacao = new TelaPontuacao();
        setScreen(telaPontuacao);
        telaPontuacao.analisarPontuacaoMaxima(telaMenu.pegarMusica(), contexto, telaJogo.pegarPontuacao(), estado);
        estado = "analisandoPontuacao";
    }
}
