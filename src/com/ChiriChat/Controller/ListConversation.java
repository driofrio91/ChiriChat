package com.ChiriChat.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.ChiriChat.Adapter.myAdapterMensajes;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Mensajes;

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


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_conversation);


        editText = (EditText) findViewById(R.id.text_sent_msg);
        buttonSend = (Button) findViewById(R.id.bt_sent_msg);
        lisViewMensajes = (ListView) findViewById(android.R.id.list);


        //Recupero el nombre del contacto
        Bundle extras = getIntent().getExtras();

        Contactos c ;

        if (extras != null){
            c = getIntent().getParcelableExtra("key");

            //Cambiamos el titulo de la actividad
            this.setTitle(c.getNombre());
            Log.d("ID", c.toString());

        }



        allMensajes = new ArrayList<Mensajes>();

        adapterMensajes = new myAdapterMensajes(this, allMensajes);

        lisViewMensajes.setAdapter(adapterMensajes);
       // setRetainInstance(true);
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }
        Log.d("SAved instance", String.valueOf(savedInstanceState));
        if (savedInstanceState != null){

            allMensajes = savedInstanceState.getParcelableArrayList("list");

            adapterMensajes.notifyDataSetChanged();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

       // = lisViewMensajes.onSaveInstanceState();
        ArrayList<Mensajes> valores = adapterMensajes.getItemMensajes();
        outState.putParcelableArrayList("list",valores);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allMensajes = savedInstanceState.getParcelableArrayList("list");
        Log.d("onREstoreIn", allMensajes.get(allMensajes.size()-1).toString() );

        adapterMensajes.setItemMensajes(allMensajes);
        adapterMensajes.notifyDataSetChanged();
        // setRetainInstance(true);
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_conversation, menu);

        return super.onCreateOptionsMenu(menu);
    }



    public void send(View view){

        String cadena = editText.getText().toString().trim();

       if (!cadena.isEmpty()){

          Mensajes men = new Mensajes(cadena,1);

           allMensajes.add(men);

           adapterMensajes.notifyDataSetChanged();

           lisViewMensajes.setSelection(allMensajes.size()-1);

           editText.setText("");
       }

    }


}