package com.ChiriChat.Controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.ChiriChat.AsynTask.ActualizarUsuarios;
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
    private GestionBaseDatosMensajes gBDCMensajes = new GestionBaseDatosMensajes();
    private GestionBaseDatosConversaciones GBDCConversaciones = new GestionBaseDatosConversaciones();
    BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private int numeroUsuarios;

    private ActualizarUsuarios actualizaUsuarios;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_contacts);

        listContacts = (ListView) findViewById(R.id.listView_Contacts);
//---------------------------------
        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        String sql = "PRAGMA foreign_keys = ON";
        baseDatos.execSQL(sql);
        // gb.borrarContactos(baseDatos);

		/*Como son usuarios fijos y solo se van a insertar una vez,
		comprobamos antes de insertarlos que no existe ningun contacto
		en la tabla usuarios*/
        
        //numeroUsuarios = GBDCContactos.cuentaUsuarios(baseDatosL);
        if (GBDCContactos.cuentaUsuarios(baseDatosL) <2) {
        	
        	gb.insertarUsuarios(baseDatos);
        } 

        recuperarListaContactos();

        // Devueleve el numero de contactos
//        Log.d("NUMERO CONTACTOS", "" + contacts.size());
//        numeroContactos = contacts.size();

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(contacts.get(position));
                Log.d("CONTACTO PULSADO", contacts.get(position).getNombre());
            }
        });
        //  listContacts.setTextFilterEnabled(true);

        listContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Metodo que crea las aopciones de menu, aqui inflamos el layout del menu.
     *
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
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuBar_Refresh:
                actualizaUsuarios = new ActualizarUsuarios(this,this);
                actualizaUsuarios.execute();
                break;
            default:
                //setRefreshActionButtonState(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Metodo que sustituira el boton de actualizar por una ProgresBar
     *
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

    /**
     * Metodo que devolvera un intent para compartir.
     * Lo usa el ActioProvider
     *
     * @return
     */
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Estoy usando Chirichat");
        return intent;
    }


    public void recuperarListaContactos(){

        // Recuperamos todos los usuarios de la tablas usuarios.
        contacts = (ArrayList<Contactos>) gb.recuperarContactos(baseDatosL);

        if (contacts.isEmpty()){
            actualizaUsuarios = new ActualizarUsuarios(this,this);
            actualizaUsuarios.execute();
            contacts = (ArrayList<Contactos>) gb.recuperarContactos(baseDatosL);
        }

        adapterContacts = new myAdapterContacts(this, contacts);

        listContacts.setAdapter(adapterContacts);

        adapterContacts.notifyDataSetChanged();
    }

    /**
     * Metodo que abre una conversacion con el objeto obtenido de la lista.
     *
     * @param contacto
     */
    public void lanza(Contactos contacto) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("contacto", contacto);
        Log.d("metodo Contacto", contacto.toString());
        i.putExtras(b);
        startActivity(i);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ListChats.class);
        startActivity(intent);
        this.finish();
    }


    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ListContacts.this);

        alert.setTitle("Eliminar mensaje");
        alert.setMessage("Quieres eliminar este contacto?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO llamada a la base de datos para que elimine este objeto

                // main code on after clicking yes
                contacts.remove(deletePosition);
                adapterContacts.notifyDataSetChanged();
                adapterContacts.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


    }

}
