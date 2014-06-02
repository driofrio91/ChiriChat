package com.ChiriChat.Controller;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;


public class ListContacts extends Activity {

    private ListView listContacts;

    private myAdapterContacts adapterContacts;

    private Menu optionsMenu;
    private ShareActionProvider provider;


    GestionBaseDatosContactos gb = new GestionBaseDatosContactos();
    private ArrayList<Contactos> contacts = null;
    //private ArrayList<Mensajes> listaMensajes = null;
    private int numeroContactos;
    private GestionBaseDatosContactos GBDCContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosMensajes gBDCMensajes= new GestionBaseDatosMensajes();
    private GestionBaseDatosConversaciones GBDCConversaciones= new GestionBaseDatosConversaciones();
    BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;

    Contactos thisContacto;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contacts);

        listContacts = (ListView) findViewById(R.id.listView_Contacts);
//---------------------------------
        bd = new BDSQLite(this, null);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        String sql = "PRAGMA foreign_keys = ON";
        baseDatos.execSQL(sql);
        // gb.borrarContactos(baseDatos);

		/*Como son usuarios fijos y solo se van a insertar una vez,
		comprobamos antes de insertarlos que no existe ningun contacto
		en la tabla usuarios*/

        if (GBDCContactos.cuentaUsuarios(baseDatosL) != 0) {
            // do nothing
        } else {
            // Insertamos usuarios fijos.
            gb.insertarUsuarios(baseDatos);
        }

        // Recuperamos todos los usuarios de la tablas usuarios.
        contacts = (ArrayList<Contactos>) gb.recuperarContactos(baseDatosL);

        adapterContacts = new myAdapterContacts(this, contacts);

        listContacts.setAdapter(adapterContacts);

        // Devueleve el numero de contactos
        Log.d("NUMERO CONTACTOS", "" + contacts.size());
        numeroContactos = contacts.size();

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(contacts.get(position));
                Log.d("CONTACTO PULSADO", contacts.get(position).getNombre());
            }
        });
      //  listContacts.setTextFilterEnabled(true);

        //Recupero el nombre del contacto
        Bundle extras = getIntent().getExtras();

        //Recogemos el contacto con el que nos hemos registrado
        if (extras != null){
            thisContacto = getIntent().getParcelableExtra("thisContacto");
            //Cambiamos el titulo de la actividad
          //  this.setTitle(thisContacto.getNombre());
           // Log.d("ID", thisContacto.toString());

        }



    }

    @Override
    protected void onStart() {
        super.onStart();
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
