package br.com.caelum.alunos.cadastro.Helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import br.com.caelum.alunos.R;
import br.com.caelum.alunos.cadastro.FormularioActivity;
import br.com.caelum.alunos.cadastro.Model.Aluno;

/**
 * Created by felipearomani on 18/07/15.
 */
public class FormularioAlunoHelper {


    private final EditText nomeView;
    private final EditText enderecoView;
    private final EditText telefoneView;
    private final EditText siteView;
    private final SeekBar notaView;
    private final ImageView fotoView;
    private Aluno aluno;

    public FormularioAlunoHelper(FormularioActivity activity) {

        nomeView = (EditText) activity.findViewById(R.id.nome);
        enderecoView = (EditText) activity.findViewById(R.id.endereco);
        telefoneView = (EditText) activity.findViewById(R.id.telefone);
        siteView = (EditText) activity.findViewById(R.id.site);
        notaView = (SeekBar) activity.findViewById(R.id.nota);
        fotoView = (ImageView) activity.findViewById(R.id.foto);

        aluno = new Aluno();
    }

    public Aluno getAlunoPopulado(){

        aluno.setNome(nomeView.getText().toString());
        aluno.setEndereco(enderecoView.getText().toString());
        aluno.setSite(siteView.getText().toString());
        aluno.setTelefone(telefoneView.getText().toString());
        aluno.setNota(Double.valueOf(notaView.getProgress()));
        return aluno;
    }

    public void populaFormComAluno(Aluno alunoToEdit) {
        nomeView.setText(alunoToEdit.getNome());
        enderecoView.setText(alunoToEdit.getEndereco());
        siteView.setText(alunoToEdit.getSite());
        telefoneView.setText(alunoToEdit.getTelefone());
        notaView.setProgress(alunoToEdit.getNota().intValue());


        if(alunoToEdit.getCaminhoFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(alunoToEdit.getCaminhoFoto());
            Bitmap scaledBitmap = bitmap.createScaledBitmap(bitmap, 100, 100, true);
            fotoView.setImageBitmap(scaledBitmap);
        }

    }
}
