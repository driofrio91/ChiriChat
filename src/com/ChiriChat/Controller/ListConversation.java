/*
* Copyright (C) 2014 Alejandro Moreno Jimenez | alejandroooo887@gmail.com
*					 Danny Riofrio Jimenez | superdanny.91@gmail.com
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>. *
*/


package com.ChiriChat.Controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.ChiriChat.Adapter.myAdapterContacts;
import com.ChiriChat.Adapter.myAdapterMensajes;
import com.ChiriChat.AsynTask.CrearConversacion;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.*;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;

import java.util.ArrayList;

/**
 * Clase en la que se mostrara la conversacion actual, con la lista de mensajes.
 */
public class ListConversation extends Activity {

    private EditText editText;
    private Button buttonSend;
    private ListView lisViewMensajes;

    private myAdapterMensajes adapterMensajes;

    private ArrayList<Mensajes> allMensajes;

    private ShareActionProvider provider;

    // Instancias del modelo (Gestores de base de datos).
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosMensajes GBDMensaje = new GestionBaseDatosMensajes();
    //Instancia de la clase ComprobarConexionInternet
    private ComprobarConexionInternet comprobarInternet;

    private BDSQLite bd; // Instancia de la base de datos
    private SQLiteDatabase baseDatos; // Instancia de la base de datos escritura
    private SQLiteDatabase baseDatosL;// Instancia de la base de datos lectura

    private Bundle extras;
    private Conversaciones conversacion;
    //Instancia con dos contactos en los que tendremos el nuestro que sera el origen de la conversacione
    //y el destino que sera el otro usuario de la conversacion
    private Contactos contactoOrigen;
    private Contactos contactoDestino;

    private ArrayList<Contactos> listaContactos = new ArrayList<Contactos>();

    /**
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_conversation);

        editText = (EditText) findViewById(R.id.text_sent_msg);
        buttonSend = (Button) findViewById(R.id.bt_sent_msg);
        lisViewMensajes = (ListView) findViewById(android.R.id.list);
        //Con esta linea de codigo se colocara en el fondo de la pantalla una imagen
        //esto impedira que la imagen se se mueva al salir el teclado de android
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fondo_register));

        comprobarInternet = new ComprobarConexionInternet(this);

        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        contactoDestino = null;
        contactoOrigen = GBDContactos.devolverMiContacto(baseDatosL);

        //Recupero el nombre del contacto
        extras = getIntent().getExtras();

        //Recogemos el contacto que hemos pasabo poer bundle al hacer click en un contacto de la lista
        if (extras != null) {
            conversacion = getIntent().getParcelableExtra("conversacion");

            if (conversacion != null) {

                for (int i = 0; i < conversacion.getContactos().size(); i++) {
                    if (contactoOrigen.getId() != conversacion.getContactos().get(i).getId()) {
                        contactoDestino = conversacion.getContactos().get(i);
                    }
                }

                //Cambiamos el titulo de la actividad
                this.setTitle(conversacion.getNombre());

                Log.d("conversacion pasada por bunble", conversacion.toString());

                allMensajes = GBDMensaje.recuperarMensajes(baseDatos, conversacion.getId_conversacion());

                adapterMensajes = new myAdapterMensajes(this, allMensajes, contactoOrigen);

                lisViewMensajes.setAdapter(adapterMensajes);

            } else {
                //Si hemos entrado a esta actividad a traves de contactos, recojeremos un contacto
                contactoDestino = getIntent().getParcelableExtra("contacto");

                Log.d("Contacto origen******************", contactoOrigen.toString());
                Log.d("Contacto destino******************", contactoDestino.toString());

                listaContactos.add(contactoOrigen);
                listaContactos.add(contactoDestino);

                //Cambiamos el titulo de la actividad
                this.setTitle(contactoDestino.getNombre());
                Log.d("Contacto pasado por bunble", contactoDestino.toString());
                //Comprobamos que el contacto pulsado tenga conversacion
                int idConver = GBDConversacion.recuperarIdConversacionNombre(baseDatosL, contactoDestino.getNombre());

                if (idConver != 0) {

                    conversacion = GBDConversacion.recuperarConversacion(baseDatosL, listaContactos, idConver);
                    //si el contacto tiene conversaciones recuperamos la lista de mensajes de ese usuario
                    allMensajes = GBDMensaje.recuperarMensajes(baseDatosL, idConver);

                } else {

                    allMensajes = new ArrayList<Mensajes>();
                }

                adapterMensajes = new myAdapterMensajes(this, allMensajes, contactoOrigen);

                lisViewMensajes.setAdapter(adapterMensajes);

                Log.d("Contacto origen******************", contactoOrigen.toString());
                Log.d("Contacto destino******************", contactoDestino.toString());
            }

        }


        // setRetainInstance(true);
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }
        Log.d("Saved instance", String.valueOf(savedInstanceState));
        if (savedInstanceState != null) {

            allMensajes = savedInstanceState.getParcelableArrayList("list");

            adapterMensajes.notifyDataSetChanged();
        }


        lisViewMensajes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });


    }

    /**
     * En este metodo tendremos que decirle que nos muestre siempre el ultimo mensaje de la lista
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }
    }

    /***
     * Metodo para eliminar un usuario de la lista y de la base datos.
     * @param position
     */
    protected void removeItemFromList(final int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ListConversation.this);

        alert.setTitle(R.string.eliminarMensaje);
        alert.setMessage(R.string.eliminaMensajePregunta);
        alert.setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                GBDMensaje.borrarMensaje(baseDatos, allMensajes.get(position).getIdMensaje());

                // main code on after clicking yes
                allMensajes.remove(deletePosition);
                adapterMensajes.notifyDataSetChanged();
                adapterMensajes.notifyDataSetInvalidated();


            }
        });
        alert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }


    /**
     * Alamacenamos el estado de la actividad para que al girar el movil
     * no vuelva a reninicar la actividad
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //almacenamos la lista de mnsajes
        ArrayList<Mensajes> valores = adapterMensajes.getItemMensajes();
        outState.putParcelableArrayList("list", valores);
    }

    /**
     * Recuperamos el estado de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allMensajes = savedInstanceState.getParcelableArrayList("list");

        adapterMensajes.setItemMensajes(allMensajes);
        adapterMensajes.notifyDataSetChanged();
        // setRetainInstance(true);
        if (allMensajes.size() > 0) {
            lisViewMensajes.setSelection(allMensajes.size() - 1);
        }

    }

    /***
     * Inflamos el menu correspondiente a esta actividad.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_conversation, menu);
        //Le añadimos el boton share
        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_conversacion).getActionProvider();

        provider.setShareIntent(doShare());

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que comprobara que opcion del menu se ha pulsado.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.perfil:
                Intent i = new Intent(this, PerfilUser.class);
                Bundle b = new Bundle();
                b.putParcelable("contacto", this.contactoDestino);
                i.putExtras(b);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metoto que destruira la actividad al pulsar atras.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListChats.class);
        startActivity(intent);
        this.finish();
    }

    public void send(View view) {


        String cadena = editText.getText().toString().trim();


        if (comprobarInternet.available()) {

            if (!cadena.isEmpty()) {

                Mensajes men = new Mensajes(cadena, contactoOrigen.getId());

                allMensajes.add(men);

                Log.d("Mensaje recuperado de la base de datos", allMensajes.toString());

                adapterMensajes.notifyDataSetChanged();


                lisViewMensajes.setSelection(allMensajes.size() - 1);

                if (conversacion == null) {

                    conversacion = new Conversaciones(contactoDestino.getNombre(), listaContactos);
                    Log.d("Conversacion", conversacion.toString());

                }
                CrearConversacion crearConver = new CrearConversacion(this, this);
                crearConver.execute(conversacion, men);

                editText.setText("");
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
        Mensajes mensajeShare = new Mensajes(R.string.chateandoCon + contactoOrigen.getNombre() + R.string.enChiriChat);
        intent.putExtra(Intent.EXTRA_TEXT, mensajeShare.toString());
        return intent;
    }

    /**
     * Metodo que nos devolvera la conversacion actual
     * @return
     */
    public Conversaciones getConversacion() {
        return conversacion;
    }

    /**
     * metodo que modificara la conversacion actual
     * @param conversacion
     */
    public void setConversacion(Conversaciones conversacion) {
        this.conversacion = conversacion;
    }

    /***
     * Metodo que desactiva o activa el boton enviar
     * @param accion
     */
    public void desactivarSend(boolean accion) {
        buttonSend.setEnabled(accion);
    }


}