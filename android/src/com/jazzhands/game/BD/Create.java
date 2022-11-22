package com.jazzhands.game.BD;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Create extends SQLiteOpenHelper {
    //Variáveis com todas as informações do banco de dados
    private static final String NOME_DB = "RECORDESMUSICA";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_RECORDES = "TABELA_RECORDES";
    private static final String PATH_DB = "/data/user/0/com.jazzhands.game/databases/RECORDES";

    //Variáveis para armazenar o contexto e a conexão com o banco de dados
    private Context mContext;
    private SQLiteDatabase db;

    //Ao criar a instância, define como será a conexão com o banco de dados
    public Create(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        this.mContext = context;
        db = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Método para tentar criar a tabela
    public boolean createTable(){
        //Abre o banco de dados
        openDB();

        //Código SQL para criar a tabela caso não exista
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABELA_RECORDES
                + "("
                + "MUSICAID STRING, "
                + "PONTOS INTEGER)";

        try{
            //Tenta criar a tabela
            db.execSQL(createTable);

            //Caso consiga, cria cv que vai armazenar as informações de cada registro
            ContentValues cv = new ContentValues();
            //Loop para criar um registro para cada música
            for(int i = 1; i <= 11; i++) {
                //Define o id como o valor de i
                cv.put("MUSICAID", i);
                //Pontuação 0
                cv.put("PONTOS", 0);
                //Insere o registro na tabela
                db.insert(TABELA_RECORDES, null, cv);
            }
            return true;
        } catch(Exception e){
            //Caso já exista a tabela, imprime o alerta no console
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

