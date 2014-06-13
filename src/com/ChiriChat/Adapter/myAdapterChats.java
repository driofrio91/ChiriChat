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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ChiriChat.R;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;

/**
 * Apadter personalizado en que que inflaremos nuesteras vistas yls rellenaremos con los
 * onjetos que contenga la arrayList
 *
 */
public class myAdapterChats extends BaseAdapter {

    private Activity activity;
    private ArrayList<Conversaciones> itemConversaciones;
    private LayoutInflater inflater;
    private ViewHolderConver holder = null;


    public myAdapterChats(Activity activity, ArrayList<Conversaciones> itemConversaciones) {
        this.activity = activity;
        this.itemConversaciones = itemConversaciones;
        inflater = LayoutInflater.from(activity);

    }

    @Override
    public int getCount() {
      return itemConversaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return itemConversaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemConversaciones.get(position).getId_conversacion();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Conversaciones conver = (Conversaciones) getItem(position);

        if (v == null) {

            v = inflater.inflate(R.layout.item_chat, null);
            holder = new ViewHolderConver(v);
            v.setTag(holder);

        } else {
            holder = new ViewHolderConver(v);
            v.setTag(holder);

        }

        holder.setContent(conver);

        return v;
    }


}
