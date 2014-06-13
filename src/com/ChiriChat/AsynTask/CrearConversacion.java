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
import android.widget.Toast;
import com.ChiriChat.Controller.ListConversation;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;
import com.ChiriChat.Gestor.GestorDAOFactory;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;

import java.util.List;

/**
 * Esta clase extiende de AsyncTask.
 * Esta tarea es la mas compleja ya que se encarga de crear la conversacion si no existe,
 * si existe recuperarla para poder trajar con ella y de insertar los mensajes nuevos.
 */
public class CrearConversacion extends AsyncTask<Object, Void, Void> {

    private static final String MENSAJE_ERROR = "Error";
    //Contexto de la aplicacion
    private Context ctx;
    //variable tipo de la clase ListConversation
    private ListConversation listConversation;
    //Varaibles de acceso a la base da datos local
    private BDSQLite bd;
    private SQLiteDatabase baseDatos;
    private SQLiteDatabase baseDatosL;
    //Varaibles de acceso al DAO
    private IConversacioneDAO conversacioneDAO;
    private IMensajesDAO mensajesDAO;
    //Variable para trabajar con los datos de conversaciones y mensajes
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosMensajes GBDMensajes = new GestionBaseDatosMensajes();

    private Conversaciones conver;
    private Mensajes men;
    //Mensaje que solo se creara cuando haya ocurrido un error al insertar datos en el  webservice
    public Mensajes mensajeErrorServer;

    /**
     * Constructor
     * @param ctx contexto de la aplicacion
     * @param listConversation ListConversation
     */
    public CrearConversacion(Context ctx, ListConversation listConversation) {
        this.ctx = ctx;
        this.listConversation = listConversation;

    }

    /**
     * Este metodo se ejecutara antes de toda la tarea, aqui se podra modificar la vista.
     */
    @Override
    protected void onPreExecute() {

        bd = BDSQLite.getInstance(ctx);
        baseDatos = bd.getWritableDatabase();

        listConversation.desactivarSend(false);

    }

    /**
     * Metodo donde se ejecuta la parte mas pesada de la tarea. en el se accede al DAO,
     * a la base de datos local.
     * @param params
     * @return
     */
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


            conver = GBDConversacion.recuperarConversacion(baseDatos, conver.getContactos(), conver.getId_conversacion());

        } else {
            //Si la conversacion no existe la creo en el server y la inserto en la DB local
            try {

                //TODO Registro server
                conver = conversacioneDAO.insert(conver);
                listConversation.setConversacion(conver);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (conver instanceof Conversaciones) {

                //REGISTRO LOCAL
                conver = GBDConversacion.crearConversacion(baseDatos, conver);

            }
        }
        //Recupero el mensaje
        men = (Mensajes) params[1];
        //El mensaje no tiene idConversacion, lo edito
        men.setIdConversacion(conver.getId_conversacion());

        //Lo inserto en el server y lo recupero
        try {
            men = mensajesDAO.insert(men);
            //inserto el mensaje en la base de datos local
            if (men instanceof Mensajes) {
                //Insertaremos el mensaje el el webservice a traves del DAO
                GBDMensajes.insertarMensaje(baseDatos, men);

            }
        } catch (Exception e) {
            //Si pasa cualquir cosa a la hora de recuperar el mensaje insertado
            //se counstrira el mensaje de error que lo mostrara al Toast
            mensajeErrorServer = new Mensajes(MENSAJE_ERROR);
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    /**
     * Metodo que se ejecutara despues de que el metodo doInBackground haya terminado,
     * en el podremos acceder a la vista.
     * @param aVoid
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        if (mensajeErrorServer != null) {
            Toast.makeText(ctx, mensajeErrorServer.toString(), Toast.LENGTH_LONG).show();
        }
        if (GBDConversacion.existeConversacion(baseDatos, conver.getId_conversacion())) {
            listConversation.desactivarSend(true);
        }
    }

}
