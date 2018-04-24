package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DisciplinaAlunoCustomAdapter extends BaseAdapter {

    //
    private final List<Disciplina> items;
    private final Activity act;

    public DisciplinaAlunoCustomAdapter(List<Disciplina> items, Activity act) {
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
        final View view = act.getLayoutInflater().inflate(R.layout.itemlist, parent,false);
        //pega o item de uma dada posição
        final Disciplina item = items.get(position);
        //pega os valores do título e data de publicação para cada item da lista e preenche os textviews
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(item.getNome());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setBackgroundColor(Color.parseColor("#dddddd"));

                //quando ele clica ele irá pra essa activity indicada
                Intent intent = new Intent(act, AcessoAtribuirNotasActivity.class);
                intent.putExtra("extraCPFALuno", ListDisciplinaAlunoActivity.cpf);
                intent.putExtra("extraCodDisAluno",  item.getCodigo());
                intent.putExtra("extraNomeDis", item.getNome());
                act.startActivity(intent);


            }
        });


        return view;
    }





}
