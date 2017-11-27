package com.if1001exemplo1.tccvaseguro;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText emailet,senhaet;
    private Button login,naosoucadastrado;
    private String email;
    private String senha;

    @Override
    //carregando layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //recuperando os componentes do layout para manipulá-los
        login = (Button) findViewById(R.id.bt_login_login);
        naosoucadastrado = (Button) findViewById(R.id.bt_login_nao_sou_cadastrado);
        emailet = (EditText) findViewById(R.id.et_login_email);
        senhaet = (EditText) findViewById(R.id.et_login_senha);

        //ação quando aperta o botão
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resgatando as informações do CAActivity através do sharedPreferences
                //chave e valor default
                email = emailet.getText().toString();
                senha = senhaet.getText().toString();

                List<Usuario> list = null;


                UsuarioDBController usuarioDBController = new UsuarioDBController(getApplication());
                try {
                    list = usuarioDBController.getAllUsuario();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (list == null){

                } else {
                    for (int i=0; i<list.size(); i++){
                        if (list.get(i).getEmail().equals(email)){
                            if (list.get(i).getSenha().equals(senha)){
                                Intent intent = new Intent(getApplication(),MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplication(), "Senha invalida", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplication(), "Email invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

        //ação quando aperta o botão
        naosoucadastrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),UsuarioActivity.class);
                startActivity(intent);
            }
        });


    }

}

