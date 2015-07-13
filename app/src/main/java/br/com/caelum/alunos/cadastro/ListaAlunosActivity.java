package br.com.caelum.alunos.cadastro;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.caelum.alunos.R;

/**
 * Created by felipearomani on 12/07/15.
 */
public class ListaAlunosActivity extends Activity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);

        lista = (ListView) findViewById(R.id.lista);

        final String[] nomes = {"Felipe", "Elisa", "Woody"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomes);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("sss", "Clicou no item");
                Toast.makeText(ListaAlunosActivity.this, "A posição clicada é " + position, Toast.LENGTH_SHORT).show();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListaAlunosActivity.this, "Nome clicado " + parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
