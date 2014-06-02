package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.ChiriChat.R;

import java.util.Locale;

/**
 * Created by danny on 30/05/2014.
 */
public class Opciones extends Activity {

    private static final String LANGUAGE = "language";
    private static final String ESPAÑOL = "es";
    private static final String ENGLISH = "en";

    private ListView listview;
    private String[] idiomas = new String[]{"Idioma del sistema","Español","Ingles"};
    Locale myLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        listview = (ListView) findViewById(R.id.listView_Idiomas);


        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_selectable_list_item, idiomas);

        listview.setAdapter(adaptador);

        listview.setEnabled(true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        setLocal(Locale.getDefault().toString());
                        break;
                    case 1:
                        setLocal(ESPAÑOL);
                        break;
                    case 2:
                        setLocal(ENGLISH);
                        break;
                }
            }
        });
    }

    /**
     * Metodo que cambiara el leguaje actual de la app
     * @param lang
     */
    private void setLocal(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, ListChats.class);
        startActivity(refresh);
        setLanguage(lang);
        finish();

    }

    private void setLanguage(String Languaje) {
        SharedPreferences prefs = getSharedPreferences(
                Opciones.class.getSimpleName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE, Languaje);
        editor.commit();
    }
}