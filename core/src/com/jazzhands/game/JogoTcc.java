package com.jazzhands.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class JogoTcc extends ApplicationAdapter {
    SpriteBatch batch;
    BitmapFont fonte;
    int frame = 0;
    double anguloX, anguloY, anguloZ;
    String posicao = "";
    Music musicaTeste;
    int[] batidas = {20,52,82,105,135,167,185,217,247,270,300,330,352,382,415,435,467,497,517,547,580,600,632,662,685,717,747,770,800,832,852,885,915,937,967,990,1020,1050,1072,1102,1135,1155,1187,1217,1237,1267,1300,1320,1352,1382,1405,1437,1467,1490,1520,1552,1572,1605,1635,1657,1687,1710,1740,1770,1792,1822,1855,1875,1907,1937,1957,4717,4747,4770,4800,4830,4852,4882,4915,4935,4967,4997,5017,5047,5080,5100,5132,5162,5185,5217,5247,5270,5300,5332,5352,5385,5415,5437,5467,5490,5520,5550,5572,5602,5635,5655,5687,5717,5737,5767,5800,5820,5852,5882,5905,5937,5967,5990,6020,6052,6072,6105,6135,6157,6187,6210,6240,6270,6292,6322,8962,8995,9015,9047,9077,9097,9127,9160,9180,9212,9242};
    int batidaAtual = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.getData().setScale(8);
        anguloX = anguloY = anguloZ = 0.00;
        musicaTeste = Gdx.audio.newMusic(Gdx.files.internal("Drum-Bass-Cartoon-On-On-ft-Daniel-Levi_.mp3")); // Cartoon - On & On (Lyrics) feat. Daniel Levi
    }

    @Override
    public void render() {
        // Ao começar o programa, a música inicia
        if(frame == 0)
            musicaTeste.play();
        frame += 1;
        //Pega o valor do acelerômetro, em m/s^2, e converte para graus
        anguloX = Gdx.input.getAccelerometerX() * 9.18;
        anguloY = Gdx.input.getAccelerometerY() * 9.18;
        anguloZ = Gdx.input.getAccelerometerZ() * 9.18;
        // A cada 40/60 segundos ele analizará a posição do celular, aproximadamente o intervalo entre as batidas do metrônomo da música
        if (frame == batidas[batidaAtual]) {
            batidaAtual++;
            // Para cima
            if (anguloY > 40) {
                posicao = "cima";
            }
            //Para trás ou para frente
            else if (anguloY < 40) {
                if(anguloX < 40 && anguloX > -40) {
                    if (anguloZ > 0)
                        posicao = "frente";
                    else
                        posicao = "trás";
                }
                else if(anguloX > 40)
                {
                    posicao = "esquerda";
                }
                else
                    posicao = "direita";
            }
        }
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        fonte.draw(batch, posicao, 0, 1200);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
