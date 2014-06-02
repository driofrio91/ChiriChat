package com.ChiriChat.Controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import com.ChiriChat.Adapter.myAdapterContacts;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;


public class ListContacts extends Activity {

    private ListView listContacts;
    private ArrayList<Contactos> allContactos = new ArrayList<Contactos>();

    private myAdapterContacts adapterContacts;

    private Menu optionsMenu;
    private ShareActionProvider provider;


    /////////////////////////////
    ////////Ejemplos/////////////
    /////////////////////////////
    Contactos contacto1 = new Contactos(0, "Danny", "Danny", 696969696);

    Contactos contacto2 = new Contactos(1, "Alejandro", "Adios", 985698569);

    Contactos thisContacto;

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
                lanza(allContactos.get(position));
            }
        });
        listContacts.setTextFilterEnabled(true);

        //Recupero el nombre del contacto
        Bundle extras = getIntent().getExtras();

        //Recogemos el contacto que hemos pasabo poer bundle al hacer click en un contacto de la lista
        if (extras != null){
            thisContacto = getIntent().getParcelableExtra("thisContacto");
            //Cambiamos el titulo de la actividad
            this.setTitle(thisContacto.getNombre());
            Log.d("ID", thisContacto.toString());

        }



    }

    /**
     * Metodo que crea las aopciones de menu, aqui inflamos el layout del menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.optionsMenu = menu;

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_contacts, menu);
        //Boton de compartir
        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_contactos)
                .getActionProvider();

        provider.setShareIntent(doShare());


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que controla que opcion de menu pulsamos
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuBar_Refresh:
                setRefreshActionButtonState(true);
                break;
            case R.id.menuBar_my_perfil:
               openEditPerfil();

                break;
            case R.id.menu_settings:
                Intent i = new Intent(this, Opciones.class);
                startActivity(i);
                break;
            default:
                setRefreshActionButtonState(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Metodo que sustituira el boton de actualizar por una ProgresBar
     * @param refreshing
     */
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

    /**+
     * Metodo que devolvera un intent para compartir.
     * Lo usa el ActioProvider
     * @return
     */
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        return intent;
    }

    /**
     * Metodo que abre una conversacion con el objeto obtenido de la lista.
     * @param contacto
     */
    public void lanza(Contactos contacto) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("contacto", contacto);
        Log.d("metodo Contacto", contacto.toString());
        i.putExtras(b);
        startActivity(i);

    }

    public void openEditPerfil(){
        Intent i = new Intent(this, EditMyPerfilUser.class);
        Bundle b = new Bundle();
        b.putParcelable("contacto", thisContacto);

        i.putExtras(b);
        startActivity(i);
    }



}
