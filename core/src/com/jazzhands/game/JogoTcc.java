package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JogoTcc extends ApplicationAdapter implements Screen {
    SpriteBatch batch;
    BitmapFont fonte;

    Music musica;

    int frame = 0;
    int ritmo = 0;
    double proximaBatida = 0;
    int batidaAtual = 0;
    String[][] movimentacao = {{"cima", "frente"}};
    int batidasFalhas = 0;

    int pontuacao = 0;
    String estado = "jogando";


    @java.lang.Override
    public void show() {

    }

    @java.lang.Override
    public void render(float delta) {
        //Método obrigatório de Screen
    }

    public void iniciar(String[] enderecoMusica)
    {
        musica = Gdx.audio.newMusic(Gdx.files.internal(enderecoMusica[0]));
        ritmo = Integer.parseInt(enderecoMusica[1]);
        proximaBatida = ritmo;

        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.getData().setScale(1);
    }

    public void render(String posicao) {
        // Processamento da música
        if(frame == 0) {
            musica.play();
            estado = "jogando";
        }

        if(!musica.isPlaying())
            estado = "ganhou";

        frame += 1;

        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame >= proximaBatida) {
            batidaAtual++;
            proximaBatida += ritmo;
            if (posicao == movimentacao[0][batidaAtual % 2])
            {
                if(batidaAtual > 10) {
                    pontuacao++;
                    batidasFalhas = 0;
                }
                Gdx.input.vibrate(200);
            }
            else
                batidasFalhas++;
            if(batidasFalhas >= 10) {
                estado = "perdeu";
                musica.stop();
                //Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://aws-sa-east-1.connect.psdb.cloud/jazzhands?sslMode=VERIFY_IDENTITY",
                            "85y7ejgyd54nl7sm22s0",
                            "main-2022-nov-10-0o5cv2");
                }
                catch (Exception err)
                {
                    StringWriter sw = new StringWriter();
                    err.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();

                    ScreenUtils.clear(0, 0, 0, 1);
                    batch.begin();
                    fonte.draw(batch, exceptionAsString, 0, 1200);
                    batch.end();
                }
            }
        }
    }

    public String pegarEstado()
    {
        return estado;
    }

    @java.lang.Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
