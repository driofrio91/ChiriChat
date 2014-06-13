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

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;

/**
 * Clase que se encargfara de cargar los item de los contacto y mostrarlos en la vista.
 */
public class myAdapterContacts extends BaseAdapter {

    private Activity activity;
    private ArrayList<Contactos> itemContactos;
    private LayoutInflater inflater;
    private ViewHolder holder = null;


    public myAdapterContacts(Activity activity, ArrayList<Contactos> itemContactos) {
        this.activity = activity;
        this.itemContactos = itemContactos;
        inflater = LayoutInflater.from(activity);

    }

    @Override
    public int getCount() {
        return itemContactos.size();
    }

    @Override
    public Object getItem(int position) {
        return itemContactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemContactos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        Contactos contact = (Contactos) getItem(position);

        if (v == null) {

            v = inflater.inflate(R.layout.item_contact, null);
            holder = new ViewHolder(v);
            v.setTag(holder);

        } else {
            holder = new ViewHolder(v);
            v.setTag(holder);

        }

        holder.setContent(contact);

        return v;
    }

}
