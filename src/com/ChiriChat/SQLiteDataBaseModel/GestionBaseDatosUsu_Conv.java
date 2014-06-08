package com.ChiriChat.SQLiteDataBaseModel;/**
 * Created by danny on 03/06/2014.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny Riofrio Jimenez
 */
public class GestionBaseDatosUsu_Conv {

    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private GestionBaseDatosConversaciones GBDConversacione;

    public GestionBaseDatosUsu_Conv(GestionBaseDatosConversaciones GBDConversacione) {
        this.GBDConversacione = GBDConversacione;
    }

    /**
     * Metodo que devolvera una lista de contactos pertencientes a la conversacion
     *
     * @param id_conversacion
     * @return
     */
    public ArrayList<Contactos> getUsuariosConversacion(SQLiteDatabase baseDatos, int id_conversacion) {
        ArrayList<Contactos> contactos = new ArrayList<Contactos>();

        String sql = "SELECT id_usuario FROM USU_CONV WHERE id_conversacion = " + id_conversacion;

        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                Contactos contacto = GBDContactos.contactoPorID(baseDatos, c.getInt(0));
                contactos.add(contacto);
                Log.d("Contacto recuperado getUsuariosConversacion", contacto.toString());
            } while (c.moveToNext());

        }
        return contactos;
    }

    /**
     * Metodo que me devolvera una lista de conversaciones que pertenezcan al usuario.
     *
     * @return
     */
    public List<Conversaciones> getConversacionesUsuario() {
        return null;
    }

}
