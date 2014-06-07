package com.ChiriChat.SQLiteDataBaseModel;

/**
 * Created by Alejandro on 30/05/2014.
 */

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;


public class GestionBaseDatosConversaciones {

    private GestionBaseDatosUsu_Conv GBDUsuConversaciones = new GestionBaseDatosUsu_Conv(this);

    /**
     *
     * @param baseDatos
     * @param conversacion
     * @return
     */
    public Conversaciones crearConversacion(SQLiteDatabase baseDatos, Conversaciones conversacion) {

        Conversaciones conver = null;

       if(existeConversacion(baseDatos, conversacion.getId_conversacion())) {

           conver = recuperarConversacion(baseDatos,conversacion.getContactos());


       }else{

           String sql = "INSERT INTO CONVERSACION (id_conversacion,nombre,ocultar)"
                   + " values (" + conversacion.getId_conversacion()
                   + ",'"+conversacion.getNombre()+"','false')";
           baseDatos.execSQL(sql);

           for (int i = 0; i < conversacion.getContactos().size(); i++) {
               String sqlUsuConv = "INSERT INTO USU_CONV (id_conversacion, id_usuario) " +
                       "VALUES(" + conversacion.getId_conversacion()
                       + ", " + conversacion.getContactos().get(i).getId()+ ")";
               Log.d("Datos de la talba conversacion", conversacion.getId_conversacion()+"<->"+conversacion.getContactos().get(i));
               baseDatos.execSQL(sqlUsuConv);
           }

           conver = new Conversaciones(conversacion.getId_conversacion(),
                   conversacion.getContactos().get(1).getNombre(),
                   conversacion.getContactos(),0);

           Log.d("Conversacion creada", conver.toString());

       }
        return conver;
    }

    /**
     * Método que elimina los datos de la tabla CONVERSACION.
     *
     * @param baseDatos Objeto SQLiteDatabase
     */
    public void borrarConversaciones(SQLiteDatabase baseDatos) {
        baseDatos.delete("CONVERSACION", null, null);
    }

    /**
     * Método que cuenta el total de conversaciones existentes en base de datos.
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @return numero de conversaciones.
     */
    public int contarConversaciones(SQLiteDatabase baseDatos, Contactos contacto) {
        int lineas;
        String sql = "SELECT COUNT(*) FROM CONVERSACION WHERE nombre = '"+contacto.getNombre()+"'";
        Cursor c = baseDatos.rawQuery(sql, null);
        c.moveToNext();
        lineas = c.getInt(0);
        return lineas;
    }

    /**
     * Método que comprueba si existe conversación por nombre de usario.
     * @param baseDatos
     * @param id
     * @return
     */
    public boolean existeConversacion(SQLiteDatabase baseDatos, int id) {

        String sql = "SELECT * FROM CONVERSACION WHERE id_conversacion= " + id + "";
        Cursor c = baseDatos.rawQuery(sql, null);

        if (!c.moveToFirst()) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Método que devuelve el id de una conversación por nombre de la misma.
     *
     * @param baseDatosL Objeto SQLiteDatabase
     * @param nombre     Nombre de la conversación.
     * @return id de conversación.
     */
    public int recuperarIdConversacionNombre(SQLiteDatabase baseDatosL, String nombre) {
        int id = 0;
        String sql = "SELECT id_conversacion FROM CONVERSACION WHERE NOMBRE ='" + nombre + "'";
        Cursor c = baseDatosL.rawQuery(sql, null);
        if (c.moveToNext()){
            id = c.getInt(0);
        }

        return id;
    }
    

    public Conversaciones recuperarConversacionNombre(SQLiteDatabase baseDatosL,Contactos ContactoOrigen, Contactos contactoDestino) {
    	ArrayList <Contactos> contactos= new ArrayList<Contactos>();
    	contactos.add(ContactoOrigen);
    	contactos.add(contactoDestino);
        Conversaciones conversacion = null;
        String sql = "SELECT id_conversacion, nombre  FROM CONVERSACION WHERE nombre ='" + contactoDestino.getNombre()+"'" ;
        Cursor c = baseDatosL.rawQuery(sql, null);
        c.moveToFirst();
        conversacion= new Conversaciones(c.getInt(0), c.getString(1),contactos , 1);

        return conversacion;
    }

    /**
     *
     * @param baseDatosL
     * @param listacontactos
     * @return
     */
    public Conversaciones recuperarConversacion(SQLiteDatabase baseDatosL, ArrayList<Contactos> listacontactos) {
        Conversaciones conversacion = null;
        String[] valores_recuperar = {"id_conversacion", "nombre", "ocultar"};

        Cursor c = baseDatosL.query("CONVERSACION", valores_recuperar, null, null,
                null, null, null, null);

        c.moveToFirst();


        do {
            conversacion = new Conversaciones(c.getInt(0), c.getString(1), listacontactos, c.getInt(2));
            Log.d("DATOS DE LA CONVERSACION", "" + c.getInt(0) + c.getString(1) + c.getInt(2));

        } while (c.moveToNext());

        c.close();

        return conversacion;
    }



    public ArrayList<Conversaciones> recuperarConversaciones(SQLiteDatabase baseDatosL) {

        ArrayList<Conversaciones> conversaciones = new ArrayList<Conversaciones>();

        ArrayList<Contactos>listacontactos;

        Conversaciones conversacion = null;

        String[] valores_recuperar = {"id_conversacion", "nombre", "ocultar"};

        Cursor c = baseDatosL.query("CONVERSACION", valores_recuperar, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            Log.d("Datos de la conversacion", c.toString());

            do {
                listacontactos = GBDUsuConversaciones.getUsuariosConversacion(baseDatosL,c.getInt(0));
                conversacion = new Conversaciones(c.getInt(0), c.getString(1), listacontactos, c.getInt(2));

                Log.d("Conversacion creada", "->" + conversacion.toString());
                Log.d("numero de conversaciones, ", String.valueOf(conversacion.size()));

                conversaciones.add(conversacion);
            } while (c.moveToNext());
            //		baseDatosL.close();
            c.close();
        }
       // Log.d("numero de conversaciones", String.valueOf(conversacion.size()));
        return conversaciones;
    }


}
