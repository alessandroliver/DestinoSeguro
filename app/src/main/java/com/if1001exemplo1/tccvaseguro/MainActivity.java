package com.if1001exemplo1.tccvaseguro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.if1001exemplo1.tccvaseguro.SendSMS.getSelectedContacts;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Runnable {

    private FragmentManager fragmentManager;
    private Bundle state;
    private TextView setDestionation;

    //variáveis para o relatório

    public static boolean emitir_alerta = false;
    public static DangerousLocations localizacao_alerta = new DangerousLocations(0,0);
    public static String horario_do_alerta = "00:00";
    public static String pessoas_receberam_alerta = "";
    public static Date data_do_alerta = new Date(System.currentTimeMillis());;


    //widgets
    private ImageView reload1, reload2, r1, r2, walking, driving, bicycling, destinationPlace,close;
    private TextView reloadText1, reloadText2;
    public static TextView alarmText;
    public static FrameLayout alarmFrame;
    private  List<String> locations;
    private FrameLayout select1, select2, select3, destinationFrame, routeFrame;

    //sharedPreferences e seus editores
    SharedPreferences sharedPrefCheck;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    SharedPreferences.Editor editor;

    private GoogleApiClient playServices;
    public static android.location.Location loc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.state=savedInstanceState;
            onReady();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        //se sair da tela, cancela a rota
        editor.putBoolean(Constants.CHECK_ROUTE, false);
        editor.apply();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //cancelar alarme
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)){
            //enviar relatório pra o usuário saber o que aconteceu durante a rota
            Relatorio relatorio = new Relatorio(Maps.origem, Maps.destino, Maps.tempo_saida, Maps.tempo_chegada,MainActivity.emitir_alerta, MainActivity.localizacao_alerta, MainActivity.horario_do_alerta, MainActivity.pessoas_receberam_alerta, MainActivity.data_do_alerta);
            new SendMail().sendRelatorio(this, new MainActivity(), new LatLng(Maps.latOri, Maps.longOri), relatorio);

            //se houver rota,cancela trajeto e cancela alarme(se houver)
            editor.putBoolean(Constants.CHECK_ROUTE, false);
            editor.apply();

            if(getIntent().getExtras() != null){
                getIntent().removeExtra("destinationName");
                getIntent().removeExtra("latOrigenExtra");
                getIntent().removeExtra("longOrigenExtra");
                getIntent().removeExtra("mode");
                Maps.mode = "";
                Maps.destAdress = "";

            }
            //'reinicia' app
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else {
            //se não houver rota, fecha cancelando alarme se houver
            editor.putBoolean(Constants.CHECK_ROUTE, false);
            editor.apply();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //esse é o menu que já vem implementado pelo android
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //opções do menu, por enquanto com esses elementos

        if (id == R.id.nav_preferências) {
            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivity(intent);
        } if(id == R.id.nav_contatos){
            Intent intent = new Intent(MainActivity.this, CardViewPessoaClickActivity.class);
            startActivity(intent);
        } if(id == R.id.nav_logout){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onReady() {

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //já vem quando a gnt seleciona um projeto com menu padrão
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //sharedPreferences
        //esse é pra ver se há rota
        sharedPrefCheck = getSharedPreferences(Constants.CHECK, Context.MODE_PRIVATE);
        editor = sharedPrefCheck.edit();
        //referente as da preferences Activity
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);
        edt = sharedPreferences.edit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //inicializa os componentes da tela pegando do layout
        setDestionation = (TextView) findViewById(R.id.iv_set_destination);
        reload1 = (ImageView) findViewById(R.id.reload_place1);
        reload2 = (ImageView) findViewById(R.id.reload_place2);
        r1 = (ImageView) findViewById(R.id.reload_place_im1);
        r2 = (ImageView) findViewById(R.id.reload_place_im2);
        reloadText1 = (TextView) findViewById(R.id.reload_place1_txt);
        reloadText2 = (TextView) findViewById(R.id.reload_place2_txt);
        select1 = (FrameLayout) findViewById(R.id.triangle1);
        select2 = (FrameLayout) findViewById(R.id.triangle2);
        select3 = (FrameLayout) findViewById(R.id.triangle3);
        close = (ImageView) findViewById(R.id.cancel_route);
        alarmFrame = (FrameLayout) findViewById(R.id.alarm_act);
        routeFrame = (FrameLayout) findViewById(R.id.route_frame);
        alarmText = (TextView) findViewById(R.id.alarm_txt);
        walking = (ImageView) findViewById(R.id.walking_mode);
        driving = (ImageView) findViewById(R.id.driving_mode);
        bicycling = (ImageView) findViewById(R.id.bicycling_mode);
        destinationFrame = (FrameLayout) findViewById(R.id.frame_destination);
        destinationPlace = (ImageView) findViewById(R.id.place_iv_destination);
        startFragment();


        //começar vendo qual foi o último modo de trajeto escolhido pelo usuário
        if ((sharedPreferences.getString("listTransportPreference", "walking").equals("walking"))) {
            setVisibilityMode("walking");

        } else if ((sharedPreferences.getString("listTransportPreference", "walking").equals("driving"))) {
            setVisibilityMode("driving");

        } else {
            setVisibilityMode("bicycling");


        }

        //checa se foi solicitado uma rota
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            setVisibilityWithRoute();

        } else {
            setDestionation.setVisibility(View.VISIBLE);
            destinationFrame.setVisibility(View.VISIBLE);
            destinationPlace.setVisibility(View.VISIBLE);
            locations = new LocationDatasController(this).getLastDestination();

            //os reloads só aparecem se forem feitos pelo menos 2 trajetos
            if (locations.size() > 1) {
                reload1.setVisibility(View.VISIBLE);
                reload2.setVisibility(View.VISIBLE);
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                reloadText2.setVisibility(View.VISIBLE);
                reloadText1.setVisibility(View.VISIBLE);

                reloadText1.setText(locations.get(locations.size() - 1));
                reloadText2.setText(locations.get(locations.size() - 2));
            }
        }

        // selecionar destino
        setDestionation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlaceAutoCompleteActivity.class);
                intent.putExtra("latOrigenExtra", Maps.latOri);
                intent.putExtra("longOrigenExtra", Maps.longOri);
                intent.putExtra("adressOrigenExtra", Maps.addressOrigen);
                startActivity(intent);

            }
        });

        //emitir alarme
        alarmFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //variáveis para o relatório

                emitir_alerta= true;
                localizacao_alerta = new DangerousLocations(Maps.latOri, Maps.longOri);

                ArrayList<Guardiao> contacts = getSelectedContacts(getApplication());

                String contatosParaEnviar = "";
                if (contacts.size() > 0) {
                    for (int i = 0; i < contacts.size(); i++) {
                        pessoas_receberam_alerta += contacts.get(i).getNome();
                    }
                }
                //hora
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                Date hora = Calendar.getInstance().getTime();
                horario_do_alerta = sd.format(hora);
                data_do_alerta =new Date(System.currentTimeMillis());

                SharedPreferences sharedPref = getSharedPreferences(Constants.PREFERENCES
                        , Context.MODE_PRIVATE);
                alarmFrame.setVisibility(View.INVISIBLE);
                alarmText.setVisibility(View.INVISIBLE);
                //habiita o alerta
                new Alert(MainActivity.this).enableAlert(sharedPref.getInt("timeAlertPreference", 40));
                //salva o local perigoso
                new LocationDatasController(MainActivity.this).dangerousLocationsInsert(new DangerousLocations(Maps.latOri, Maps.longOri));
            }
        });

        //caso seja selecionado a opção à pé
        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibilityMode("walking");
                if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
                    if (!(sharedPreferences.getString("listTransportPreference", "walking").equals("walking"))) {
                        new Maps().getRoute(Maps.destAdress, Maps.mMap, MainActivity.this);

                    }

                }
                edt.putString("listTransportPreference", "walking");
                edt.apply();


            }
        });

        //caso o usuário vá dirigindo
        //caso o usuário vá dirigindo
        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibilityMode("driving");
                if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
                    if (!(sharedPreferences.getString("listTransportPreference", "walking").equals("driving"))) {
                        new Maps().getRoute(Maps.destAdress, Maps.mMap, MainActivity.this);
                    }
                }
                edt.putString("listTransportPreference", "driving");
                edt.apply();

            }
        });
        //caso vá pedalando
        bicycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibilityMode("bicycling");

                if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
                    if (!(sharedPreferences.getString("listTransportPreference", "walking").equals("bicycling"))) {
                        new Maps().getRoute(Maps.destAdress, Maps.mMap, MainActivity.this);
                    }
                }
                edt.putString("listTransportPreference", "bicycling");
                edt.apply();

            }
        });

        //cancelar a rota
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enviar relatório pra o usuário saber o que aconteceu durante a rota
                Relatorio relatorio = new Relatorio(Maps.origem, Maps.destino, Maps.tempo_saida, Maps.tempo_chegada,MainActivity.emitir_alerta, MainActivity.localizacao_alerta, MainActivity.horario_do_alerta, MainActivity.pessoas_receberam_alerta, MainActivity.data_do_alerta);
                new SendMail().sendRelatorio(getApplication(), new MainActivity(), new LatLng(Maps.latOri, Maps.longOri), relatorio);

                editor.putBoolean(Constants.CHECK_ROUTE, false);
                editor.apply();

                if (getIntent().getExtras() != null) {
                    getIntent().removeExtra("destinationName");
                    getIntent().removeExtra("latOrigenExtra");
                    getIntent().removeExtra("longOrigenExtra");
                    getIntent().removeExtra("mode");
                    Maps.mode = "";
                    Maps.destAdress = "";

                }
                //tudo isso pra reiniciar o APP
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        run();

    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void run() {
        //obtendo localização por fused location
        //loc = LocationServices.FusedLocationApi.getLastLocation(playServices);

        reload1 = (ImageView) findViewById(R.id.reload_place1);
        reload2 = (ImageView) findViewById(R.id.reload_place2);

        //escolher o reload place 1
        reload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //seta pra true pra dizer que há rota
                editor.putBoolean(Constants.CHECK_ROUTE, true);
                //aplica a atualização
                editor.apply();
                setVisibilityWithRoute();
                 new Maps().getRoute(locations.get(locations.size()-1), Maps.mMap, MainActivity.this);
            }
        });

        reload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(Constants.CHECK_ROUTE, true);
                editor.apply();
                setVisibilityWithRoute();
                new Maps().getRoute(locations.get(locations.size()-2), Maps.mMap, MainActivity.this);
            }
        });

    }

    //iniciando o mapa

    public void startFragment(){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new Maps(), "ProviderFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

//o que fica visível quando tem rota
    public void setVisibilityWithRoute(){
        routeFrame.setVisibility(View.VISIBLE);
        alarmFrame.setVisibility(View.VISIBLE);
        alarmText.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);

        destinationFrame.setVisibility(View.INVISIBLE);
        destinationPlace.setVisibility(View.INVISIBLE);
        setDestionation.setVisibility(View.INVISIBLE);
        reload1.setVisibility(View.INVISIBLE);
        reload2.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);
        reloadText2.setVisibility(View.INVISIBLE);
        reloadText1.setVisibility(View.INVISIBLE);

    }

    //isso só serve pra pôr aquela seta debaixo das imagens superiores
    public void setVisibilityMode(String mode){
        switch (mode){
            case "walking":
                select1.setVisibility(View.VISIBLE);
                select2.setVisibility(View.INVISIBLE);
                select3.setVisibility(View.INVISIBLE);
                break;
            case "driving":
                select1.setVisibility(View.INVISIBLE);
                select2.setVisibility(View.VISIBLE);
                select3.setVisibility(View.INVISIBLE);
                break;
            case "bicycling":
                select1.setVisibility(View.INVISIBLE);
                select2.setVisibility(View.INVISIBLE);
                select3.setVisibility(View.VISIBLE);
                break;
            default:
                Log.i("Main", "Modo não válido");
        }
    }

    public static void setVisibilityAlarm(){
        alarmFrame.setVisibility(View.VISIBLE);
        alarmText.setVisibility(View.VISIBLE);
    }

}



