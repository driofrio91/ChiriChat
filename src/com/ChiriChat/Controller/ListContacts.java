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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.ChiriChat.Adapter.myAdapterContacts;
import com.ChiriChat.AsynTask.ActualizarUsuarios;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;


public class ListContacts extends Activity {

    private ListView listContacts;

    private myAdapterContacts adapterContacts;

    private Menu optionsMenu;




    private ArrayList<Contactos> contacts = null;

    private GestionBaseDatosContactos GBDCContactos = new GestionBaseDatosContactos();

    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;


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

        setTitle(R.string.tituloContactos);

        listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lanza(contacts.get(position));
                Log.d("CONTACTO PULSADO", contacts.get(position).getNombre());
            }
        });


        listContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });

        recuperarListaContactos();

        toastInfo();
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

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

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



    public void recuperarListaContactos(){

        // Recuperamos todos los usuarios de la tablas usuarios.
        contacts = (ArrayList<Contactos>) GBDCContactos.recuperarContactos(baseDatosL);

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

        alert.setTitle("Eliminar contacto");
        alert.setMessage("Quieres eliminar este contacto?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


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

    public void toastInfo(){
        if(contacts.isEmpty()){
            Toast.makeText(this,R.string.actualizeContactos, Toast.LENGTH_LONG).show();
        }
    }

}
