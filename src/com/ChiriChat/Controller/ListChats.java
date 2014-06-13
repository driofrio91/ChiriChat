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
import android.widget.ShareActionProvider;
import com.ChiriChat.Adapter.myAdapterChats;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;

/**
 * Clase en la que estara todas las coversaciones.
 * Esta sera la clase principal, de manera que siempre se habrira esta clase para que
 * puedas acceder desde ella a todas las demas.
 */
public class ListChats extends Activity {
    //Instancia de la clase que trabaja la lista de conversaciones
    private GestionBaseDatosConversaciones GBDConversaciones = new GestionBaseDatosConversaciones();

    private ListView listViewChats;
    private myAdapterChats adapterChats;

    //Variable del boton Share
    private ShareActionProvider provider;
    //Lista de conversaciones
    private ArrayList<Conversaciones> allChats = new ArrayList<Conversaciones>();

    //Instancias de la base de datos.
    private BDSQLite bd; // Instancia de la base de datos
    private SQLiteDatabase baseDatos; // Instancia de la base de datos escritura
    private SQLiteDatabase baseDatosL;// Instancia de la base de datos lectura



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_chats);
        //Instancia de la base de datos para trabajar
        bd= BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();

        listViewChats = (ListView) findViewById(R.id.listView_Chats);

        allChats = GBDConversaciones.recuperarConversaciones(baseDatosL);

        Log.d("Conversaciones Lista", "" + allChats.toString());

        adapterChats = new myAdapterChats(this, allChats);

        listViewChats.setAdapter(adapterChats);

        setTitle(R.string.tituloConver);

        listViewChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openConversacion(allChats.get(position));
                Log.d("CONVERSACIOSELECCIONADA", "" + allChats.get(position).getNombre());
            }
        });

        listViewChats.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });

        adapterChats.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos la vista del menu que le corresponde a la actividad
        MenuInflater inflate = getMenuInflater();

        inflate.inflate(R.menu.menu_activity_chats, menu);
        //Iniciamos la variable de boton share
        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_contactos)
                .getActionProvider();
        //Le asignamos el metodo a la accion de compartir.
        provider.setShareIntent(doShare());

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que se encargara de las opciones selecionadas del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_contacts:

                Intent intent = new Intent(this, ListContacts.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.menu_settings:
                Intent i = new Intent(this, Opciones.class);
                startActivity(i);
                this.finish();
                break;
            case R.id.menuBar_my_perfil:
                openEditPerfil();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo que al pulsar atras finalizara la actividad finalizando la alicacion.
     */
    @Override
    public void onBackPressed() {
        this.finish();
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
        intent.putExtra(Intent.EXTRA_TEXT, R.string.usandoChiriChat);
        return intent;
    }


    /**
     * Metodo que abrira al actividad de editar perfil
     */
    public void openEditPerfil() {
        Intent i = new Intent(this, EditMyPerfilUser.class);
        startActivity(i);

    }

    /**
     * Metodo que abre una conversacion con el objeto obtenido de la lista.
     *
     * @param conver
     */
    public void openConversacion(Conversaciones conver) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("conversacion", conver);

        Log.d("metodo Conversacion", conver.toString());
        i.putExtras(b);
        startActivity(i);
        this.finish();
    }

    /**
     * Metodo que eliminara la conversaion seleccionada de la lista de conversaciones.
     * @param position
     */
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                ListChats.this);

        alert.setTitle("Eliminar conversacion");
        alert.setMessage("Quieres eliminar esta conversacion?");
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // main code on after clicking yes
                allChats.remove(deletePosition);
                adapterChats.notifyDataSetChanged();
                adapterChats.notifyDataSetInvalidated();

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

