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


package com.ChiriChat.SQLiteDataBaseModel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;
import java.util.List;


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
