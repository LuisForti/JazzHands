package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

//Classe que controla as telas
public class Controlador extends Game {
    //Objetos das telas
    TelaIntroducao telaIntroducao;
    Menu telaMenu;
    JogoTcc telaJogo;
    TelaPontuacao telaPontuacao;

    //Variável que define o que está acontecendo
    String estado;

    //Variáveis para controle da posição do celular
    double anguloX, anguloY, anguloZ;
    String posicao;
    Context contexto;

    //Ao criar vai passar o contexto do programa, variável necessária para o banco de dados
    public Controlador(Context context) {
        contexto = context;
    }

    //Ao abrir vai trocar para a tela de Introdução
    @Override
    public void create()
    {
        trocarParaIntroducao();
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
            //Para baixo
            if(anguloY < -60) {
                posicao = "baixo";
            }
                //Caso esteja inclinado, verifica para onde está
            else
            {
                if(anguloX > 40)
                {
                    posicao = "esquerda";
                }
                else
                {
                    if(anguloX < -40)
                    {
                        posicao = "direita";
                    }
                    else
                    {
                        if(anguloZ > 20)
                            posicao = "frente";
                        else if (anguloZ < -20)
                            posicao = "tras";
                    }
                }
            }
        }

        //Controle do que renderizar no jogo
        switch (estado)
        {
            //Caso esteja na introdução
            case "introducao":
            {
                //Processa
                telaIntroducao.render(posicao);
                //Verifica se o jogador quer ir para o menu
                if(telaIntroducao.pegarEstado() == "menu")
                {
                    //Caso queira, libera telaIntroducao e troca para o menu
                    telaIntroducao = null;
                    trocarParaMenu();
                }
                break;
            }
            //Caso esteja no menu
            case "menu": {
                //Processa
                telaMenu.render(posicao);
                //Verifica o estado do menu
                if (telaMenu.pegarEstado() == "jogando") {
                    //Caso o jogador tenha escolhido uma música, troca para o jogo
                    trocarParaJogo();
                }
                break;
            }
            //Caso esteja no jogo
            case "jogando": {
                //Pega o estado do jogo
                estado = telaJogo.pegarEstado();
                //Verifica o estado do jogo
                switch (estado) {
                    //Caso ainda esteja jogando, processa o jogo
                    case "jogando": telaJogo.render(posicao); break;
                    //Caso tenha acabado a música, troca para a tela de pontuação
                    case "perdeu":
                    case "ganhou": trocarParaPontuacao(); break;
                }
                break;
            }
            //Caso esteja na tela de pontuação
            case "analisandoPontuacao": {
                //Processa
                telaPontuacao.render(posicao);
                //Caso o jogador tenha terminado a introdução
                if (telaPontuacao.pegarEstado() == "menu")
                    //Troca para o menu
                    trocarParaMenu();
                break;
            }
        }
    }

    //Método para trocar para a introdução
    private void trocarParaIntroducao()
    {
        //Define o estado
        estado = "introducao";
        //Cria uma TelaIntroducao e abre ela
        telaIntroducao = new TelaIntroducao();

        setScreen(telaIntroducao);
    }

    //Método para trocar para o menu
    private void trocarParaMenu()
    {
        //Define o estado
        estado = "menu";
        //Cria uma instância de menu, caso não tenha uma, e abre ela
        if(telaMenu == null)
            telaMenu = new Menu();
        
        setScreen(telaMenu);
    }

    //Método para trocar para o jogo
    private void trocarParaJogo()
    {
        //Caso tenha um jogo criado
        if(telaJogo != null) {
            //Chama o método hide para eliminar as instâncias salvas no jogo
            telaJogo.hide();
        }

        //Limpa telaJogo
        telaJogo = null;
        //Define o estado
        estado = "jogando";
        //Cria uma instância do jogo e abre ela
        telaJogo = new JogoTcc();
        setScreen(telaJogo);
        //Passa para o jogo as informações da música escolhida
        telaJogo.iniciar(telaMenu.pegarMusica());
    }

    //Método para trocar para a pontuação
    private void trocarParaPontuacao()
    {
        //Caso já tenha uma tela de pontuação
        if(telaPontuacao != null) {
            //Chama o método hide para eliminar as instâncias salvas
            telaPontuacao.hide();
        }

        //Limpa telaPontuacao
        telaPontuacao = null;
        //Cria uma instancia da tela de pontuação e abre ela
        telaPontuacao = new TelaPontuacao();
        setScreen(telaPontuacao);
        //Passa as informações necessárias para analisar a pontuação (qual música, o contexto para abrir o banco de dados, a pontuação e o estado se venceu ou perdeu)
        telaPontuacao.analisarPontuacaoMaxima(telaMenu.pegarMusica(), contexto, telaJogo.pegarPontuacao(), estado);
        //Define o estado
        estado = "analisandoPontuacao";
    }
}
