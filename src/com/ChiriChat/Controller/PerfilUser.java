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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

/**
 * Clase en la que se muestra la informacion de los contactos
 */
public class PerfilUser extends Activity {

    private TextView textNombre;
    private TextView textTelefono;
    private TextView textEstado;
    private ImageView imagen;

    private Bundle bundle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_contact);

        textNombre = (TextView) findViewById(R.id.contact_nombre);
        textTelefono = (TextView) findViewById(R.id.contact_Telefono);
        textEstado = (TextView) findViewById(R.id.contact_Estado);
        imagen = (ImageView) findViewById(R.id.imageView_Contact);

        bundle = getIntent().getExtras();
        if (bundle != null) {

            Contactos contacto = getIntent().getParcelableExtra("contacto");

            textNombre.setText(contacto.getNombre());
            textTelefono.setText(String.valueOf(contacto.getTelefono()));
            textEstado.setText(contacto.getEstado());
            this.setTitle(contacto.getNombre());
        }
    }

    /**
     * Metodo que inflara la vista del menu perteneciente a esta actividad.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_perfil, menu);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Metodo que destruira la actividad al pulsar el boton atras.
     */
    @Override
    public void onBackPressed() {
        this.finish();
    }
}