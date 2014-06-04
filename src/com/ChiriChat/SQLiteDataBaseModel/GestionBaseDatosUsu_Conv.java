package com.ChiriChat.SQLiteDataBaseModel;/**
 * Created by neosistec on 03/06/2014.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ChiriChat.model.Contactos;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny Riofrio Jimenez
 */
public class GestionBaseDatosUsu_Conv {

    /**
     * Metodo que devolvera una lista de id de usuarios pertencientes a la conversacion
     *
     * @param id_conversacion
     * @return
     */
    public static ArrayList getUsuariosConversacion(SQLiteDatabase baseDatos, int id_conversacion) {
        ArrayList<Contactos> contactos = new ArrayList<Contactos>();

        String sql = "SELECT id_usuario FROM USU_CONV WHERE id_conversacion = " + id_conversacion;

        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                Contactos contacto = GestionBaseDatosContactos.contactoPorID(baseDatos,c.getInt(0));
                contactos.add(contacto);
                Log.d("Contacto recuperado getUsuariosConversacion", contacto.toString());
            } while (c.moveToNext());

        }
        return contactos;
    }

}
