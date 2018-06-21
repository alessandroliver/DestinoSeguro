package com.if1001exemplo1.tccvaseguro.old;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;

import com.if1001exemplo1.tccvaseguro.R;

public class PreferencesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Bundle extras = getIntent().getExtras();

    }

    public static class PreferencesFragment extends PreferenceFragment {


        private SharedPreferences.OnSharedPreferenceChangeListener mListener;
        private Preference mensagemOkPreference, mensagemSOSPreference,
                ativarLPPreference, listPreference, timeAlarm, timeEmail, apagarHistorico, emailToPreference;
        private SharedPreferences sharedPrefCheck;
        private SharedPreferences.Editor edit;


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(com.if1001exemplo1.tccvaseguro.R.xml.preferences);
            SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES
                    , Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();

            sharedPrefCheck = getActivity().getSharedPreferences(Constants.CHECK, Context.MODE_PRIVATE);
            edit = sharedPrefCheck.edit();

            //Pega as preferências e seta com o sumário
            emailToPreference = (Preference) getPreferenceManager().findPreference(Constants.EMAIL_TO);
            emailToPreference.setSummary(sharedPref.getString("emailToPreference", "alessandrorodrigues270@gmail.com"));

            mensagemOkPreference = (Preference) getPreferenceManager()
                    .findPreference(Constants.MENSAGEM_Ok);
            mensagemOkPreference.setSummary(sharedPref.getString("msgOkPreference", "Estou bem!"));

            mensagemSOSPreference = (Preference) getPreferenceManager()
                    .findPreference(Constants.MENSAGEM_SOS);
            mensagemSOSPreference.setSummary(sharedPref.getString("msgSOSPreference", "SOS"));

            ativarLPPreference = (Preference) getPreferenceManager()
                    .findPreference(Constants.ATIVAR_LOCAIS_PERIGOSO);

            listPreference = (Preference) getPreferenceManager()
                    .findPreference(Constants.LIST_TRANSPORTE);
            listPreference.setSummary(sharedPref.getString("listTransportPreference", "walking"));

            timeAlarm = (Preference) getPreferenceManager()
                    .findPreference(Constants.TEMPO_ENVIO_ALERTAS);
            timeAlarm.setSummary("Está programado para: " + sharedPref.getInt("timeAlertPreference", 40) + " segundos");

            timeEmail = (Preference) getPreferenceManager()
                    .findPreference(Constants.TEMPO_ENVIO_EMAILS);
            timeEmail.setSummary("Está programado para: " + sharedPref.getInt("timeSendPreference", 40) + " segundos");


            apagarHistorico = (Preference) getPreferenceManager()
                    .findPreference(Constants.APAGAR_HISTORICO);
            apagarHistorico.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new Alert(getActivity()).enableAlertPreference();
                    return false;
                }
            });


            // Define um listener para atualizar caso haja modificação
            mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    if (key.equals(Constants.MENSAGEM_Ok)) {
                        Preference msgOk = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        msgOk.setSummary(sharedPreferences.getString(key, ""));
                        editor.putString("msgOkPreference", msgOk.getSummary().toString());
                        editor.apply();
                    } else if (key.equals(Constants.EMAIL_TO)) {
                        Preference msgSos = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        msgSos.setSummary(sharedPreferences.getString(key, ""));
                        editor.putString("emailToPreference", msgSos.getSummary().toString());
                        editor.apply();
                    } else if (key.equals(Constants.MENSAGEM_SOS)) {
                        Preference msgSos = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        msgSos.setSummary(sharedPreferences.getString(key, ""));
                        editor.putString("msgSOSPreference", msgSos.getSummary().toString());
                        editor.apply();
                    } else if (key.equals(Constants.ATIVAR_LOCAIS_PERIGOSO)) {
                        SwitchPreference atvPerigosos = (SwitchPreference) findPreference(key);
                        // Set summary to be the user-description for the selected value

                        if (!(atvPerigosos.isChecked())) {
                            new Maps().removeDangerousLocations(getActivity(), Maps.mMap);
                        }
                        if (atvPerigosos.isChecked()) {
                            new Maps().markerDangerousLocations(getActivity(), Maps.mMap);
                        }
                        editor.putBoolean("ativarLPPreference", atvPerigosos.isChecked());
                        editor.apply();
                    } else if (key.equals(Constants.TEMPO_ENVIO_ALERTAS)) {
                        Preference timeAlert = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        String value = "Está programado para: " + sharedPreferences.getInt(key, 40) + " segundos";
                        timeAlert.setSummary(value);
                        editor.putInt("timeAlertPreference", sharedPreferences.getInt(key, 40));
                        editor.apply();
                    } else if (key.equals(Constants.TEMPO_ENVIO_EMAILS)) {
                        Preference timeEmail = findPreference(key);
                        // Set summary to be the user-description for the selected value
                        String value = "Está programado para: " + sharedPreferences.getInt(key, 40) + " segundos";
                        timeEmail.setSummary(value);
                        editor.putInt("timeSendPreference", sharedPreferences.getInt(key, 40));
                        editor.apply();
                    } else if (key.equals(Constants.LIST_TRANSPORTE)) {
                        Preference transpMode = findPreference(key);
                        transpMode.setSummary(sharedPreferences.getString(key, "walking"));
                        if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
                            if (!(sharedPreferences.getString("listTransportPreference", "walking").equals(sharedPreferences.getString(key, "walking")))) {
                                edit.putBoolean(Constants.CHECK_ROUTE, true);
                                edit.apply();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("destinationName", Maps.destAdress);
                                intent.putExtra("latOrigenExtra", Maps.latOri);
                                intent.putExtra("longOrigenExtra", Maps.longOri);
                                intent.putExtra("mode", Maps.mode);
                                startActivity(intent);

                            }

                        }
                        editor.putString("listTransportPreference", transpMode.getSummary().toString());
                        editor.apply();
                    }
                }
            };

            // Pega objeto SharedPreferences gerenciado pelo PreferenceManager para este Fragmento
            SharedPreferences prefs = getPreferenceManager()
                    .getSharedPreferences();

            // Registra listener no objeto SharedPreferences
            prefs.registerOnSharedPreferenceChangeListener(mListener);


        }

    }
}




