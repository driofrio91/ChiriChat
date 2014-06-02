package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;

/**
 * Created by danny on 30/05/2014.
 */
public class Register extends Activity {

    private static final String ID_USER = "idUsuario";
    private static final String NOMBRE = "nombre";
    private static final String TELEFONO = "telefono";
    private static final String ESTADO = "estado";

    private EditText nombre;
    private EditText telefono;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = (EditText) findViewById(R.id.login_usuario_edit);
        telefono = (EditText) findViewById(R.id.login_telefono_edit);

      iniciarActividadPrincipal();

    }

    public void registrar(View v){

        if (nombre.getText().length() >= 0 && telefono.getText().length() == 9){

            String nombe = nombre.getText().toString();
            int telef = Integer.parseInt(telefono.getText().toString());
            saveUsuario(1,nombe,telef,":)");

            iniciarActividadPrincipal();
        }

    }

    private void saveUsuario(int idUsuario, String nombre, int telefono, String estado) {
        SharedPreferences prefs = getSharedPreferences(
                Register.class.getSimpleName(),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ID_USER, idUsuario);
        editor.putString(NOMBRE, nombre);
        editor.putInt(TELEFONO, telefono);
        editor.putString(ESTADO, estado);
        editor.commit();
    }

    /**
     * Metodo que me devuelve el usuario registrado.
     * @return
     */
    private Contactos getUsuario(){
        Contactos c = null;
            SharedPreferences prefs = getSharedPreferences(
                    Register.class.getSimpleName(),
                    Context.MODE_PRIVATE);
        int idUsuario = prefs.getInt(ID_USER, 0);
        String nombre = prefs.getString(NOMBRE, "");
        int telefono = prefs.getInt(TELEFONO,0);
        String estado = prefs.getString(ESTADO, "");

        if (telefono != 0 || !nombre.equals("")) {
            c = new Contactos(idUsuario, nombre, estado, telefono);
        }

        return c;
    }

    /**
     * Metoto que conprueba que ya exista registro del dispositivo
     */
    public void iniciarActividadPrincipal(){

        Contactos contacto = getUsuario();
        if (contacto instanceof Contactos){
            Intent intent = new Intent(this, ListChats.class);
            Bundle b = new Bundle();
            b.putParcelable("thisContacto", contacto);
            intent.putExtras(b);
            startActivity(intent);
        }

    }

}