package com.ChiriChat.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.ChiriChat.R;
import com.ChiriChat.model.Mensajes;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by neosistec on 13/05/2014.
 */
public class ListConversation extends Activity {

    private EditText editText;
    private Button buttonSend;
    private ListView lisViewMensajes;

    private myAdapterMensajes adapterMensajes;

    private ArrayList<Mensajes> allMensajes;

    private Activity activity = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_conversation);

        editText = (EditText) findViewById(R.id.text_sent_msg);
        buttonSend = (Button) findViewById(R.id.bt_sent_msg);
        lisViewMensajes = (ListView) findViewById(android.R.id.list);

        //Recupero el nombre del contacto
        Bundle extras = getIntent().getExtras();
        String nombreContact = extras.getString("nombre");
        //Cambiamos el titulo de la actividad
        this.setTitle(nombreContact);

        allMensajes = new ArrayList<Mensajes>();

        Mensajes men = new Mensajes("fdfas",1);

        allMensajes.add(men);

        adapterMensajes = new myAdapterMensajes(activity, allMensajes);

        lisViewMensajes.setAdapter(adapterMensajes);


    }

    public void send(View view){

        Mensajes men = new Mensajes(editText.getText().toString(),1);



        allMensajes.add(men);

        lisViewMensajes.setAdapter(adapterMensajes);

        editText.setText("");
    }
}