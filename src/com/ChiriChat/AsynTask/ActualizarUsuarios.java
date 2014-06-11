package com.ChiriChat.AsynTask;

/**
 * Created by neosistec on 05/06/2014.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import android.util.Log;
import com.ChiriChat.Controller.ListContacts;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.List;

/**
 * @author Danny Riofrio Jimenez
 */
public class ActualizarUsuarios extends AsyncTask<Object, Integer, Object> {

    private Context ctx;
    private ListContacts listContcts;
    private IContactosDAO contactosDAO;
    private List allContacts;
    //
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosConversaciones GBDConversacione = new GestionBaseDatosConversaciones();

    private Contactos micontacto;

    public ActualizarUsuarios(Context ctx, ListContacts listContacts) {
        this.ctx = ctx;
        this.listContcts = listContacts;

        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();

        try {
            contactosDAO = GestorDAOFactory.getInstance().getFactory()
                    .getContactosDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listContcts.setRefreshActionButtonState(true);

        micontacto = GBDContactos.devolverMiContacto(baseDatos);

    }

    @Override
    protected Object doInBackground(Object... p) {

        try {
            Thread.sleep(4000);

            allContacts = contactosDAO.getAllSinMiContacto(micontacto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.allContacts;
    }
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        GBDContactos.actualizaAllContactos(baseDatos, allContacts);

        listContcts.setRefreshActionButtonState(false);

        List<Conversaciones> allConver = GBDConversacione.recuperarConversaciones(baseDatosL);

        GBDConversacione.actualizaAllConversaciones(baseDatos, allConver);

        listContcts.recuperarListaContactos();

    }

}
