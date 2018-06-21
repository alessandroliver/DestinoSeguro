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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperFeedback;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperGuardioes;
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperUsuario;
import com.if1001exemplo1.tccvaseguro.basicas.BO;
import com.if1001exemplo1.tccvaseguro.basicas.Feedback;
import com.if1001exemplo1.tccvaseguro.basicas.User;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

public class MainActivityFeedback extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelperFeedback myDB;
    DatabaseHelperUsuario myDBuser;
    EditText id, origem, destino, modo, quantidadeAlerta, tempoPercurso, dateFeedback, latDangerousLocation,
            lngDangerousLocation, horarioAlerta, descricaoAlerta, cpfUsu;
    Button add, save_btn, list_btn, update_btn, delete_btn, enviarFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feedback);
        myDB = new DatabaseHelperFeedback(this);
        myDBuser = new DatabaseHelperUsuario(this);
        id = (EditText)findViewById(R.id.et_feedback_id);
        origem = (EditText)findViewById(R.id.et_feedback_origem);
        destino = (EditText)findViewById(R.id.et_feedback_destino);
        modo = (EditText)findViewById(R.id.et_feedback_modo);
        quantidadeAlerta = (EditText)findViewById(R.id.et_feedback_quantidadeAlerta);
        tempoPercurso = (EditText)findViewById(R.id.et_feedback_tempoPercurso);
        dateFeedback = (EditText)findViewById(R.id.et_feedback_dateFeedback);
        latDangerousLocation = (EditText)findViewById(R.id.et_feedback_latDangerousLocation);
        lngDangerousLocation = (EditText)findViewById(R.id.et_feedback_lngDangerousLocation);
        horarioAlerta = (EditText)findViewById(R.id.et_feedback_horarioAlerta);
        descricaoAlerta = (EditText)findViewById(R.id.et_feedback_descricaoAlerta);
        cpfUsu = (EditText)findViewById(R.id.et_feedback_cpfUsu);
        add= (Button) findViewById(R.id.send_btn_feedback);
        save_btn = (Button)findViewById(R.id.send_btn_feedback);
        list_btn = (Button)findViewById(R.id.feedback_btn);
        update_btn = (Button)findViewById(R.id.update_btn_feedback);
        delete_btn = (Button)findViewById(R.id.delete_btn_feedback);
        enviarFeedback = (Button)findViewById(R.id.enviarFeedback_btn_feedback);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityFeedback.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });*/
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        enviarFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_feedback :
                //converter para int
                String myString = quantidadeAlerta.getText().toString();
                int cll = Integer.parseInt(myString);
                //converter para double
                String text = tempoPercurso.getText().toString();
                double tempoPercursoDouble = Double.parseDouble(text);

                String tex = latDangerousLocation.getText().toString();
                double latDangerousLocationDouble = Double.parseDouble(tex);

                String txt = lngDangerousLocation.getText().toString();
                double lngDangerousLocationDouble = Double.parseDouble(txt);

                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( dateFeedback.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                boolean result = myDB.salvaFeedback(origem.getText().toString(),
                        destino.getText().toString(),
                        modo.getText().toString(),
                        cll,
                        tempoPercursoDouble,
                        datei,
                        latDangerousLocationDouble,
                        lngDangerousLocationDouble,
                        horarioAlerta.getText().toString(),
                        descricaoAlerta.getText().toString(),
                        cpfUsu.getText().toString()
                );

                if (result) {
                    Toast.makeText(MainActivityFeedback.this, "Success Add Feedback", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    modo.setText("");
                    quantidadeAlerta.setText("");
                    tempoPercurso.setText("");
                    dateFeedback.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horarioAlerta.setText("");
                    descricaoAlerta.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityFeedback.this, "Fails Add Feedback", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.feedback_btn :
                Cursor feedback = myDB.listFeedback();
                if (feedback.getCount() == 0) {
                    alert_message("Message", "No data user found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();

                while (feedback.moveToNext()) {
                    buffer.append("Id : " + feedback.getString(0) + "\n");
                    buffer.append("Origem : " + feedback.getString(1) + "\n");
                    buffer.append("Destino : " + feedback.getString(2) + "\n");
                    buffer.append("Modo : " + feedback.getString(3) + "\n\n\n");
                    buffer.append("Quantidade Alerta : " + feedback.getString(4) + "\n\n\n");
                    buffer.append("Tempo Percurso : " + feedback.getString(5) + "\n\n\n");
                    buffer.append("Data Feedback : " + feedback.getString(6) + "\n\n\n");
                    buffer.append("Latitude Dangerous Location : " + feedback.getString(7) + "\n\n\n");
                    buffer.append("Longitude Dangerous Location : " + feedback.getString(8) + "\n\n\n");
                    buffer.append("Horário Alerta : " + feedback.getString(9) + "\n\n\n");
                    buffer.append("Descrição Alerta : " + feedback.getString(10) + "\n\n\n");
                    buffer.append("CPF User : " + feedback.getString(11) + "\n\n\n");
                }
                //show data student
                alert_message("List Feedback", buffer.toString());
                break;
            case R.id.update_btn_feedback :

                //converter para int
                String myString1 = quantidadeAlerta.getText().toString();
                int cll1 = Integer.parseInt(myString1);
                //converter para double
                String text1 = tempoPercurso.getText().toString();
                double tempoPercursoDouble1 = Double.parseDouble(text1);

                String tex1 = latDangerousLocation.getText().toString();
                double latDangerousLocationDouble1 = Double.parseDouble(tex1);

                String txt1 = lngDangerousLocation.getText().toString();
                double lngDangerousLocationDouble1 = Double.parseDouble(txt1);

                //converter para Data
                DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
                Date datei1 = null;
                try {
                    datei1 = df1.parse( dateFeedback.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean result1 = myDB.updateFeedback(id.getText().toString(),
                        origem.getText().toString(),
                        destino.getText().toString(),
                        modo.getText().toString(),
                        cll1,
                        tempoPercursoDouble1,
                        datei1,
                        latDangerousLocationDouble1,
                        lngDangerousLocationDouble1,
                        horarioAlerta.getText().toString(),
                        descricaoAlerta.getText().toString(),
                        cpfUsu.getText().toString());
                if (result1) {
                    Toast.makeText(MainActivityFeedback.this, "Success update data Feedback", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    modo.setText("");
                    quantidadeAlerta.setText("");
                    tempoPercurso.setText("");
                    dateFeedback.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horarioAlerta.setText("");
                    descricaoAlerta.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityFeedback.this, "Fails update data Feedback", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_feedback :
                Integer result2 = myDB.delete_feedback(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityFeedback.this, "Success delete a Feedback", Toast.LENGTH_LONG).show();
                    origem.setText("");
                    destino.setText("");
                    modo.setText("");
                    quantidadeAlerta.setText("");
                    tempoPercurso.setText("");
                    dateFeedback.setText("");
                    latDangerousLocation.setText("");
                    lngDangerousLocation.setText("");
                    horarioAlerta.setText("");
                    descricaoAlerta.setText("");
                    cpfUsu.setText("");
                }else {
                    Toast.makeText(MainActivityFeedback.this, "Fails delete a Feedback", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.enviarFeedback_btn_feedback :
                Cursor feed = myDB.listFeedback();
                while (feed.moveToNext()) {
                    if(feed.getString(0).equals(id.getText().toString())){

                        //converter para int
                        String qtdAlerta = feed.getString(4);
                        int qtdAlertaI = Integer.parseInt(qtdAlerta);

                        //converter para int
                        String idString = feed.getString(0);
                        int idInt = Integer.parseInt(idString);
                        //converter para double
                        String tempoS = feed.getString(5);
                        double tempoDo = Double.parseDouble(tempoS);
                        //converter para double
                        String latS = feed.getString(7);
                        double latDo = Double.parseDouble(latS);
                        //converter para double
                        String lngS = feed.getString(8);
                        double lngDo = Double.parseDouble(lngS);
                        //converter para Data
                        DateFormat dateform = new SimpleDateFormat("MM/dd/yyyy");
                        Date dataD = null;
                        try {
                            dataD = dateform.parse(feed.getString(6));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        User usuario = null;
                        Cursor usu = myDBuser.getUsuario(feed.getString(11));
                        DateFormat dateUsu = new SimpleDateFormat("MM/dd/yyyy");
                        Date nasci = null;
                        try {
                            nasci = dateUsu.parse( usu.getString(7).toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //pego os guardiões desse usuário
                        // ele vem como uma lista de nomes dos guardiaos, pelo nome pegamos o guardian

                        List<String> guardians = myDBuser.listGuardianUser(usu.getString(12));
                        DatabaseHelperGuardioes g = new DatabaseHelperGuardioes(this);


                        if (usu.getCount() > 0) {
                            usuario = new User(usu.getInt(0), usu.getString(1), usu.getString(2), usu.getString(3),
                                    usu.getString(4), usu.getString(5), usu.getString(6), nasci, usu.getInt(8),
                                    usu.getString(9), usu.getString(10), usu.getDouble(11), usu.getString(12), usu.getString(13), g.getGuardians(guardians));
                        }

                        Feedback newFeed = new Feedback(idInt, feed.getString(1),feed.getString(2),feed.getString(3)
                                ,qtdAlertaI, tempoDo, dataD, latDo, lngDo, feed.getString(9), feed.getString(10),usuario);

                        try {
                            newFeed.enviarFeedback(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
