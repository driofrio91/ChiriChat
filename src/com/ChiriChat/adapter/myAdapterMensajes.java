package com.ChiriChat.adapter;/**
 * Created by neosistec on 13/05/2014.
 */

import android.app.Activity;
import android.util.Log;
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

    /**
     * Metodo que nos devolvera la vista para ese momento.
     * Comprobara que tipo de vista es la que tiene que inflar, segun el metodo @see getItemViewType
     *
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        Mensajes men = (Mensajes) getItem(position);
        Log.d("Mesaje -> ",men.toString());
        BaseHolder baseHolder = null;

        if (v == null){

            baseHolder = new BaseHolder();

            switch (getItemViewType(position)){

                case 0:
                    v = inflater.inflate(R.layout.item_conversation_local, null);
                    baseHolder.text = (TextView) v.findViewById(R.id.MensajeLocal);
                    break;
                case 1:
                    v = inflater.inflate(R.layout.item_conversation_remoto, null);
                    baseHolder.text = (TextView) v.findViewById(R.id.MensajeRemoto);
                    break;
            }

            v.setTag(baseHolder);

        }else {

            baseHolder = (BaseHolder) v.getTag();
            Log.d("reciblar","raciclado");
        }

        baseHolder.text.setText(men.toString());

        return v;
    }


    class ViewHolder {
        TextView mensaje, mensajeRemoto;
    }

    /**
     * Condicion para saber que vista queremos.
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        
        //TODO sustituir por la condicion de que los mensajes sean de locales o remotos

        if (itemMensajes.get(position).getCadena().contains("1")){

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
