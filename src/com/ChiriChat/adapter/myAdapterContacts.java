package com.ChiriChat.adapter;/**
 * Created by neosistec on 13/05/2014.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.util.ArrayList;

/**
 * @author Danny Riofrio Jimenez
 */
public class myAdapterContacts extends BaseAdapter {

    private Activity activity;
    private ArrayList<Contactos> itemContactos;
    private LayoutInflater inflater;
    //////////////////////////////////////////
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

        Contactos contact = ( Contactos ) getItem(position);

        if ( v == null ){

            v = inflater.inflate(R.layout.item_contact, null);
            holder = new ViewHolder();
            holder.nombre = ( TextView ) v.findViewById(R.id.nombre);
            holder.estado = (TextView) v.findViewById(R.id.estado);
            holder.image = (ImageView) v.findViewById(R.id.image);
            v.setTag(holder);

        }else {

            v.setTag(holder);

        }

        holder.nombre.setText(contact.getNombre());
        holder.estado.setText(contact.getEstado());

        return v;
    }


    /**
     *
     */
    class ViewHolder {

        ImageView image;
        TextView nombre, estado;

    }
}
