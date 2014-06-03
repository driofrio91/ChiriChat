package com.ChiriChat.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Conversaciones;

/**
 * Created by danny on 3/06/14.
 */
public class ViewHolderConver extends BaseHolder {

    ImageView image;
    TextView nombre, ultimoMensaje;


    public ViewHolderConver(View v) {
        getView(v);
    }

    @Override
    public void getView(View v) {
        image = (ImageView) v.findViewById(R.id.imageChat);
        nombre = (TextView) v.findViewById(R.id.nombreChat);
        ultimoMensaje = (TextView) v.findViewById(R.id.ultimoMensajeChat);
    }

    @Override
    public void setContent(Object o) {
        Conversaciones conversaciones = (Conversaciones) o;
        nombre.setText(conversaciones.getNombre());

    }
}
