package com.if1001exemplo1.tcc;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DisciplinaCustomAdapter extends BaseAdapter {

    //
    private final List<Disciplina> items;
    private final Activity act;

    public DisciplinaCustomAdapter(List<Disciplina> items, Activity act) {
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
                //quando o usuario seleciona uma determinada disciplina
                //essa disciplina será atribuída ao aluno
                view.setBackgroundColor(Color.parseColor("#dddddd"));

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date datei = df.parse(ListDisciplinaMatriculaActivity.di);
                    item.setData_inicio(datei);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Date datei = df.parse(ListDisciplinaMatriculaActivity.df);
                    item.setData_fim(datei);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //associar disciplina ao aluno
                new AlunoDBController(act).insertDisciplinaAluno(item, ListDisciplinaMatriculaActivity.cpf);

            }
        });


        return view;
    }





}
