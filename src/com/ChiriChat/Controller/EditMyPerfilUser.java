package com.ChiriChat.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

import java.util.zip.Inflater;

/**
 * Created by danny on 30/05/2014.
 */
public class EditMyPerfilUser extends Activity {

    private EditText textNombre;
    private EditText textTelefono;
    private EditText textEstado;
    private ImageView image;

    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_perfil_user);

        textNombre = (EditText) findViewById(R.id.contact_My_nombre);
        textTelefono = (EditText) findViewById(R.id.contact_My_Telefono);
        textEstado = (EditText) findViewById(R.id.contact_My_Estado);
        image = (ImageView) findViewById(R.id.imageView_My_Contact);

        bundle = getIntent().getExtras();
        if (bundle != null){
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

        inflater.inflate(R.menu.mu_activity_edit_my_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }
}