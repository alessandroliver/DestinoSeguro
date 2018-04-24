package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//adapters vão ser usados para adaptar os objetos para uma view (que será colocado em uma lista de view
//nesse caso, adaptar cada aluno da lista de aluno para um listview
//O custom vem de customizar, porque queremos customizar essas views para exibir mais informações
public class AlunoCustomAdapter extends BaseAdapter {

    //
    private final List<Aluno> items;
    private final Activity act;

    public AlunoCustomAdapter(List<Aluno> items, Activity act) {
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
    //dará o id do item, como n temos um atributo id em Aluno mantemos retornando 0
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // criar view para cada aluno
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        //como nossa classe n se trata de uma Act, precisamos receber uma pra pegar a view
        //pegamos o responsável em inflar e chamaremos o método inflate() que criará a View e a retornará para nós
        //item_list_aluno serve para poder dizer o que a view vai ter
        final View view = act.getLayoutInflater().inflate(R.layout.item_list_aluno, parent,false);
        //pega o item de uma dada posição
        final Aluno item = items.get(position);
        //pega os valores do título e data de publicação para cada item da lista e preenche os textviews
        TextView nome = (TextView) view.findViewById(R.id.item_nome_aluno);
        nome.setText(item.getNome());

        TextView email = (TextView) view.findViewById(R.id.item_aluno_email);
        email.setText("E-mail: " + item.getEmail());

        TextView turma = (TextView) view.findViewById(R.id.item_aluno_turma);
        turma.setText("Turma: " + item.getTurma());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(ListAlunoActivity.acao != null) {
                if (ListAlunoActivity.acao.equals("boletim")) {
                    Intent intent = new Intent(act, SenhaAcessoBoletimActivity.class);
                    intent.putExtra("cpfAlunoDisciplinaExtra", item.getCpf());
                    act.startActivity(intent);
                } else {
                    Intent intent = new Intent(act, ListDisciplinaAlunoActivity.class);
                    intent.putExtra("cpfAlunoDisciplinaExtra", item.getCpf());
                    act.startActivity(intent);
                }
            }


            }
        });


        return view;
    }



}
