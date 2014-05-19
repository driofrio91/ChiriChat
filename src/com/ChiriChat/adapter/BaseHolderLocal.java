package com.ChiriChat.adapter;/**
 * Created by neosistec on 19/05/2014.
 */

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Mensajes;

/**
 * @author Danny Riofrio Jimenez
 */
public class BaseHolderLocal implements BaseHolder {

    private ImageView image;
    private TextView textView;

    /**
     * El constructor llamara al metodo @see getView() para inicializar las vistas.
     * @param v
     */
    public BaseHolderLocal(View v) {
        getView(v);

    }

    /**
     * Metodo que nos inicializara las vista.
     * @param v
     */
    @Override
    public void getView(View v) {
        image = (ImageView) v.findViewById(R.id.imageLocal);
        textView = (TextView) v.findViewById(R.id.MensajeLocal);
    }


    /**
     * Metodo que modificara el contenido de layout
     * @param o
     */
    @Override
    public void setContent(Object o) {
        Mensajes mensaje = (Mensajes) o;
        //si los contactos tiene imagen editarla aqui
        textView.setText(mensaje.toString());
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView gettextView() {
        return textView;
    }

    public void settextView(TextView textView) {
        this.textView = textView;
    }
}
