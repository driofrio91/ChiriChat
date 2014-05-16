package com.ChiriChat.Controller;/**
 * Created by neosistec on 13/05/2014.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Mensajes;

import java.util.ArrayList;

/**
 * @author Danny Riofrio Jimenez
 */
public class myAdapterMensajes extends BaseAdapter {

    private Activity activity;
    private ArrayList<Mensajes> itemMensajes;
    private LayoutInflater inflater;
    //////////////////////////////////////////
    private ViewHolder holder = null;

    public myAdapterMensajes(Activity activity, ArrayList<Mensajes> itemMensajes) {

        this.activity = activity;
        this.itemMensajes = itemMensajes;
        inflater = LayoutInflater.from(activity);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        Mensajes men = (Mensajes) getItem(position);

        //TODO cambiar esto por la comporbacion si es un mensaje local o de entrada

        if (men.getCadena().equals("1")) {

            if (v == null) {

                v = inflater.inflate(R.layout.item_conversation, null);

               // v.findViewById(R.id.textView2).setVisibility(View.GONE);
                // v.findViewById(R.id.textView1).setVisibility(View.INVISIBLE);

                holder = new ViewHolder();
                holder.mensaje = (TextView) v.findViewById(R.id.textView2);
                v.setTag(holder);

            } else {

                v.setTag(holder);

            }
            holder.mensaje.setText(men.toString());

        } else if(!men.getCadena().equals("1")) {

            if (v == null) {

                v = inflater.inflate(R.layout.item_conversation, null);

                // v.findViewById(R.id.textView1).setVisibility(View.INVISIBLE);
               // v.findViewById(R.id.textView1).setVisibility(View.GONE);

                holder = new ViewHolder();
                holder.mensaje = (TextView) v.findViewById(R.id.textView1);
                v.setTag(holder);

            } else {

                v.setTag(holder);

            }

            holder.mensaje.setText(men.getCadena());
        }

        men=null;

        return v;
    }


    class ViewHolder {

        TextView mensaje;

    }

}
