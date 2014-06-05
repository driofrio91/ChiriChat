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

    /**
     * Método que crea una conversación y la inserta en base de datos.
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @param contacto  Contacto con el que se creara la conversacion, su nombre es el nombre de la conversacion
     */
    public Conversaciones crearConversacion(SQLiteDatabase baseDatos, Contactos contacto, ArrayList<Contactos> contactos) {

        Conversaciones conver = null;

       if(existeConversacion(baseDatos, contacto.getNombre())) {

           conver = recuperarConversacion(baseDatos,contactos);


       }else{

           String sql = "INSERT INTO CONVERSACION (nombre,version,ocultar)"
                   + " values ('" + contacto.getNombre() + "',0,'false')";
           baseDatos.execSQL(sql);
           //Recupero el id de la conversacion qe acabo de crear
           int idConversacion = recuperarIdConversacionNombre(baseDatos, contacto.getNombre());
           for (int i = 0; i < contactos.size(); i++) {
               String sqlUsuConv = "INSERT INTO USU_CONV (id_conversacion, id_usuario) " +
                       "VALUES(" + idConversacion + ", " + contactos.get(i).getId() + ")";
               baseDatos.execSQL(sqlUsuConv);
           }

           conver = new Conversaciones(idConversacion, contacto.getNombre(), 1, contactos, 0);
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
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @param nombre    Nombre de usuario destino.
     * @return true si existe, false si no existe.
     */
    public boolean existeConversacion(SQLiteDatabase baseDatos, String nombre) {
        String sql = "SELECT * FROM CONVERSACION WHERE NOMBRE='" + nombre + "'";
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
        conversacion= new Conversaciones(c.getInt(0), c.getString(1), 1,contactos , 1);

        return conversacion;
    }
    
    public Conversaciones recuperarConversacion(SQLiteDatabase baseDatosL, ArrayList<Contactos> listacontactos) {
        Conversaciones conversacion = null;
        String[] valores_recuperar = {"id_conversacion", "nombre", "version", "ocultar"};

        Cursor c = baseDatosL.query("CONVERSACION", valores_recuperar, null, null,
                null, null, null, null);

        c.moveToFirst();


        do {
            conversacion = new Conversaciones(c.getInt(0), c.getString(1), c.getInt(2), listacontactos, c.getInt(3));
            Log.d("DATOS DE LA CONVERSACION", "" + c.getInt(0) + c.getString(1) + c.getInt(2) + c.getString(3));

        } while (c.moveToNext());

        c.close();

        return conversacion;
    }



    public ArrayList<Conversaciones> recuperarConversaciones(SQLiteDatabase baseDatosL) {

        ArrayList<Conversaciones> conversaciones = new ArrayList<Conversaciones>();

        ArrayList<Contactos>listacontactos;

        Conversaciones conversacion = null;

        String[] valores_recuperar = {"id_conversacion", "nombre", "version", "ocultar"};

        Cursor c = baseDatosL.query("CONVERSACION", valores_recuperar, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            Log.d("Datos de la conversacion", c.toString());

            do {
                listacontactos = GestionBaseDatosUsu_Conv.getUsuariosConversacion(baseDatosL,c.getInt(0));
                conversacion = new Conversaciones(c.getInt(0), c.getString(1), c.getInt(2), listacontactos, c.getInt(3));

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
