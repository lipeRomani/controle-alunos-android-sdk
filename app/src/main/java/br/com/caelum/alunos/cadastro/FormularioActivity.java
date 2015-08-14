package br.com.caelum.alunos.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.alunos.R;
import br.com.caelum.alunos.cadastro.Helper.FormularioAlunoHelper;
import br.com.caelum.alunos.cadastro.Model.Aluno;
import br.com.caelum.alunos.cadastro.dao.AlunoDao;
import br.com.caelum.alunos.cadastro.dao.DBHelper;

/**
 * Created by felipearomani on 12/07/15.
 */
public class FormularioActivity extends Activity {

    private Button salvarBtn;
    private FormularioAlunoHelper helper;
    private AlunoDao dao;
    private Aluno alunoToEdit;
    private ImageView foto;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        salvarBtn = (Button) findViewById(R.id.salvar);
        foto = (ImageView) findViewById(R.id.foto);

        helper = new FormularioAlunoHelper(this);
        dao = new AlunoDao(new DBHelper(this));

        Intent intent = getIntent();
        alunoToEdit = (Aluno) intent.getSerializableExtra(Extras.ALUNO_SELECIONADO);

        if(alunoToEdit != null){
            helper.populaFormComAluno(alunoToEdit);
            salvarBtn.setText("Atualizar");
            Toast.makeText(this,alunoToEdit + "Testeee",Toast.LENGTH_LONG).show();
        }

        //Listener do botão salvar
        salvarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = helper.getAlunoPopulado();
                if(path != null) aluno.setCaminhoFoto(path);
                if(alunoToEdit != null){
                    aluno.setId(alunoToEdit.getId());
                    dao.atualizar(aluno);
                }else{
                    dao.inserir(aluno);
                }

                dao.close();

                finish();

            }
        });

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                path = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png";
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagem = new File(path);
                Uri uri = Uri.fromFile(imagem);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(irParaCamera,123);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            if(resultCode == RESULT_OK){
                Log.i("aaaa", path);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Bitmap scaledBitmap = bitmap.createScaledBitmap(bitmap, 100, 100, true);
                foto.setImageBitmap(scaledBitmap);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_form_alunos,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()){
            case R.id.lista_alunos:
                Intent intent = new Intent(FormularioActivity.this,ListaAlunosActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}
