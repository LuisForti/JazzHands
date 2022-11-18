package com.jazzhands.game.BD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jazzhands.game.BD.Pontuacao;
import android.database.sqlite.SQLiteOpenHelper;

public class Read extends SQLiteOpenHelper{
    private static final String NOME_DB = "RECORDESMUSICA";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_RECORDES = "TABELA_RECORDES";
    private static final String PATH_DB = "/data/user/0/com.jazzhands.game/databases/RECORDES";
    private Context mContext;
    private SQLiteDatabase db;

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

    public Pontuacao getPontuacao(int id) {
        openDB();
        Pontuacao p = new Pontuacao();
        String getPontuacao = "select * from " + TABELA_RECORDES + " WHERE MUSICAID = ?";
        try{
            Cursor c = db.rawQuery(getPontuacao, new String[] {Integer.toString(id)});
            if(c.moveToFirst()) {
                p.setId(c.getInt(0));
                p.setPontos(c.getInt(1));
                c.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }
        return p;
    }

    @SuppressLint("WrongConstant")
    private void openDB(){
        if(!db.isOpen()){
            db = mContext.openOrCreateDatabase(PATH_DB,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }
}

