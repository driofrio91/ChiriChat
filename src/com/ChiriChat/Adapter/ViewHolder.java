package com.ChiriChat.Adapter;/**
 * Created by neosistec on 19/05/2014.
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

/**
 * @author Danny Riofrio Jimenez
 */
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
