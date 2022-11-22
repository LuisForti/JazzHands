package com.jazzhands.game.BD;

//Classe para armazenar os dados do banco de dados
public class Pontuacao {
    //Informações de cada pontuação
    private Integer musicaId;
    private Integer pontos;

    //Construtor
    public Pontuacao() {
        musicaId = 1;
    }

    //Métodos para manipular as informações
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