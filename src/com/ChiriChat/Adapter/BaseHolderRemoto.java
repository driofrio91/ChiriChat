package com.ChiriChat.Adapter;/**
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
public class BaseHolderRemoto extends BaseHolder {

    private ImageView image;
    private TextView textView;

    /**
     * El constructor llamara al metodo @see getView() para inicializar las vistas.
     *
     * @param v
     */
    public BaseHolderRemoto(View v) {
        getView(v);
    }

    /**
     * Metodo que nos inicializara las vista.
     * @param v
     */
    @Override
    public void getView(View v) {

        image = (ImageView) v.findViewById(R.id.imagenRemoto);
        textView = (TextView) v.findViewById(R.id.MensajeRemoto);

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

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

