package com.jazzhands.game.BD;

public class Pontuacao {

    Integer musicaId;
    Integer pontos;

    public Pontuacao() {
        musicaId = 1;
    }

    public Integer getId() {
        return musicaId;
    }

    public void setId(int id) {
        this.musicaId = id;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

}