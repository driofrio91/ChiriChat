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
import com.ChiriChat.model.Contactos;


public class ViewHolder extends BaseHolder{

    ImageView image;
    TextView nombre, estado;

    public ViewHolder(View v) {
        getView(v);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getEstado() {
        return estado;
    }

    public void setEstado(TextView estado) {
        this.estado = estado;
    }


    @Override
    public void getView(View v) {
        image = (ImageView) v.findViewById(R.id.imageContact);
        nombre = (TextView) v.findViewById(R.id.nombreContact);
        estado = (TextView) v.findViewById(R.id.estadoContact);
    }

    @Override
    public void setContent(Object o) {
        Contactos contacto = (Contactos) o;
        //Si el contacto tien imagen editar aqui
        nombre.setText(contacto.getNombre());
        estado.setText(contacto.getEstado());
    }
}
