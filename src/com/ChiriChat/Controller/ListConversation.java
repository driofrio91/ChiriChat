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
import android.widget.*;

import com.ChiriChat.Adapter.myAdapterContacts;
import com.ChiriChat.Adapter.myAdapterMensajes;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;

import java.util.ArrayList;

/**
 * Created by neosistec on 13/05/2014.
 */
public class ListConversation extends Activity {

    private EditText editText;
    private Button buttonSend;
    private ListView lisViewMensajes;

    private Mensajes men;

    private myAdapterMensajes adapterMensajes;

    private ArrayList<Mensajes> allMensajes;

    private Contactos contacto, contactoPrueba;

    private ShareActionProvider provider;

    // Instancias del modelo (Gestores de base de datos).
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosMensajes GBDMensaje = new GestionBaseDatosMensajes();

    private BDSQLite bd; // Instancia de la base de datos
    private SQLiteDatabase baseDatos; // Instancia de la base de datos escritura
    private SQLiteDatabase baseDatosL;// Instancia de la base de datos lectura
    //Numero de mensajes para el bundle.
    int numeroMensajes;

    private Bundle extras;
    private Conversaciones conv;

    public ListConversation() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_conversation);

        editText = (EditText) findViewById(R.id.text_sent_msg);
        buttonSend = (Button) findViewById(R.id.bt_sent_msg);
        lisViewMensajes = (ListView) findViewById(android.R.id.list);


        bd = new BDSQLite(this, null);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();


        //Recupero el nombre del contacto
        extras = getIntent().getExtras();

        //Recogemos el contacto que hemos pasabo poer bundle al hacer click en un contacto de la lista
        if (extras != null) {
            contacto = getIntent().getParcelableExtra("contacto");
            contactoPrueba = getIntent().getParcelableExtra("contactoPrueba");
            //Cambiamos el titulo de la actividad
            this.setTitle(contacto.getNombre());
            Log.d("Contacto pasado por bunble", contacto.toString());
        }


        allMensajes = new ArrayList<Mensajes>();

        adapterMensajes = new myAdapterMensajes(this, allMensajes);

        lisViewMensajes.setAdapter(adapterMensajes);
        // setRetainInstance(true);
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }
        Log.d("Saved instance", String.valueOf(savedInstanceState));
        if (savedInstanceState != null) {

            allMensajes = savedInstanceState.getParcelableArrayList("list");

            adapterMensajes.notifyDataSetChanged();
        }

        lisViewMensajes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lisViewMensajes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
    protected void onResume() {
        super.onResume();
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }
    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ListConversation.this);

        alert.setTitle("Eliminar mensaje");
        alert.setMessage("Quieres eliminar este mensaje?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO llamada a la base de datos para que elimine este objeto

                // main code on after clicking yes
                allMensajes.remove(deletePosition);
                adapterMensajes.notifyDataSetChanged();
                adapterMensajes.notifyDataSetInvalidated();

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<Mensajes> valores = adapterMensajes.getItemMensajes();
        outState.putParcelableArrayList("list", valores);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allMensajes = savedInstanceState.getParcelableArrayList("list");
//        Log.d("onREstoreIn", allMensajes.get(allMensajes.size()-1).toString() );

        adapterMensajes.setItemMensajes(allMensajes);
        adapterMensajes.notifyDataSetChanged();
        // setRetainInstance(true);
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_conversation, menu);

        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_conversacion).getActionProvider();

        provider.setShareIntent(doShare());


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i = new Intent(this, PerfilUser.class);
                Bundle b = new Bundle();
                b.putParcelable("contacto", this.contacto);
                b.putParcelable("conver", this.conv);
                b.putParcelable("mensaje", this.men);
                i.putExtras(b);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListChats.class);
        startActivity(intent);
        this.finish();
    }

    public void send(View view) {
        ArrayList<Contactos> listaContactos = new ArrayList<Contactos>();
        String cadena = editText.getText().toString().trim();
        if (!cadena.isEmpty()) {
            // Log.d("CONTAR CONVERSACIONES",
            // ""+GBDConversacion.contarConversaciones(baseDatosL));

            // Obtiene el id de nuestro contacto.
            // Creamos dos objetos java que nos van a servir como origen y destino.
            // El nuestro que no cambia nunca, y el contacto destino que es el que
            // va a cambiar

		/*
         * Este se pedirá a través de una pantalla de configuración.(De momento
		 * al ser usuarios fijos, deberiamos de usar un telefono ya existente.)
		 * Creamos un objto contacto origen y uno destino. Y los obtenemos de
		 * Base de datos filtrando por telefono.
		 */
            Contactos contactoOrigen = GBDContactos.devolverMiContacto(baseDatosL);


            // El origen destino será el que hayamos seleccionado del arrayLis la
            // lista de contactos

            Contactos contactoDestino = new Contactos(contacto.getId(),
                    contacto.getNombre(), contacto.getEstado(),
                    contacto.getTelefono());

            // Comprobamos los datos de los contactos.
            Log.d("Contacto origen******************", contactoOrigen.toString());
            Log.d("Contacto destino******************", contactoDestino.toString());

            // Comprobamos si la conversación existe(Si existe no la creamos de nuevo.)
//        if	(!GBDConversacion.existeConversacion(baseDatos, contactoDestino.getNombre())){
            // Creamos la conversacion en baseDatos.
            GBDConversacion.crearConversacion(baseDatos, contactoDestino.getNombre());

            listaContactos.add(contactoOrigen);
            listaContactos.add(contactoDestino);
            Log.d("*********************************",
                    "Usuarios añadidos al ArrayList");
//        }

            //Se recupera la conversacion con la lista de contactos?¿
            conv = GBDConversacion.recuperarConversacion(baseDatosL, listaContactos);
            Log.d("DATOS OBJETO CONVERSACION", conv.toString());

            GBDMensaje.insertarMensaje(baseDatos, cadena, contactoOrigen.getId(), conv.getId_conversacion());

            Log.d("Nº MEN de la conversacion: " + conv.getId_conversacion(), "" + GBDMensaje.contarMensajes(baseDatosL,
                    contactoOrigen.getId(),
                    conv.getId_conversacion()));

//            ArrayList<Contactos> contactoses = new ArrayList<Contactos>();
//            contactoses.add(contactoPrueba);
//            contactoses.add(contacto);

            men = GBDMensaje.recuperarMensaje(baseDatos, conv.getId_conversacion());
//            //conv = new Conversaciones(1,contacto.getNombre(),1,contactoses,1);
//
            allMensajes.add(men);
            // allMensajes= GBDMensaje.recuperarMensajes(baseDatos, conv.getId_conversacion());

            Log.d("Mensaje recuperado de la base de datos", allMensajes.toString());
            adapterMensajes.notifyDataSetChanged();

            //   adapterMensajes = new myAdapterMensajes(this, allMensajes);
            lisViewMensajes.setSelection(allMensajes.size() - 1);

            editText.setText("");
        }

    }

    /**
     * +
     * Metodo que devolvera un intent para compartir.
     * Lo usa el ActioProvider
     *
     * @return
     */
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        return intent;
    }


}