package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

/**
 * Created by danny on 30/05/2014.
 */
public class EditMyPerfilUser extends Activity {

    private TextView textNombre;
    private TextView textTelefono;
    private EditText textEstado;
    private ImageView image;

    private Contactos contacto;
    GestionBaseDatosContactos GBDContactos= new GestionBaseDatosContactos();
    
    BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_perfil_user);

        textNombre = (TextView) findViewById(R.id.contact_My_nombre);
        textTelefono = (TextView) findViewById(R.id.contact_My_Telefono);
        textEstado = (EditText) findViewById(R.id.contact_My_Estado);
        image = (ImageView) findViewById(R.id.imageView_My_Contact);

        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        
        
        contacto = GBDContactos.devolverMiContacto(baseDatosL);

        if (contacto instanceof Contactos){
            textNombre.setText(contacto.getNombre());
            textTelefono.setText(String.valueOf(contacto.getTelefono()));
            textEstado.setText(contacto.getEstado());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.mu_activity_edit_my_perfil, menu);

        return super.onCreateOptionsMenu(menu);
    }

//    public Contactos recuperarUsuario(){
//
//        Contactos myContacto = null;
//        SharedPreferences prefs = getSharedPreferences(
//                Register.class.getSimpleName(),
//                Context.MODE_PRIVATE);
//
//        String json = prefs.getString("Usuario","");
//        JSONObject usuario;
//
//        try {
//            usuario = new JSONObject(json);
//            myContacto = new Contactos(usuario);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        if (myContacto instanceof Contactos) {
//            Log.d("Contacto recuperardo con JSON",myContacto.toString());
//            return myContacto;
//        }
//
//        return null;
//    }
}