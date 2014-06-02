package com.ChiriChat.SQLiteDataBaseModel;

/**
 * Created by Alejandro on 30/05/2014.
 */
import java.util.ArrayList;

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
     * @param nombre Nombre de la conversación.(Será el nombre del usuario destino).
     */
    public void crearConversacion(SQLiteDatabase baseDatos, String nombre){
        String sql = "INSERT INTO CONVERSACION (nombre,version,ocultar)"
                + " values ('"+nombre+"',0,'false')";
        baseDatos.execSQL(sql);
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
    public int contarConversaciones(SQLiteDatabase baseDatos){
        int lineas;
        String sql = "SELECT COUNT(*) FROM CONVERSACION";
        Cursor c = baseDatos.rawQuery(sql, null);
        c.moveToNext();
        lineas = c.getInt(0);
        return lineas;
    }

    /**
     * Método que comprueba si existe conversación por nombre de usario.
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @param nombre Nombre de usuario destino.
     * @return true si existe, false si no existe.
     */
    public boolean existeConversacion(SQLiteDatabase baseDatos,String nombre){
        String sql = "SELECT COUNT(*) FROM CONVERSACION WHERE NOMBRE='"+nombre+"'";
        Cursor c = baseDatos.rawQuery(sql, null);
        c.moveToNext();
        if(c.getInt(0)<1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Método que devuelve el id de una conversación por nombre de la misma.
     *
     * @param baseDatosL Objeto SQLiteDatabase
     * @param nombre Nombre de la conversación.
     * @return id de conversación.
     */
    public int recuperarConversacion(SQLiteDatabase baseDatosL,String nombre){
        int id;
        String sql = "SELECT id_conversacion FROM CONVERSACION WHERE NOMBRE ='"+ nombre+"'";
        Cursor c = baseDatosL.rawQuery(sql, null);
        c.moveToNext();
        id = c.getInt(0);
        return id;
    }

    public Conversaciones recuperarConversacion(SQLiteDatabase baseDatosL,ArrayList<Contactos> listacontactos){
        Conversaciones conversacion= null;
        String[] valores_recuperar = {"id_conversacion","nombre", "version", "ocultar"};

        Cursor c = baseDatosL.query("CONVERSACION", valores_recuperar, null, null,
                null, null, null, null);

        c.moveToFirst();
        //Aqui hago un log de los datos de la conversación

        do {
            conversacion = new Conversaciones(c.getInt(0), c.getString(1), c.getInt(2),listacontactos,c.getInt(3));
            Log.d("DATOS DE LA CONVERSACION",""+c.getInt(0)+c.getString(1)+c.getInt(2)+c.getString(3));
            //Log.d("",conversacion.toString());
        } while (c.moveToNext());
//		baseDatosL.close();
        c.close();

        return conversacion;
    };
}
