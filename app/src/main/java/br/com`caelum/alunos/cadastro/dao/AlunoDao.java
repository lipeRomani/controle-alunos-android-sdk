package br.com.caelum.alunos.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.alunos.cadastro.Model.Aluno;


public class AlunoDao {

    private static final String TABLE = "alunos";

    private final DBHelper helper;

    public AlunoDao(DBHelper helper) {
        this.helper = helper;
    }


    private ContentValues toContentValuesOf(Aluno aluno) {
        ContentValues cv = new ContentValues();

        cv.put("nome", aluno.getNome());
        cv.put("telefone", aluno.getTelefone());
        cv.put("endereco", aluno.getEndereco());
        cv.put("site", aluno.getSite());
        cv.put("nota", aluno.getNota());
        if (aluno.getCaminhoFoto() != null) {
            cv.put("caminho_foto", aluno.getCaminhoFoto());
        }
        return cv;
    }

    public void inserir(Aluno aluno) {

        ContentValues cv = toContentValuesOf(aluno);
        helper.getWritableDatabase().insert(TABLE, null, cv);
    }

    public List<Aluno> getLista() {
        String sql = "SELECT * FROM " + TABLE + ";";
        List<Aluno> alunos = new ArrayList<>();

        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getInt(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminho_foto")));

            alunos.add(aluno);
        }

        return alunos;
    }

    public void close() {
        helper.close();
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();

        String[] args = {String.valueOf(aluno.getId())};
        writableDatabase.delete(TABLE, "id=?", args);
    }

    public void atualizar(Aluno aluno) {

        SQLiteDatabase writableDatabase = helper.getWritableDatabase();

        ContentValues contentValues = toContentValuesOf(aluno);

        String[] args = {String.valueOf(aluno.getId())};
        writableDatabase.update(TABLE, contentValues, "id=?", args);

    }
}
