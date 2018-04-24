package com.if1001exemplo1.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

public class ListFuncionarioActivity extends Activity {
    private List<Funcionario> funcionarioLista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_funcionario);
        try {
            funcionarioLista = new FuncionarioDBController(this).getAllFunc();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView funcionariolista = (ListView) findViewById(R.id.funcionario_list);
        FuncionarioCustomAdapter funcionarioCustomAdapter = new FuncionarioCustomAdapter(funcionarioLista, this);
        //ArrayAdapter<Funcionario> adapter = new ArrayAdapter<Funcionario>(this, android.R.layout.simple_list_item_1, funcionarioLista);
        funcionariolista.setAdapter(funcionarioCustomAdapter);
    }

}
