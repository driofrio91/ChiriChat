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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;


/**
 * Clase enla que se mostraran los datos del perfil de usuario.
 * Por el momento no se puede editarlos datos.
 */
public class EditMyPerfilUser extends Activity {

    private TextView textNombre;
    private TextView textTelefono;
    private TextView textEstado;
    private ImageView image;

    private Contactos contacto;
    GestionBaseDatosContactos GBDContactos= new GestionBaseDatosContactos();
    
    BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_perfil_user);

        textNombre = (TextView) findViewById(R.id.contact_My_nombre);
        textTelefono = (TextView) findViewById(R.id.contact_My_Telefono);
        textEstado = (TextView) findViewById(R.id.contact_My_Estado);
        image = (ImageView) findViewById(R.id.imageView_My_Contact);

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fondo_register));

        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        
        //Recupero mi usuaario de la base da datos
        contacto = GBDContactos.devolverMiContacto(baseDatosL);
        //Si recupero un usuario lo muestro en pantalla
        if (contacto instanceof Contactos){
            textNombre.setText(contacto.getNombre());
            textTelefono.setText(String.valueOf(contacto.getTelefono()));
            textEstado.setText(contacto.getEstado());
            this.setTitle(contacto.getNombre());
        }

    }

    /**
     * Metodo que inicializara la barra de menu y la pintara de color negro
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        return super.onCreateOptionsMenu(menu);
    }

}