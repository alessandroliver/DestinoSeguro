package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProfessorCustomAdapter extends BaseAdapter {

    //
    private final List<Professor> items;
    private final Activity act;

    public ProfessorCustomAdapter(List<Professor> items, Activity act) {
        this.items = items;
        this.act = act;
    }
    // informa qnts itens possui no List
    @Override
    public int getCount() {
        return items.size();
    }
    //item de uma dada posição
    @Override
    public Object getItem(int position)
    {
        return items.get(position);    }
    //dará o id do item, como n temos um atributo id em ItemRSS mantemos retornando 0
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // criar view para cada item
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        //como nossa classe n se trata de uma Act, precisamos receber uma pra pegar a view
        //pegamos o responsável em inflar e chamaremos o método inflate() que criará a View e a retornará para nós
        final View view = act.getLayoutInflater().inflate(R.layout.item_list_professor, parent,false);
        //pega o item de uma dada posição
        final Professor item = items.get(position);
        //pega os valores do título e data de publicação para cada item da lista e preenche os textviews
        TextView nome = (TextView) view.findViewById(R.id.item_nome_professor);
        nome.setText(item.getNome());

        TextView email = (TextView) view.findViewById(R.id.item_email_professor);
        email.setText("E-mail: "+ item.getEmail());

        TextView disciplina = (TextView) view.findViewById(R.id.item_professor_disciplina);
        disciplina.setText("Diciplina: "+item.getDisciplina().getNome());


        return view;
    }



}
