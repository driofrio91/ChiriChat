package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import com.ChiriChat.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by neosistec on 30/05/2014.
 */
public class Opciones extends Activity {

    private ListView listview;
    private CheckBox checkBox;
    private String[] idiomas = new String[]{"Español","Ingles"};
    Locale myLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        listview = (ListView) findViewById(R.id.listView_Idiomas);
        checkBox = (CheckBox) findViewById(R.id.checkBox_automatico);

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, idiomas);

        listview.setAdapter(adaptador);

        listview.setEnabled(false);
//        listview.set
    }

    private void setLocal(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, this.getClass());
        startActivity(refresh);

    }
}