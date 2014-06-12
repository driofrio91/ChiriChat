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
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Mensajes;

import java.util.ArrayList;


public class myAdapterMensajes extends BaseAdapter {

    private Activity activity;
    private ArrayList<Mensajes> itemMensajes;
    private LayoutInflater inflater;

    private Contactos miContacto;

    public myAdapterMensajes(Activity activity, ArrayList<Mensajes> itemMensajes, Contactos miContacto) {

        this.activity = activity;
        this.itemMensajes = itemMensajes;
        inflater = LayoutInflater.from(activity);
        this.miContacto = miContacto;
    }

    public ArrayList<Mensajes> getItemMensajes() {
        return itemMensajes;
    }

    public void setItemMensajes(ArrayList<Mensajes> itemMensajes) {
        this.itemMensajes = itemMensajes;
    }

    @Override
    public int getCount() {
        return itemMensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return itemMensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemMensajes.get(position).getIdMensaje();
    }

    /**
     * Metodo que nos devolvera la vista que necesitamos en ese momento.
     * Comprobara que tipo de vista es la que tiene que inflar, segun el metodo @see getItemViewType().
     *
     * @param position
     * @param convertView
     * @param parent
     *
     * @return View -> vista que necesita el ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        Mensajes men = (Mensajes) getItem(position);

        BaseHolder baseHolder = null;

        if (v == null){

            switch (getItemViewType(position)){

                case 0:
                    v = inflater.inflate(R.layout.item_conversation_remoto, null);
                    baseHolder = new BaseHolderLocal(v);
                    break;
                case 1:
                    v = inflater.inflate(R.layout.item_conversation_local, null);
                    baseHolder = new BaseHolderRemoto(v);
                    break;
            }

            v.setTag(baseHolder);
           // Log.d("Nuevo","Vista nueva");
        }else {

            baseHolder = (BaseHolder) v.getTag();
           // Log.d("reciclar","reciclado");
        }

        baseHolder.setContent(men);

        return v;
    }


    /**
     * Condicion para saber que vista queremos.
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        //TODO sustituir por la condicion de que los mensajes sean de locales o remotos
       int idUser = itemMensajes.get(position).getIdUsuario();
        int idMiuser = miContacto.getId();
        if (idUser != idMiuser){

            return 0;

        }else{

            return 1;
        }

    }


    /**
     * Metodo que nos devuelve el numero de total de vistas que tenemos.
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
