package com.ChiriChat.Controller;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ChiriChat.R;
import com.ChiriChat.Adapter.myAdapterContacts;
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

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_TITLE);
      actionBar.setDisplayShowTitleEnabled(false);



        listContacts = (ListView) findViewById(R.id.listView_Contacts);

        allContactos.add(contacto1);
        Log.d("contacto1",contacto1.toString());
        allContactos.add(contacto2);
        Log.d("contacto2",contacto2.toString());

        adapterContacts = new myAdapterContacts(this, allContactos);

        listContacts.setAdapter(adapterContacts);

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(allContactos.get(position));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void lanza(Contactos contacto) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("key",contacto);
        Log.d("metodo Contacto",contacto.toString());
        i.putExtras(b);
        startActivity(i);

    }


}
