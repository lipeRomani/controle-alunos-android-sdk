package br.com.caelum.alunos.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

import br.com.caelum.alunos.R;
import br.com.caelum.alunos.cadastro.Model.Aluno;
import br.com.caelum.alunos.cadastro.adapter.AlunoAdapter;
import br.com.caelum.alunos.cadastro.dao.AlunoDao;
import br.com.caelum.alunos.cadastro.dao.DBHelper;


public class ListaAlunosActivity extends Activity {

    private ListView lista;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);

        lista = (ListView) findViewById(R.id.lista);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("sss", "Clicou no item");

                Aluno alunoToEdit = (Aluno) parent.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intent.putExtra(Extras.ALUNO_SELECIONADO,alunoToEdit);
                startActivity(intent);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ListaAlunosActivity.this, "Nome clicado " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                aluno = (Aluno) parent.getItemAtPosition(position);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo:
               Intent irParaFormulario =  new Intent(this, FormularioActivity.class);
                startActivity(irParaFormulario);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        carregaLista();
        super.onResume();
    }

    private void carregaLista() {
        AlunoDao dao = new AlunoDao(new DBHelper(this));
        List<Aluno> alunos = dao.getLista();
        dao.close();
        AlunoAdapter adapter = new AlunoAdapter(this,alunos);
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //Menu action
        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + aluno.getTelefone()));
                startActivity(intent);
                return false;
            }
        });

        //Menu SMS
        MenuItem sms = menu.add("Enviar SMS");
        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + aluno.getTelefone()));
                intent.putExtra("sms_body", "Olá " + aluno.getNome());
                startActivity(intent);

                return false;
            }
        });

        //Menu Achar no mapa
        MenuItem mapa = menu.add("Achar no mapa");
        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?z=14&q=" + aluno.getEndereco()));
                startActivity(intent);

                return false;
            }
        });

        //Menu para navegar pelo site
        MenuItem site = menu.add("Navegar no site");
        site.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String siteAluno = aluno.getSite();

                if(!siteAluno.startsWith("http://")){
                    siteAluno = "http://" + siteAluno;
                }

                intent.setData(Uri.parse(siteAluno));
                startActivity(intent);

                return false;
            }
        });

        //Deletar Action
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDao dao = new AlunoDao(new DBHelper(ListaAlunosActivity.this));
                dao.deletar(aluno);
                dao.close();
                carregaLista();
                return false;
            }
        });

        menu.add("Enviar E-mail");

    }
}
