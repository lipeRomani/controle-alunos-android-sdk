package br.com.caelum.alunos.cadastro.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felipearomani on 18/07/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE = "cadastro";

    private static String TABELA_ALUNO = "alunos";

    public DBHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlAluno = "CREATE TABLE " + TABELA_ALUNO + " ("
                + "id INTEGER PRIMARY KEY,"
                + "nome TEXT UNIQUE NOT NULL,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "site TEXT,"
                + "nota REAL,"
                + "caminho_foto TEXT"
                + ");";

        db.execSQL(sqlAluno);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABELA_ALUNO + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
