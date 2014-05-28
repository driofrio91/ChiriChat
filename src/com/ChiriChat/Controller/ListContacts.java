package com.ChiriChat.Controller;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import com.ChiriChat.Adapter.myAdapterContacts;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.security.Provider;
import java.util.ArrayList;

public class ListContacts extends Activity {

    private ListView listContacts;
    private ArrayList<Contactos> allContactos = new ArrayList<Contactos>();

    private myAdapterContacts adapterContacts;

    private Menu optionsMenu;
    private ShareActionProvider provider;
    SearchView searchView;
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
        Log.d("contacto1", contacto1.toString());
        allContactos.add(contacto2);
        Log.d("contacto2", contacto2.toString());

        adapterContacts = new myAdapterContacts(this, allContactos);

        listContacts.setAdapter(adapterContacts);

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(allContactos.get(position));
            }
        });
        listContacts.setTextFilterEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.optionsMenu = menu;

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_contacts, menu);
        //Boton de compartir
        provider = (ShareActionProvider) menu.findItem(R.id.menu_share)
                .getActionProvider();


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuBar_Refresh:
                setRefreshActionButtonState(true);
                break;
            case R.id.menu_share:
                doShare();
                break;
            default:
                setRefreshActionButtonState(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.menuBar_Refresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }

    public void doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        provider.setShareIntent(intent);
    }

    public void lanza(Contactos contacto) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("key", contacto);
        Log.d("metodo Contacto", contacto.toString());
        i.putExtras(b);
        startActivity(i);

    }


}
