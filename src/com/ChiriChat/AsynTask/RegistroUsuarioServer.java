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



package com.ChiriChat.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.ChiriChat.Controller.Register;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Clase que estiende de AsyncTask, en ella se ejecutara una tarea asincronica en la que
 * se registrara nuesto usuario en la base da datos
 */
public class RegistroUsuarioServer extends AsyncTask<Object, Integer, Object> {

    private static final String ESTADO = ":)";
    //Variables de acceso al DAO
    private IContactosDAO contactoDAO;

    private ProgressDialog dialog;
    private Context ctx;
    //
    private Contactos contacto;
    private Register register;
    //Base de datos
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();


    //Variables con las que se almacena los datos del usuario actual
    private int idUsuario;
    private String nombre;
    private String estado;
    private int telefono;


    //Variable en la que se almacenara el ID del GCM devuelto por el servidor de google.
    private String regid;
    private GoogleCloudMessaging gcm;
    //
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    //Variables en la que se alamcenara todas las opciones del registro del gcm.
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    private static final String PROPERTY_USER = "user";
    private static final String ID_USUARIO = "idUser";

    //Tiempo de expiracion dque caduca el ID del GCM.
    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;

    //Sender ID, codigo del proyecto registrado en google que nos dara acceso al GCM de google
    private String SENDER_ID = "871266258505";

    static final String TAG = "GCMDemo";

    /**
     * Consructor de la clase
     * @param ctx contexto de la aplicaion
     * @param reg Clase donde se almacenara la clase que lo lanza
     */
    public RegistroUsuarioServer(Context ctx, Register reg) {
        this.ctx = ctx;
        register = reg;
        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
    }

    /**
     * En este metodo se mostrara un dialogo modal que informara al usuario de que se esta
     * realizando.
     */
    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(R.string.insertandousuario);
        dialog.setMessage("espere un momento/wait a moment");
        dialog.setIndeterminate(true);
        dialog.show();

    }

    /**
     * En este metodo se ejecutara toda la tarea el la que se recuperara el usuario pasada a la AsyncTask
     * se obtendra el ID del GCM y se almacenara en la base da datos del servidor y local.
     * @param params parametros pasados a la AsyncTask
     * @return
     */
    @Override
    protected String doInBackground(Object... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.contacto = (Contactos) params[0];


        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }

            //Nos registramos en los servidores de GCM
            regid = gcm.register(SENDER_ID);

            Log.d(TAG, "Registrado en GCM: registration_id=" + regid);

            this.contacto.setIdgcm(regid);

            //Nos registramos en nuestro servidor
            Contactos registrado = registerServer(this.contacto);

            Log.d("Esta registrado =>", String.valueOf(registrado));

            //Guardamos los datos del registro
            if (registrado instanceof  Contactos) {
                setRegistration(ctx, this.contacto);
            }
        } catch (IOException ex) {
            Log.d(TAG, "Error registro en GCM:" + ex.getMessage());
        }


        return null;
    }


    /**
     * Metodo que se ejecutara al terminara @see doInBackground() en le que si lo que
     * devulve el metodo aterior es un contacto se iniciara la actividad principal si no
     * se mostrara un mensaje de error.
     * @param o
     */
    @Override
    protected void onPostExecute(Object o) {

        dialog.dismiss();
        if (contacto instanceof Contactos){
            register.iniciarActividadPrincipal();
        }else{
            Toast.makeText(ctx,
                    "En esto momentos no puedes conectarte con el servidor, intentalo mas tarde",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo que accedera al webservidor a traves del DAO insertara un contacto
     * @param contacto
     * @return
     */
    public Contactos registerServer(Contactos contacto){
        try {
            contactoDAO = GestorDAOFactory.getInstance().getFactory().getContactosDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Registro SERVER
            this.contacto =   contactoDAO.insert(contacto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  this.contacto;
    }


    /**
     * Metodo que almacenara un contacto en la base da datos
     * @param ctx
     * @param contacto
     */
    public void setRegistration(Context ctx, Contactos contacto){

        GBDContactos.insertarUsuario(baseDatos, contacto);

    }

}

