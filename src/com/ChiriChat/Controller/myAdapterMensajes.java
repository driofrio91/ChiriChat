package com.ChiriChat.Controller;/**
 * Created by neosistec on 13/05/2014.
 */

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
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

        ViewHolder viewHolder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.item_conversation, null);
            holder = new ViewHolder();
            holder.mensaje = (TextView) v.findViewById(R.id.textViewMensaje);
            //holder.mensajeRemoto = (TextView) v.findViewById(R.id.textViewRemoto);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.mensaje.setText(men.toString());

        LayoutParams lp = (LayoutParams) holder.mensaje.getLayoutParams();

        //TODO cambiar esto por la comprobacion si es un mensaje local o de entrada

        if (men.getCadena().equals("1")) {

            lp.gravity = Gravity.LEFT;


        } else {

            lp.gravity = Gravity.RIGHT;
        }

        holder.mensaje.setLayoutParams(lp);
        return v;
    }


    class ViewHolder {
        TextView mensaje, mensajeRemoto;
    }

    @Override
    public int getItemViewType(int position) {
        //if mensaje recivido

        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
