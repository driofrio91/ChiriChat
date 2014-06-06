package com.ChiriChat.AsynTask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.model.Conversaciones;


public class CrearConversacion extends AsyncTask<Object, Void, Void> {

    private Context ctx;
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private IConversacioneDAO conversacioneDAO;
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();

    public CrearConversacion(Context ctx) {
        this.ctx=ctx;


    }

    @Override
    protected void onPreExecute() {

        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
    }

    @Override
    protected Void doInBackground(Object... params) {
        Log.w("DoinBackground", "--------------------------------");
        try {
            conversacioneDAO = GestorDAOFactory.getInstance().getFactory().getConversacionesDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("DoinBackground", "-----------11111111---------------");

        Conversaciones conver = (Conversaciones) params[0];
        Log.w("DoinBackground", "-----------22222222---------------");
        //Registro en el server
        try {
            conver = conversacioneDAO.insert(conver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.w("DoinBackground", "-----------333333333---------------");
        if(conver instanceof Conversaciones){
            //REGISTRO LOCAL
            GBDConversacion.crearConversacion(baseDatos, conver.getContactos().get(1),conver.getContactos());
            Log.w("DoinBackground", "-----------4444444444444444---------------");
//        }else{
//            onCancelled();
        }
        Log.w("DoinBackground", "-----------555555555555555---------------");
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
