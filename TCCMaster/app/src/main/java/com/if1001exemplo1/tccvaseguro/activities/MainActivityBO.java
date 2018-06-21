package com.if1001exemplo1.tccvaseguro.activities;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.if1001exemplo1.tccvaseguro.R;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperBO;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperGuardioes;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperUsuario;
import com.if1001exemplo1.tccvaseguro.basicas.BO;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

public class MainActivityBO extends AppCompatActivity implements View.OnClickListener {
    //Declração de variáveis
    DatabaseHelperBO myDB;
    DatabaseHelperUsuario myDBuser;
    EditText id, tipoOcorrencia, rua, cep, bairro, uf, dataOcorrencia, descricaoAcontecido,
            descricaoPessoasEnvolvidas, quantidadeCoisasRoubadas, cpfUsu;
    Button add, save_btn, list_btn, update_btn, delete_btn, enviarBO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Inicializando
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bo);
        myDB = new DatabaseHelperBO(this);
        myDBuser = new DatabaseHelperUsuario(this);
        id = (EditText)findViewById(R.id.et_bo_id);
        tipoOcorrencia = (EditText)findViewById(R.id.et_bo_tipoOcorrencia);
        rua = (EditText)findViewById(R.id.et_bo_rua);
        cep = (EditText)findViewById(R.id.et_bo_cep);
        bairro = (EditText)findViewById(R.id.et_bo_bairro);
        uf = (EditText)findViewById(R.id.et_bo_uf);
        dataOcorrencia = (EditText)findViewById(R.id.et_bo_dataOcorrencia);
        descricaoAcontecido = (EditText)findViewById(R.id.et_bo_descricaoAcontecido);
        descricaoPessoasEnvolvidas = (EditText)findViewById(R.id.et_bo_descricaoPessoasEnvolvidas);
        quantidadeCoisasRoubadas = (EditText)findViewById(R.id.et_bo_quantidadeCoisasRoubadas);
        cpfUsu = (EditText)findViewById(R.id.et_bo_cpfUsu);

        //botões
        add= (Button) findViewById(R.id.send_btn_bo);
        save_btn = (Button)findViewById(R.id.send_btn_bo);
        list_btn = (Button)findViewById(R.id.bo_btn);
        update_btn = (Button)findViewById(R.id.update_btn_bo);
        delete_btn = (Button)findViewById(R.id.delete_btn_bo);
        enviarBO = (Button)findViewById(R.id.enviarBO_btn_bo);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityBO.this, BOActivity.class);
                startActivity(intent);
            }
        });*/
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        enviarBO.setOnClickListener(this);
    }

    //geração o clique dos botões
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_bo :
                //ADICIONAR BO
                //converter para int
                String myString = quantidadeCoisasRoubadas.getText().toString();
                int cll = Integer.parseInt(myString);
                //converter para double
                String text = cep.getText().toString();
                double cepDouble = Double.parseDouble(text);
                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( dataOcorrencia.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //salva no BD as informações
                boolean result = myDB.salvaBO(tipoOcorrencia.getText().toString(),
                        rua.getText().toString(),
                        cepDouble,
                        bairro.getText().toString(),
                        uf.getText().toString(),
                        datei,
                        descricaoAcontecido.getText().toString(),
                        descricaoPessoasEnvolvidas.getText().toString(),
                        cll,
                        cpfUsu.getText().toString()
                );
                if (result) {
                    //Mensagem
                    Toast.makeText(MainActivityBO.this, "Success Add BO", Toast.LENGTH_LONG).show();
                    //LIMPA EDITTEXT
                    tipoOcorrencia.setText("");
                    rua.setText("");
                    cep.setText("");
                    bairro.setText("");
                    uf.setText("");
                    dataOcorrencia.setText("");
                    descricaoAcontecido.setText("");
                    descricaoPessoasEnvolvidas.setText("");
                    quantidadeCoisasRoubadas.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityBO.this, "Fails Add BO", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bo_btn :

                //LISTAR
                Cursor bo = myDB.listBO();
                if (bo.getCount() == 0) {
                    alert_message("Message", "No data BO found");
                    return;
                }
                //append data bo to buffer
                StringBuffer buffer = new StringBuffer();
                while (bo.moveToNext()) {
                    buffer.append("Id : " + bo.getString(0) + "\n");
                    buffer.append("Tipo Ocorrencia : " + bo.getString(1) + "\n");
                    buffer.append("Rua : " + bo.getString(2) + "\n");
                    buffer.append("CEP : " + bo.getString(3) + "\n\n\n");
                    buffer.append("Bairro : " + bo.getString(4) + "\n\n\n");
                    buffer.append("UF : " + bo.getString(5) + "\n\n\n");
                    buffer.append("Data Ocorrencia : " + bo.getString(6) + "\n\n\n");
                    buffer.append("Descricao Acontecido : " + bo.getString(7) + "\n\n\n");
                    buffer.append("Descricao Pessoas Envolvidas : " + bo.getString(8) + "\n\n\n");
                    buffer.append("Quantidade Coisas Roubadas : " + bo.getString(9) + "\n\n\n");
                    buffer.append("CPF User : " + bo.getString(10) + "\n\n\n");
                }
                //show data bo
                alert_message("List BO", buffer.toString());
                break;
            case R.id.update_btn_bo :
                ///ATUALIZA
                //converter para int
                String quantidadeCoisasRoubadasString = quantidadeCoisasRoubadas.getText().toString();
                int quantidadeCoisasRoubadasInt = Integer.parseInt(quantidadeCoisasRoubadasString);
                //converter para double
                String cepString = cep.getText().toString();
                double cepD = Double.parseDouble(cepString);
                //converter para Data
                DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
                Date data = null;
                try {
                    data = datef.parse( dataOcorrencia.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result1 = myDB.updateBO(id.getText().toString(),
                        tipoOcorrencia.getText().toString(),
                        rua.getText().toString(),
                        cepD,
                        bairro.getText().toString(),
                        uf.getText().toString(),
                        data,
                        descricaoAcontecido.getText().toString(),
                        descricaoPessoasEnvolvidas.getText().toString(),
                        quantidadeCoisasRoubadasInt,
                        cpfUsu.getText().toString());
                if (result1) {
                    Toast.makeText(MainActivityBO.this, "Success update data BO", Toast.LENGTH_LONG).show();
                    tipoOcorrencia.setText("");
                    rua.setText("");
                    cep.setText("");
                    bairro.setText("");
                    uf.setText("");
                    dataOcorrencia.setText("");
                    descricaoAcontecido.setText("");
                    descricaoPessoasEnvolvidas.setText("");
                    quantidadeCoisasRoubadas.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityBO.this, "Fails update data BO", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_bo :
                //DELETAR BO
                //chamar o método delete do banco de BO
                Integer result2 = myDB.delete_bo(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityBO.this, "Success delete a BO", Toast.LENGTH_LONG).show();
                    tipoOcorrencia.setText("");
                    rua.setText("");
                    cep.setText("");
                    bairro.setText("");
                    uf.setText("");
                    dataOcorrencia.setText("");
                    descricaoAcontecido.setText("");
                    descricaoPessoasEnvolvidas.setText("");
                    quantidadeCoisasRoubadas.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityBO.this, "Fails delete a BO", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.enviarBO_btn_bo :
                //ENVIAR BO pelo ID
                //lista bos
                Cursor bos = myDB.listBO();

                while (bos.moveToNext()) {
                    //compara id com o ID passado
                    if(bos.getString(0).equals(id.getText().toString())){

                        //converter para int
                        String qtdCoisasRoubadasString = bos.getString(9);
                        int qtdCoisasRoubadasInt = Integer.parseInt(qtdCoisasRoubadasString);

                        //converter para int
                        String idString = bos.getString(0);
                        int idInt = Integer.parseInt(idString);
                        //converter para double
                        String cepS = bos.getString(3);
                        double cepDo = Double.parseDouble(cepS);
                        //converter para Data
                        DateFormat dateform = new SimpleDateFormat("MM/dd/yyyy");
                        Date dataD = null;
                        try {
                            dataD = dateform.parse(bos.getString(6));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // vou no bd de usuário e acho o usuário correspondente ao CPF armazeanado
                        User usuario = null;
                        //recebo o usuário do BD de usuário
                        Cursor usu = myDBuser.getUsuario(bos.getString(10));
                        DateFormat dateUsu = new SimpleDateFormat("MM/dd/yyyy");
                        Date nasci = null;
                        try {
                            if (usu.getCount() > 0) {
                                nasci = dateUsu.parse(usu.getString(7).toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        List<String> guardians = null;
                        DatabaseHelperGuardioes g = new DatabaseHelperGuardioes(this);

                        //pego os guardiões desse usuário
                        // ele vem como uma lista de nomes dos guardiaos, pelo nome pegamos o guardian
                        if (usu.getCount() > 0) {
                            guardians = myDBuser.listGuardianUser(usu.getString(12));
                        }

                        //pegar as informações do cursos e montar o usuário
                        if (usu.getCount() > 0) {
                            usuario = new User(usu.getInt(0), usu.getString(1), usu.getString(2), usu.getString(3),
                                    usu.getString(4), usu.getString(5), usu.getString(6), nasci, usu.getInt(8),
                                    usu.getString(9), usu.getString(10), usu.getDouble(11), usu.getString(12), usu.getString(13), g.getGuardians(guardians));
                        }

                        //cria BO com todas as infomações
                        BO newBO = new BO(idInt, bos.getString(1),bos.getString(2),cepDo,bos.getString(4),
                                bos.getString(5), dataD, bos.getString(7), bos.getString(8), qtdCoisasRoubadasInt,
                                usuario);

                        try {
                            //chamar método de enviar BO
                            newBO.enviarBO(this);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }

                }



                    break;
        }
    }
    public void alert_message(String title, String message) {
        //cria um diálogo com todos os bos
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

