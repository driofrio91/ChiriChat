/*
* Copyright (C) 2014 Alejandro Moreno Jimenez | alejandroooo887@gmail.com
*					 Danny Riofrio Jimenez | superdanny.91@gmail.com
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>. *
*/


package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
 * Clase que pedira el registro del usuario en el primer uso
 */
public class Register extends Activity {

    private static final String ESTADO = "Disponible";

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


    /**
     * En el metodo onCreatese iniciaran las vista y se comprobara que si estamos registrados para
     * iniciar actividad principal (ListChats), si no nos pedira que nos regisrtremos.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getLanguage();

        nombre = (EditText) findViewById(R.id.login_usuario_edit);
        telefono = (EditText) findViewById(R.id.login_telefono_edit);

        bd = BDSQLite.getInstance(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();

        miContacto = GBDContactos.devolverMiContacto(baseDatosL);
        conexion = new ComprobarConexionInternet(this);
        if (miContacto != null) {
            iniciarActividadPrincipal();
        }

    }

    /**
     * Metodo comprobara que tengamos conexion a internet y si hemos introducido algo
     * en el formulario, contruira el objeto contacto con los mis datos, y se lo
     * pasara a la tarea asincronica que nos registrara en el server
     * @param v
     */
    public void registrar(View v){
        if	(conexion.available()){
            if (nombre.getText().length() >= 0 && telefono.getText().length() == 9){
                telef= Integer.parseInt(telefono.getText().toString());

                //REGISTRO EN WEB
                miContacto =new Contactos( nombre.getText().toString(), ESTADO,telef );
                try {

                    RegistroUsuarioServer registro = new RegistroUsuarioServer(this, this);
                    registro.execute(miContacto);

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }else{
                Toast toast = Toast.makeText(this, R.string.datosIncorrectos, Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }



    /**
     * Que inicia la actividad principal y finaliza esta actividad.
     */
    public void iniciarActividadPrincipal() {

        Intent intent = new Intent(this, ListChats.class);
        startActivity(intent);
        this.finish();

    }

    /**
     * Metodo que obtiene el lenguaje actual, obtendra el lenguaje por defecto del sistema
     * a la hora de insalar.
     */
    public void getLanguage() {
        SharedPreferences prefs = getSharedPreferences(
                Register.class.getSimpleName(), Context.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");

        Language.setLocal(language);
        startActivity(this.getIntent());

    }


}