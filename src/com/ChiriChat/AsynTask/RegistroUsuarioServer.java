package com.ChiriChat.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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

    /////////////////////////////////////
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
    private static final String PROPERTY_USER = "user";
    private static final String ID_USUARIO = "idUser";

    public static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24 * 7;

    String SENDER_ID = "871266258505";

    static final String TAG = "GCMDemo";

    private Context context;

    private GoogleCloudMessaging gcm;

    //Variable en la que se almacenara el ID devuelto por el servidor de google.
    private String regid;


    private int idUsuario;
    private String nombre;
    private String estado;
    private int telefono;

    //////////////////////////////////////////////


    public RegistroUsuarioServer(Context ctx, Register reg) {
        this.ctx = ctx;
        register = reg;
        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
        try {
            contactoDAO = GestorDAOFactory.getInstance().getFactory().getContactosDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(ctx);
        }

        //Nos registramos en los servidores de GCM

        try {
            regid = gcm.register(SENDER_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.contacto = (Contactos) params[0];

        //añadimos el gcm de google al usuario
        this.contacto.setIdgcm(regid);

        //Nos registramos en nuestro servidor
        Contactos registrado = null;
        try {
            registrado = registerServer(this.contacto );
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Registro SERVER


        if (registrado  instanceof Contactos) {


            setRegistration(registrado);


            regid = getRegistrationId(context);
            //regid = "";

            //Si no disponemos de Registration ID comenzamos el registro
            //  if (regid.equals("")) {
            //     TareaRegistroGCM tarea = new TareaRegistroGCM();
            //     tarea.execute(txtUsuario.getText().toString());

            //  }

        }

        return null;
    }


    @Override
    protected void onPostExecute(Object o) {

        dialog.dismiss();
        if (this.contacto  instanceof Contactos) {
            register.iniciarActividadPrincipal();
        } else {
            Toast.makeText(ctx,
                    "En esto momentos no puedes conectarte con el servidor, intentalo mas tarde",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void setRegistration(Contactos contacto) {

        //REGISTRO LOCAL
        String nombreUsuario = contacto.getNombre();
        int telef = contacto.getTelefono();

        GBDContactos.insertarUsuario(baseDatos, contacto);


    }

    public Contactos registerServer(Contactos contacto) throws Exception {

        this.contacto = contactoDAO.insert(contacto);

        return contacto;

    }

    private String getRegistrationId(Context context) {
        SharedPreferences prefs = ctx.getSharedPreferences(
                Register.class.getSimpleName(),
                Context.MODE_PRIVATE);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");

        if (registrationId.length() == 0) {
            Log.d(TAG, "Registro GCM no encontrado.");
            return "";
        }

        String registeredUser =
                prefs.getString(PROPERTY_USER, "user");
        //Si el usuario esta registrado, obtendremos el nombre del archivo de preferencias.
        nombre = registeredUser;

        int registerID = prefs.getInt(ID_USUARIO, Integer.MIN_VALUE);

        idUsuario = registerID;

        int registeredVersion =
                prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);

        long expirationTime =
                prefs.getLong(PROPERTY_EXPIRATION_TIME, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String expirationDate = sdf.format(new Date(expirationTime));

        Log.d(TAG, "Registro GCM encontrado (usuario=" + registeredUser +
                ", version=" + registeredVersion +
                ", expira=" + expirationDate + ")");

        int currentVersion = getAppVersion(context);

        if (registeredVersion != currentVersion) {
            Log.d(TAG, "Nueva versión de la aplicación.");
            return "";
        } else if (System.currentTimeMillis() > expirationTime) {
            Log.d(TAG, "Registro GCM expirado.");
            return "";
        }


        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);

            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Error al obtener versión: " + e);
        }
    }


}

