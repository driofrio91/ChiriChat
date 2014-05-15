package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;

public class ListContacts extends Activity {

    private ListView listContacts;
    private ArrayList<Contactos> allContactos = new ArrayList<Contactos>();

    private myAdapterContacts adapterContacts;

    /////////////////////////////
    ////////Ejemplos/////////////
    /////////////////////////////
    Contactos contacto1 = new Contactos(0, "Danny", "Danny", 696969696);

    Contactos contacto2 = new Contactos(1, "Alejandro", "Adios", 985698569);

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contacts);

        listContacts = (ListView) findViewById(R.id.listView_Contacts);

        allContactos.add(contacto1);
        allContactos.add(contacto2);

        adapterContacts = new myAdapterContacts(this, allContactos);

        listContacts.setAdapter(adapterContacts);

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(allContactos.get(position).getNombre());
            }
        });

    }

    public void lanza(String nombre) {
        Intent i = new Intent(this, ListConversation.class);
        i.putExtra("nombre",nombre);
        startActivity(i);

    }
}
