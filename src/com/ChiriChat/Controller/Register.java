package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ChiriChat.AsynTask.RegistroUsuarioServer;
import com.ChiriChat.Language.Language;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;

/**
 * Created by danny on 30/05/2014.
 */
public class Register extends Activity {

    private static final String ESTADO = ":)";
    int telef;
    private static final String LANGUAGE = "language";


    private EditText nombre;
    private EditText telefono;

    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();

    private Contactos miContacto = new Contactos();

    private ComprobarConexionInternet conexion;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getLanguage();

//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        String t = telephonyManager.getDeviceId();
//        Log.d("imei telefono",t);

        nombre = (EditText) findViewById(R.id.login_usuario_edit);
        telefono = (EditText) findViewById(R.id.login_telefono_edit);

        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();

        miContacto = GBDContactos.devolverMiContacto(baseDatosL);
        conexion = new ComprobarConexionInternet(this);
        if ((miContacto instanceof Contactos) && miContacto.getTelefono() != 0) {
            iniciarActividadPrincipal();
        }

    }

    public void registrar(View v){
        if	(conexion.available()){
            if (nombre.getText().length() >= 0 && telefono.getText().length() == 9){
                telef= Integer.parseInt(telefono.getText().toString());
                //REGISTRO EN WEB
                miContacto =new Contactos( nombre.getText().toString(), ESTADO,telef );
                try {

                    //  miContacto = contactoDAO.insert(miContacto);
                    RegistroUsuarioServer registro = new RegistroUsuarioServer(this, this);
                    registro.execute(miContacto);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }else{
                Toast toast = Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }



    /**
     * Metoto que conprueba que ya exista registro del dispositivo
     */
    public void iniciarActividadPrincipal() {

        Intent intent = new Intent(this, ListChats.class);
        startActivity(intent);
        this.finish();

    }

    public void getLanguage() {
        SharedPreferences prefs = getSharedPreferences(
                Register.class.getSimpleName(), Context.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");

        //Log.d("PREFERS", prefs.getAll().toString());
        //Log.d("Idioma ---->", language);
        Language.setLocal(language);
        startActivity(this.getIntent());

    }


}