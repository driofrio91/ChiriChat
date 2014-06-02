package com.ChiriChat.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;

/**
 * Created by danny on 31/05/14.
 */
public class myAdapterChats extends BaseAdapter {

    private Activity activity;
    private ArrayList<Conversaciones> itemContactos;
    private LayoutInflater inflater;
    private ViewHolder holder = null;


    public myAdapterChats(Activity activity, ArrayList<Conversaciones> itemContactos) {
        this.activity = activity;
        this.itemContactos = itemContactos;
        inflater = LayoutInflater.from(activity);

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Conversaciones conver = (Conversaciones) getItem(position);

        if (v == null) {

            v = inflater.inflate(R.layout.item_contact, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
            Log.d("Nuevo", "Vista nueva");
        } else {
            holder = new ViewHolder(v);
            v.setTag(holder);
            Log.d("reciclar","reciclado");
        }

        holder.setContent(conver);

        return v;
    }


}
