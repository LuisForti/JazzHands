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

    //Método para analisar o resultado da música
    public void analisarPontuacaoMaxima(String[] enderecoMusica, Context contexto, int pontuacaoAtual, String estado)
    {
        //Se ele ganhou
        if(estado == "ganhou")
        {
            //Toca o áudio de vitória
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\TerminouAMusica.mp3"));
            audio.play();
        }
        else
        {
            //Toca o áudio de derrota
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\Perdeu.mp3"));
            audio.play();
        }

        //Pega o id da música
        int idMusica = Integer.parseInt(enderecoMusica[2]);

        //Cria uma instância da classe Create
        Create c = new Create(contexto);
        //Tenta criar a tablea
        c.createTable();

        //Cria uma instância de Read e lê o recorde da música
        Read r = new Read(contexto);
        Pontuacao p1 = r.getPontuacao(idMusica);
        maiorPontuacao = p1.getPontos();
        pontuacao = pontuacaoAtual;

        //Espera o áudio acabar
        while (audio.isPlaying())
            ;
        //Libera o espaço alocado ao áudio
        audio.dispose();

        //Se o jogador superou seu recorde
        if(pontuacao > maiorPontuacao) {
            //Toca o áudio comemorando
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PassouRecordeIntro.mp3"));
            audio.play();

            //Atualiza a pontuação da música no banco de dados
            Pontuacao p = new Pontuacao();
            p.setId(idMusica);
            p.setPontos(pontuacao);
            Update u = new Update(contexto);
            u.updatePontuacao(p);

            //Espera o áudio terminar
            while (audio.isPlaying())
                ;
            //Libera o espaço alocado ao áudio
            audio.dispose();

            //Toca a segunda parte do áudio comemorando
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PassouRecordeFim" + (int)(Math.random() * 3 + 1) + ".mp3"));
            audio.play();
            //Espera o áudio acabar
            while (audio.isPlaying())
                ;
            //Libera o espaço alocado ao áudio
            audio.dispose();
        }
        else
        {
            //Toca o áudio alertando que não superou seu recorde
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\NaoPassouRecorde.mp3"));
            audio.play();
            //Espera o áudio acabar
            while (audio.isPlaying())
                ;
            //Libera o espaço alocado ao áudio
            audio.dispose();
        }

        //Chama o método render passando a posição frente para anunciar a pontuação
        render("frente");

        //Toca o áudio instruindo os comandos da tela de pontuação
        audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\MostrarPontuacaoEVoltarMenu.mp3"));
        audio.play();
        while (audio.isPlaying())
            ;
        audio.dispose();
    }

    @Override
    public void render(float delta) {/*Método obrigatório de Screen*/}

    //Método que processa a posição do jogador
    public void render(String posicao) {
        switch (posicao) {
            //Caso esteja para frente
            case "frente": {
                //Para e libera qualquer áudio ativo
                if(audio.isPlaying()) {
                    audio.stop();
                    audio.dispose();
                }

                //Toca o áudio avisando que irá dizer a pontuação conquistada
                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\PontuacaoTotalFoi.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;
                audio.dispose();

                //Caso a pontuação seja maior que 1999 (ou seja, x mil e y pontos)
                if (pontuacao > 1999)
                    lerNumero(pontuacao, 4, true);
                else
                    lerNumero(pontuacao, 3, false);

                //Toca o áudio avisando que irá dizer o recorde
                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\SeuRecordeFoi.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;
                audio.dispose();

                //Mesma validação
                if (maiorPontuacao > 1999)
                    lerNumero(maiorPontuacao, 4, true);
                else
                    lerNumero(maiorPontuacao, 3, false);

                break;
            }
            //Caso para direita ou para esquerda
            case "direita":
            case "esquerda": {
                //Para e libera qualquer áudio ativo
                if(audio.isPlaying()) {
                    audio.stop();
                    audio.dispose();
                }

                //Define que o jogador quer ir para o menu
                estado = "menu";
                break;
            }
            default: break;
        }
    }

    //Método para ler o número passado
    private void lerNumero(int valor, int expoente, boolean ehMilhar)
    {
        try {
            //Tenta tocar o áudio respectivo ao número passado
            audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\"+ valor +".mp3"));
            audio.play();
            while (audio.isPlaying())
                ;
            audio.dispose();
        }
        //Caso não consiga, significa que precisa quebrar o número para anunciá-lo
        catch (Exception err)
        {
            //Divide por 10 elevado ao expoente passado e salva o resto
            int resto = valor % (int)Math.pow(10, expoente);
            //Divide por 10 elevado ao expoente passado e salva o quociente
            valor = valor / (int)Math.pow(10, expoente);
            //Multiplica pelo mesmo valor, conseguindo assim a primeira parte do número
            valor = valor * (int)Math.pow(10, expoente);

            //Um exemplo: 1234, expoente 3 -> Resto: 234 -> Quociente: 1 -> valor: 1000

            //Diminui o expoente em 1
            expoente--;

            //Se o valor for maior que 999 e ehMilhar for falso (ou seja, for menor que 2000)
            if(valor > 999 && ehMilhar)
                //Divide o valor por 1000
                //Exemplo: 23456 -> ... -> valor: 20
                valor /= 1000;

            //Se o valor for diferente de 0
            if(valor != 0) {
                //Se for 100
                if (valor == 100)
                    //Como ele não funcionou no try, então deve dizer "cento" (ex: cento e vinte)
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\CENTO.mp3"));
                else
                    //Se não for 100, apenas diz o número
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\" + valor + ".mp3"));

                //Anuncia o valor
                audio.play();
                //Espera o áudio terminar
                while (audio.isPlaying())
                    ;
                //Libera o espaço alocado ao áudio
                audio.dispose();

                //Se o resto for menor que 1000 e ehMilhar for verdadeiro, significa que o número saiu da casa dos milhares
                if (resto < 1000 && ehMilhar) {
                    //ehMilhar vira falso
                    ehMilhar = false;
                    //Diz "mil" (ex: 23456 -> vinte e três MIL e quatrocentos e cinquenta e seis)
                    audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\1000.mp3"));
                    audio.play();
                    while (audio.isPlaying())
                        ;
                    audio.dispose();
                }

                //Diz "e" para ligar os números
                audio = Gdx.audio.newMusic(Gdx.files.internal("Audios\\E.mp3"));
                audio.play();
                while (audio.isPlaying())
                    ;
                audio.dispose();
            }
            //Se o resto for diferente de 0 (ou seja, o número não acabou)
            if(resto != 0) {
                //Chama o próprio método, passando o resto, o novo expoente e ehMilhar
                lerNumero(resto, expoente, ehMilhar);
            }
        }
    }

    //Método para pegar o estado
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
