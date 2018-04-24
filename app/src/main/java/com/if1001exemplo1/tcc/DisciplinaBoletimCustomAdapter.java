package com.if1001exemplo1.tcc;

/**
 * Created by Alessandro on 13/06/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DisciplinaBoletimCustomAdapter extends BaseAdapter {

    //
    private final List<Disciplina> items;
    private final Activity act;

    public DisciplinaBoletimCustomAdapter(List<Disciplina> items, Activity act) {
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
        final View view = act.getLayoutInflater().inflate(R.layout.item_boletim_list, parent,false);
        //pega o item de uma dada posição
        final Disciplina item = items.get(position);
        //pega os valores do título e data de publicação para cada item da lista e preenche os textviews
        TextView nome = (TextView) view.findViewById(R.id.tv_item_nome_disciplina);
        double m = (item.getNota1() + item.getNota2() +item.getNota3() +item.getNota4())/4;
        nome.setText(item.getNome());

        TextView media = (TextView) view.findViewById(R.id.tv_item_media_disciplina);
        media.setText(""+m + "");



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setBackgroundColor(Color.parseColor("#dddddd"));
                Intent intent = new Intent(act, VisualizarNotaDisciplinaActivity.class );
                intent.putExtra("nomeDisciplinaBoletim", item.getNome());
                intent.putExtra("cpfAlunoDiciplinaBoletim", BoletimActivity.cpf);
                act.startActivity(intent);



            }
        });


        return view;
    }





}
