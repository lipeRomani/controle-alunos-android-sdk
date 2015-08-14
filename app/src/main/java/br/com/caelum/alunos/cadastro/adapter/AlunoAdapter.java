package br.com.caelum.alunos.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.alunos.R;
import br.com.caelum.alunos.cadastro.ListaAlunosActivity;
import br.com.caelum.alunos.cadastro.Model.Aluno;


public class AlunoAdapter extends BaseAdapter {

    private Activity activity;
    private List<Aluno> alunos;

    public AlunoAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;

        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.item, null);
        Aluno aluno = alunos.get(position);

        if(position % 2 == 0){
            row.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
        }

        TextView nome = (TextView) row.findViewById(R.id.nome_linha);
        ImageView imagem = (ImageView) row.findViewById(R.id.foto_linha);

        nome.setText(aluno.getNome());

        Bitmap bitmap;
        if(aluno.getCaminhoFoto() != null){
            bitmap = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
            bitmap = bitmap.createScaledBitmap(bitmap, 100, 100, true);
        }else{
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
        }
        imagem.setImageBitmap(bitmap);

        return row;
    }
}
