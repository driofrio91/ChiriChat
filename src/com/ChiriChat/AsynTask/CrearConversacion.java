package com.ChiriChat.AsynTask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;

import java.util.List;


public class CrearConversacion extends AsyncTask<Object, Void, Void> {

    private Context ctx;
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;

    private IConversacioneDAO conversacioneDAO;
    private IMensajesDAO mensajesDAO;

    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosMensajes GBDMensajes = new GestionBaseDatosMensajes();

    private Conversaciones conver;
    private Mensajes men;

    public CrearConversacion(Context ctx) {
        this.ctx = ctx;


    }

    @Override
    protected void onPreExecute() {

        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
    }

    @Override
    protected Void doInBackground(Object... params) {

        //Se Inician los DAOS
        try {
            conversacioneDAO = GestorDAOFactory.getInstance().getFactory().getConversacionesDAO();
            mensajesDAO = GestorDAOFactory.getInstance().getFactory().getMensajesDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Recupero la conversacion
        conver = (Conversaciones) params[0];

        //Comprubo que la conversacion existe, si existe, la recuperara de la DB local
        if (GBDConversacion.existeConversacion(baseDatos, conver.getId_conversacion())) {

            conver = GBDConversacion.recuperarConversacionNombre(baseDatosL, conver.getContactos().get(0),
                    conver.getContactos().get(1));

        } else {
            //Si la conversacion no existe la creo en el server y la inserto en la DB local
            try {

                //TODO Registro server
                conver = conversacioneDAO.insert(conver);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("AsynkTask",conver.toString());

            if (conver instanceof Conversaciones) {
                //REGISTRO LOCAL
                GBDConversacion.crearConversacion(baseDatos, conver);

            }
        }
        //Recupero el mensaje
        men = (Mensajes) params[1];
        //El mensaje no tiene idConversacion, lo edito
        men.setIdConversacion(conver.getId_conversacion());

        //Lo inserto en el server y lo recupero
        try {
           men =  mensajesDAO.insert(men);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //inserto el mensaje en la base de datos local
        if (men instanceof Mensajes){

            GBDMensajes.insertarMensaje(baseDatos,men);

        }


        return null;
    }
        @Override
        protected void onProgressUpdate (Void...values){

        }

        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);
        }

}
