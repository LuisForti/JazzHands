package com.jazzhands.game.BD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Update extends SQLiteOpenHelper{
    //Variáveis com todas as informações do banco de dados
    private static final String NOME_DB = "RECORDESMUSICA";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_RECORDES = "TABELA_RECORDES";
    private static final String PATH_DB = "/data/user/0/com.jazzhands.game/databases/RECORDES";

    //Variáveis para armazenar o contexto e a conexão com o banco de dados
    private Context mContext;
    private SQLiteDatabase db;

    //Ao criar a instância, define como será a conexão com o banco de dados
    public Update(Context context) {
        super(context, NOME_DB, null,VERSAO_DB);
        this.mContext = context;
        db = getWritableDatabase(); //o método indica que será escrito no db
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
    }

    //Método para atualizar o recorde do jogador
    public boolean updatePontuacao(Pontuacao p){
        //Abre o banco de dados
        openDB();
        try {
            //Qual o critério para definir qual registro mudar
            String where = "MUSICAID = ?";
            //Cria cv que vai armazenar a nova pontuação recorde
            ContentValues cv = new ContentValues();
            cv.put("PONTOS",p.getPontos());
            //Atualiza na tabela, passando a nova pontuação, o critério de atualização e a informação de referência do critério
            db.update(TABELA_RECORDES, cv, where, new String[] {Integer.toString(p.getId())});
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            db.close(); //fecha o banco independente de ter conseguido executar ou não
        }
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
