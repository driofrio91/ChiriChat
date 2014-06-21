/*
* Copyright (C) 2014 Alejandro Moreno Jimenez | alejandroooo887@gmail.com
*					 Danny Riofrio Jimenez | superdanny.91@gmail.com
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>. *
*/


package com.ChiriChat.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.ChiriChat.R;

import java.util.Locale;


public class Opciones extends Activity {

    private static final String LANGUAGE = "language";
    private static final String ESPANOL = "es";
    private static final String ENGLISH = "en";

    private ListView listview;
    private String[] idiomas = new String[]{"System language","Español","English"};
    private Locale myLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        listview = (ListView) findViewById(R.id.listView_Idiomas);


        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        R.layout.list_view_simple, idiomas);



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
                        setLocal(ESPANOL);
                        break;
                    case 2:
                        setLocal(ENGLISH);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,ListChats.class);
        startActivity(intent);
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que cambiara el leguaje actual de la app
     * @param lang
     */
    private void setLocal(String lang) {
        Log.d("LANG",lang);
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
