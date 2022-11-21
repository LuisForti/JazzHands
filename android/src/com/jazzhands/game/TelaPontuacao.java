package com.jazzhands.game;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.jazzhands.game.BD.Create;
import com.jazzhands.game.BD.Pontuacao;
import com.jazzhands.game.BD.Read;
import com.jazzhands.game.BD.Update;

public class TelaPontuacao extends ApplicationAdapter implements Screen {
    int pontuacao, maiorPontuacao;
    Music audio;
    String estado = "analisandoPontuacao";

    @Override
    public void show() {
    }

    public void analisarPontuacaoMaxima(String[] enderecoMusica, Context contexto, int pontuacaoAtual, String estado)
    {
        if(estado == "ganhou")
        {
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\TerminouAMusica.mp3"));
            audio.play();
        }
        else
        {
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Perdeu.mp3"));
            audio.play();
        }

        int idMusica = Integer.parseInt(enderecoMusica[2]);

        Create c = new Create(contexto);
        c.createTable();

        Read r = new Read(contexto);
        Pontuacao p1 = r.getPontuacao(idMusica);
        maiorPontuacao = p1.getPontos();
        pontuacao = pontuacaoAtual;

        while (audio.isPlaying())
            ;

        if(pontuacao > maiorPontuacao) {
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PassouRecordeIntro.mp3"));
            audio.play();

            Pontuacao p = new Pontuacao();
            p.setId(idMusica);
            p.setPontos(pontuacao);
            Update u = new Update(contexto);
            u.updatePontuacao(p);

            while (audio.isPlaying())
                ;

            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PassouRecordeFim" + (int)(Math.random() * 3 + 1) + ".mp3"));
            audio.play();
            while (audio.isPlaying())
                ;
        }
        else
        {
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\NaoPassouRecorde.mp3"));
            audio.play();
            while (audio.isPlaying())
                ;
        }

        render("frente");

        audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\MostrarPontuacaoEVoltarMenu.mp3"));
        audio.play();
        while (audio.isPlaying())
            ;
    }

    @Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    public void render(String posicao) {
        switch (posicao) {
            case "frente": {
                if(audio.isPlaying()) {
                    audio.stop();
                    audio.dispose();
                }

                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PontuacaoTotalFoi.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;

                if (pontuacao > 1999)
                    lerNumero(pontuacao, 4, true);
                else
                    lerNumero(pontuacao, 3, false);

                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\SeuRecordeFoi.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;

                if (maiorPontuacao > 1999)
                    lerNumero(maiorPontuacao, 4, true);
                else
                    lerNumero(maiorPontuacao, 3, false);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
            case "direita":
            case "esquerda": {
                if(audio.isPlaying()) {
                    audio.stop();
                    audio.dispose();
                }

                estado = "menu";
                break;
            }
            default: break;
        }
    }

    private void lerNumero(int valor, int expoente, boolean ehMilhar)
    {
        try {
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\"+ valor +".mp3"));
            audio.play();
            while (audio.isPlaying())
                ;
        }
        catch (Exception err)
        {
            int resto = valor % (int)Math.pow(10, expoente);
            valor = valor / (int)Math.pow(10, expoente);
            valor = valor * (int)Math.pow(10, expoente);
            expoente--;

            if(valor > 999 && ehMilhar)
                valor /= 1000;

            if(valor != 0) {
                if (valor == 100)
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\CENTO.mp3"));
                else
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\" + valor + ".mp3"));

                audio.play();
                while (audio.isPlaying())
                    ;
                if (resto < 1000 && ehMilhar) {
                    ehMilhar = false;
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\1000.mp3"));
                    audio.play();
                    while (audio.isPlaying())
                        ;
                }
                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\E.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;
            }
            if(resto != 0) {
                lerNumero(resto, expoente, ehMilhar);
            }
        }
    }

    public String pegarEstado()
    {
        return estado;
    }

    @Override
    public void hide() {
        audio.stop();
        audio.dispose();
    }
}
