package com.ChiriChat.AsynTask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.zip.Inflater;

/**
 * Created by danny on 5/06/14.
 */
public class CrearConversacion extends AsyncTask<Object, Integer, String> {

    private Context ctx;
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private IConversacioneDAO conversacioneDAO;
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();

    public CrearConversacion(Context ctx) {
        this.ctx=ctx;
        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();

    }


    @Override
    protected String doInBackground(Object... params) {

        try {
            conversacioneDAO = GestorDAOFactory.getInstance().getFactory().getConversacionesDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Conversaciones conver = (Conversaciones) params[0];
        //Registro en el server
        try {
            conver = conversacioneDAO.insert(conver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(conver instanceof Conversaciones){
            //REGISTRO LOCAL
            GBDConversacion.crearConversacion(baseDatos, conver.getContactos().get(1),conver.getContactos());

//        }else{
//            onCancelled();
        }

        return null;
    }
}
