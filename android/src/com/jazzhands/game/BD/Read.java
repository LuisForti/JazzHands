package com.jazzhands.game.BD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jazzhands.game.BD.Pontuacao;
import android.database.sqlite.SQLiteOpenHelper;

public class Read extends SQLiteOpenHelper{
    //Variáveis com todas as informações do banco de dados
    private static final String NOME_DB = "RECORDESMUSICA";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_RECORDES = "TABELA_RECORDES";
    private static final String PATH_DB = "/data/user/0/com.jazzhands.game/databases/RECORDES";

    //Variáveis para armazenar o contexto e a conexão com o banco de dados
    private Context mContext;
    private SQLiteDatabase db;

    //Ao criar a instância, define como será a conexão com o banco de dados
    public Read(Context context) {
        super(context, NOME_DB, null,VERSAO_DB);
        this.mContext = context;
        db = getReadableDatabase(); //o método indica que será lido do db
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
    }

    //Método para pegar o recorde do jogador
    public Pontuacao getPontuacao(int id) {
        //Abre o banco de dados
        openDB();
        //Cria uma nova instância de Pontuacao
        Pontuacao p = new Pontuacao();

        //Código SQL para pegar o recorde da música com o id passado (o "?" será substituído pelo id passado no método)
        String getPontuacao = "SELECT * FROM " + TABELA_RECORDES + " WHERE MUSICAID = ?";
        try{
            //Tenta pegar o registro do id passado
            Cursor c = db.rawQuery(getPontuacao, new String[] {Integer.toString(id)});
            //Move o cursor para o primeiro (e único) registro
            if(c.moveToFirst()) {
                //Salva o id da música e a pontuação do registro
                p.setId(c.getInt(0));
                p.setPontos(c.getInt(1));
            }
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            db.close(); //fecha o banco independente de ter conseguido executar ou não
        }
        //Retorna a pontuação
        return p;
    }

    //Método para abrir o banco de dados
    @SuppressLint("WrongConstant")
    private void openDB(){
        //Se não tiver uma conexão preexisnte
        if(!db.isOpen()){
            //Conecta
            db = mContext.openOrCreateDatabase(PATH_DB,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }
}

