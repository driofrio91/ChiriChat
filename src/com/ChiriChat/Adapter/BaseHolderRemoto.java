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
import com.ChiriChat.model.Mensajes;

/**
 * Clase en la que inicializaremos las vistas de uno del los items de chats
 */
public class BaseHolderRemoto extends BaseHolder {

    private ImageView image;
    private TextView textView;

    /**
     * El constructor llamara al metodo @see getView() para inicializar las vistas.
     *
     * @param v
     */
    public BaseHolderRemoto(View v) {
        getView(v);
    }

    /**
     * Metodo que nos inicializara las vista.
     * @param v
     */
    @Override
    public void getView(View v) {

        image = (ImageView) v.findViewById(R.id.imagenRemoto);
        textView = (TextView) v.findViewById(R.id.MensajeRemoto);

    }

    /**
     * Metodo que modificara el contenido de layout
     * @param o
     */
    @Override
    public void setContent(Object o) {
        Mensajes mensaje = (Mensajes) o;
        //si los contactos tiene imagen editarla aqui
        textView.setText(mensaje.toString());

    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

