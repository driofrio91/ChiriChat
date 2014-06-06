package com.ChiriChat.AsynTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;
import com.ChiriChat.Controller.Register;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.model.Contactos;


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
        try {
            contactoDAO = GestorDAOFactory.getInstance().getFactory().getContactosDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
        contacto = (Contactos) params[0];
        try {
            //Registro SERVER
         contacto =   contactoDAO.insert(contacto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(contacto instanceof Contactos){
            //REGISTRO LOCAL
            String nombreUsuario = contacto.getNombre();
            int telef = contacto.getTelefono();

            GBDContactos.insertarUsuario(baseDatos, contacto, true);

//        }else{
//            onCancelled();
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

}

