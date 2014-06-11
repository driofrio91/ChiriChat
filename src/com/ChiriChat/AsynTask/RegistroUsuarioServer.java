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
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class RegistroUsuarioServer extends AsyncTask<Object, Integer, Object> {
    private static final String ESTADO = ":)";
    private IContactosDAO contactoDAO;
    private ProgressDialog dialog;
    private Context ctx;
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
    //Variable en la que se almacenara el ID devuelto por el servidor.
    private String regid;
    private GoogleCloudMessaging gcm;



    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    private static final String PROPERTY_USER = "user";
    private static final String ID_USUARIO = "idUser";

    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;

    String SENDER_ID = "871266258505";

    static final String TAG = "GCMDemo";


    public RegistroUsuarioServer(Context ctx, Register reg) {
        this.ctx = ctx;
        register = reg;
        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Insertando usuario en ChiriChat...");
        dialog.setMessage("Espera por favor...");
        dialog.setIndeterminate(true);
        dialog.show();

    }

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



    public void setRegistration(Context ctx, Contactos contacto){

        GBDContactos.insertarUsuario(baseDatos, contacto, true);

    }

}

