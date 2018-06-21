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
import com.if1001exemplo1.tccvaseguro.banco.DatabaseHelperEstatistica;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Alessandro on 06/06/2018.
 */
public class MainActivityEstatistica extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelperEstatistica myDB;
    EditText id, numeroAlertas, porcentagemMulheresAlertas, porcentagemHomensAlertas, horarioDeMaisAlertas,
            cidadeDeMaisAlertas, bairroDeMaisAlertas, dateMaisAlertas, modoMaisAlerta, horarioMenosAlertas,
            numerosBOfeitos;
    Button add, save_btn, list_btn, update_btn, delete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_estatistica);
        myDB = new DatabaseHelperEstatistica(this);
        id = (EditText)findViewById(R.id.et_estatistica_id);
        numeroAlertas = (EditText)findViewById(R.id.et_estatistica_numeroAlertas);
        porcentagemMulheresAlertas = (EditText)findViewById(R.id.et_estatistica_porcentagemMulheresAlertas);
        porcentagemHomensAlertas = (EditText)findViewById(R.id.et_estatistica_porcentagemHomensAlertas);
        horarioDeMaisAlertas = (EditText)findViewById(R.id.et_estatistica_horarioDeMaisAlertas);
        cidadeDeMaisAlertas = (EditText)findViewById(R.id.et_estatistica_cidadeDeMaisAlertas);
        bairroDeMaisAlertas = (EditText)findViewById(R.id.et_estatistica_bairroDeMaisAlertas);
        dateMaisAlertas = (EditText)findViewById(R.id.et_estatistica_dateMaisAlertas);
        modoMaisAlerta = (EditText)findViewById(R.id.et_estatistica_modoMaisAlerta);
        horarioMenosAlertas = (EditText)findViewById(R.id.et_estatistica_horarioMenosAlertas);
        numerosBOfeitos = (EditText)findViewById(R.id.et_estatistica_numerosBOfeitos);
        add= (Button) findViewById(R.id.send_btn_estatistica);
        save_btn = (Button)findViewById(R.id.send_btn_estatistica);
        list_btn = (Button)findViewById(R.id.estatistica_btn);
        update_btn = (Button)findViewById(R.id.update_btn_estatistica);
        delete_btn = (Button)findViewById(R.id.delete_btn_estatistica);
      /*  addGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEstatistica.this, EstatisticaActivity.class);
                startActivity(intent);
            }
        });*/
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn_estatistica :
                //converter para int
                String myString = numeroAlertas.getText().toString();
                int cll = Integer.parseInt(myString);
                String myStrin = numerosBOfeitos.getText().toString();
                int clll = Integer.parseInt(myStrin);
                //converter para double
                String text = porcentagemMulheresAlertas.getText().toString();
                double porcentagemMulheresAlertasDouble = Double.parseDouble(text);
                String tex = porcentagemHomensAlertas.getText().toString();
                double porcentagemHomensAlertasDouble = Double.parseDouble(tex);
                //converter para Data
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date datei = null;
                try {
                    datei = df.parse( dateMaisAlertas.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result = myDB.salvaEstatistica(cll,
                        porcentagemMulheresAlertasDouble,
                        porcentagemHomensAlertasDouble,
                        horarioDeMaisAlertas.getText().toString(),
                        cidadeDeMaisAlertas.getText().toString(),
                        bairroDeMaisAlertas.getText().toString(),
                        datei,
                        modoMaisAlerta.getText().toString(),
                        horarioMenosAlertas.getText().toString(),
                        clll
                );
                if (result) {
                    Toast.makeText(MainActivityEstatistica.this, "Success Add Est", Toast.LENGTH_LONG).show();
                    numeroAlertas.setText("");
                    porcentagemMulheresAlertas.setText("");
                    porcentagemHomensAlertas.setText("");
                    horarioDeMaisAlertas.setText("");
                    cidadeDeMaisAlertas.setText("");
                    bairroDeMaisAlertas.setText("");
                    dateMaisAlertas.setText("");
                    modoMaisAlerta.setText("");
                    horarioMenosAlertas.setText("");
                    numerosBOfeitos.setText("");
                }else {
                    Toast.makeText(MainActivityEstatistica.this, "Fails Add Est", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.estatistica_btn :
                Cursor estatistica = myDB.listEstatistica();
                if (estatistica.getCount() == 0) {
                    alert_message("Message", "No data user found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (estatistica.moveToNext()) {
                    buffer.append("Id : " + estatistica.getString(0) + "\n");
                    buffer.append("Numero De Alertas : " + estatistica.getString(1) + "\n");
                    buffer.append("Porcentagem Mulheres Alertas : " + estatistica.getString(2) + "\n");
                    buffer.append("Porcentagem Homens Alertas : " + estatistica.getString(3) + "\n\n\n");
                    buffer.append("Horario De Mais Alertas : " + estatistica.getString(4) + "\n\n\n");
                    buffer.append("Cidade De Mais Alertas : " + estatistica.getString(5) + "\n\n\n");
                    buffer.append("Bairro De Mais Alertas : " + estatistica.getString(6) + "\n\n\n");
                    buffer.append("Date Mais Alertas : " + estatistica.getString(7) + "\n\n\n");
                    buffer.append("Modo Mais Alerta : " + estatistica.getString(8) + "\n\n\n");
                    buffer.append("Horario Menos Alertas : " + estatistica.getString(9) + "\n\n\n");
                    buffer.append("Numeros BO Feitos : " + estatistica.getString(10) + "\n\n\n");
                }
                //show data student
                alert_message("List Estatistica", buffer.toString());
                break;
            case R.id.update_btn_estatistica :
                //converter para int
                String numeroAlertasString = numeroAlertas.getText().toString();
                int numeroAlertasInt = Integer.parseInt(numeroAlertasString);
                String numerosBOfeitosString = numerosBOfeitos.getText().toString();
                int numerosBOfeitosInt = Integer.parseInt(numerosBOfeitosString);
                //converter para double
                String porcentagemMulheresAlertasString = porcentagemMulheresAlertas.getText().toString();
                double porcentagemMulheresAlertasD = Double.parseDouble(porcentagemMulheresAlertasString);
                String porcentagemHomensAlertasString = porcentagemHomensAlertas.getText().toString();
                double porcentagemHomensAlertasD = Double.parseDouble(porcentagemHomensAlertasString);
                //converter para Data
                DateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
                Date dateM = null;
                try {
                    dateM = datef.parse( dateMaisAlertas.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean result1 = myDB.updateEstatistica(id.getText().toString(),
                        numeroAlertasInt,
                        porcentagemMulheresAlertasD,
                        porcentagemHomensAlertasD,
                        horarioDeMaisAlertas.getText().toString(),
                        cidadeDeMaisAlertas.getText().toString(),
                        bairroDeMaisAlertas.getText().toString(),
                        dateM,
                        modoMaisAlerta.getText().toString(),
                        horarioMenosAlertas.getText().toString(),
                        numerosBOfeitosInt);
                if (result1) {
                    Toast.makeText(MainActivityEstatistica.this, "Success update data Est", Toast.LENGTH_LONG).show();
                    numeroAlertas.setText("");
                    porcentagemMulheresAlertas.setText("");
                    porcentagemHomensAlertas.setText("");
                    horarioDeMaisAlertas.setText("");
                    cidadeDeMaisAlertas.setText("");
                    bairroDeMaisAlertas.setText("");
                    dateMaisAlertas.setText("");
                    modoMaisAlerta.setText("");
                    horarioMenosAlertas.setText("");
                    numerosBOfeitos.setText("");
                }else {
                    Toast.makeText(MainActivityEstatistica.this, "Fails update data Est", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.delete_btn_estatistica :
                Integer result2 = myDB.delete_estatistica(id.getText().toString());
                if (result2 > 0) {
                    Toast.makeText(MainActivityEstatistica.this, "Success delete a Est", Toast.LENGTH_LONG).show();
                    numeroAlertas.setText("");
                    porcentagemMulheresAlertas.setText("");
                    porcentagemHomensAlertas.setText("");
                    horarioDeMaisAlertas.setText("");
                    cidadeDeMaisAlertas.setText("");
                    bairroDeMaisAlertas.setText("");
                    dateMaisAlertas.setText("");
                    modoMaisAlerta.setText("");
                    horarioMenosAlertas.setText("");
                    numerosBOfeitos.setText("");
                }else {
                    Toast.makeText(MainActivityEstatistica.this, "Fails delete a Est", Toast.LENGTH_LONG).show();
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
