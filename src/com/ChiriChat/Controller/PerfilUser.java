package com.ChiriChat.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

/**
 * Created by danny on 29/05/14.
 */
public class PerfilUser extends Activity {

    private TextView textNombre;
    private TextView textTelefono;
    private TextView textEstado;
    private ImageView imagen;

    private Bundle bundle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_contact);

        textNombre = (TextView) findViewById(R.id.contact_nombre);
        textTelefono = (TextView) findViewById(R.id.contact_Telefono);
        textEstado = (TextView) findViewById(R.id.contact_Estado);
        imagen = (ImageView) findViewById(R.id.imageView_Contact);

        bundle = getIntent().getExtras();
        if (bundle != null) {
//            Log.d("Mensaje enviado", getIntent().getParcelableExtra("mensaje").toString());
//            Log.d("Conversacion", getIntent().getParcelableExtra("conver").toString());
            Contactos contacto = getIntent().getParcelableExtra("contacto");
            Log.d("telefono", String.valueOf(contacto.getTelefono()));
            textNombre.setText(contacto.getNombre());
            textTelefono.setText(String.valueOf(contacto.getTelefono()));
            textEstado.setText(contacto.getEstado());
            this.setTitle(contacto.getNombre());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }
}