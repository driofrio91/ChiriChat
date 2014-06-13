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


package com.ChiriChat.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Conversaciones;

/**
 * Clase viewholder, inicializa las vistas delitem donde se mostrara los datos del contacto
 */
public class ViewHolderConver extends BaseHolder {

    ImageView image;
    TextView nombre, ultimoMensaje;


    public ViewHolderConver(View v) {
        getView(v);
    }

    /**
     * Obtiene las vista que vayamos usar por si id.
     * @param v
     */
    @Override
    public void getView(View v) {
        image = (ImageView) v.findViewById(R.id.imageChat);
        nombre = (TextView) v.findViewById(R.id.nombreChat);
        ultimoMensaje = (TextView) v.findViewById(R.id.ultimoMensajeChat);
    }

    /**
     * Modifica el comtenido de ls vistas con el objeto que le hayamos.
     * @param o
     */
    @Override
    public void setContent(Object o) {
        Conversaciones conversaciones = (Conversaciones) o;
        nombre.setText(conversaciones.getNombre());

    }
}
