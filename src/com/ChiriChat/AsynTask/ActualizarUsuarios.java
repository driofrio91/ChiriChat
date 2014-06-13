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
 * Clase ActualizarUsuarios, clase que extiende de AsyncTask, esta clase ejecutara una tarea
 * sincronica que recojera los usuarios del server a traves del DAO y los registrara o actualzara
 * en la base datos del movil.
 */
public class ActualizarUsuarios extends AsyncTask<Object, Integer, Object> {
    //Contexto de la aplicaicon y clase desde la que fur lanzada
    private Context ctx;
    private ListContacts listContcts;

    private IContactosDAO contactosDAO;
    private List allContacts;
    //Acceso a base de datos
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosConversaciones GBDConversacione = new GestionBaseDatosConversaciones();
    //Varaible donde se almacenara mi contacto
    private Contactos micontacto;

    /**
     * Constructor
     *
     * @param ctx contexto de la aplicacion
     * @param listContacts ListContacts
     */
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

    /**
     * Aqui modificaremos la vista antes de que se ejecute la tarea.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listContcts.setRefreshActionButtonState(true);

        micontacto = GBDContactos.devolverMiContacto(baseDatos);

    }

    /**
     * Metodo en el que se ejecutara la tarea, en el se recupera mi contacto, y se elimina de la lista
     * queme devulve el servidor con todos los datos, para que no salga mi propio contacto.
     *
     * @param p
     * @return
     */
    @Override
    protected Object doInBackground(Object... p) {

        try {
            Thread.sleep(4000);

            allContacts = contactosDAO.getAllSinMiContacto(micontacto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GBDContactos.actualizaAllContactos(baseDatos, allContacts);

        Log.d("Mi contacto despues de actualiar", GBDContactos.devolverMiContacto(baseDatos).toString());

        List<Conversaciones> allConver = GBDConversacione.recuperarConversaciones(baseDatosL);

        GBDConversacione.actualizaAllConversaciones(baseDatos, allConver);

        return this.allContacts;
    }

    /**
     * Metodo en el que se modificara la vista despues de que el metodo anterior termine.
     * @param o
     */
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        listContcts.setRefreshActionButtonState(false);

        listContcts.recuperarListaContactos();

    }

}
