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


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ChiriChat.model.Contactos;


public class GestionBaseDatosContactos {


    /**
     * Metodo que insertara un usuario en la base de datos.
     *
     * @param baseDatos
     * @param contacto
     */
    public void insertarUsuario(SQLiteDatabase baseDatos, Contactos contacto) {

        Contactos contactoExistente = contactoPorID(baseDatos, contacto.getId());

        if (!(contactoExistente instanceof Contactos)) {

            String idGcm = (contacto.getIdgcm() != null) ? contacto.getIdgcm() : "null";

            if (contacto != null && contacto != devolverMiContacto(baseDatos)) {
                Log.d("Contacto a insertaner en el metodo de insertar", contacto.toString());
                String sql = "INSERT INTO USUARIOS (id_usuario, nombre, estado, telefono, idgcm)" +
                        "values (" + contacto.getId() + ", " +
                        " '" + contacto.getNombre() + "', " +
                        " '" + contacto.getEstado() + "', " +
                        contacto.getTelefono() + ", '" +
                        idGcm + "')";
                Log.d("Insertando", "--------------------------------------------");
                baseDatos.execSQL(sql);
            }
        }
    }

    /**
     * Metodo que devolvera mi contacto.
     *
     * @param baseDatos
     * @return
     */
    public Contactos devolverMiContacto(SQLiteDatabase baseDatos) {
        Contactos contacto = null;
        String sql = "SELECT * FROM USUARIOS WHERE idgcm NOT LIKE '%null%'";
        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            contacto = new Contactos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3), c.getString(4));
            Log.w("Mi Contacto recuperado", contacto.toString());
        }
        return contacto;
    }

    /**
     * Método que devuelve todos los contactos que estén en la tabla USUARIOS.
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @return lista de contactos.
     */
    public List<Contactos> recuperarContactos(SQLiteDatabase baseDatos) {

        List<Contactos> lista_contactos = new ArrayList<Contactos>();
        String[] valores_recuperar = {"id_usuario", "nombre", "estado",
                "telefono", "idgcm"};
        Cursor c = baseDatos.query("USUARIOS", valores_recuperar, " idgcm LIKE 'null'", null,
                null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Contactos contactos = new Contactos(c.getInt(0), c.getString(1),
                        c.getString(2), c.getInt(3), c.getString(4));
                lista_contactos.add(contactos);
                Log.d("Contactos recuperado de DB", contactos.toString());
            } while (c.moveToNext());
        }
        //baseDatos.close();
        c.close();
        return lista_contactos;
    }

    /**
     * Método que devuelve un objeto contacto por telefono.
     * Vias futuras
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @param telefono  Numero de teléfono del usuario a recuperar.
     * @return Objeto contacto.
     */
    public Contactos contactoPorTelefono(SQLiteDatabase baseDatos, int telefono) {
        Contactos contacto = null;
        String sql = "SELECT * FROM USUARIOS WHERE telefono=" + telefono + "";
        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            contacto = new Contactos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3), c.getString(4));
            Log.d("Contacto recuperado por telefono", contacto.toString());
        }
        return contacto;
    }

    /**
     * Metodo que me devolvera el contacto buscando por ID de contacto
     *
     * @param baseDatos instancia de la basede datos
     * @param id_usuario id delsuario
     * @return contacto en contrado, si no hay contacto retorna null
     */
    public Contactos contactoPorID(SQLiteDatabase baseDatos, int id_usuario) {
        Contactos contacto = null;
        String sql = "SELECT * FROM USUARIOS WHERE id_usuario=" + id_usuario;
        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            contacto = new Contactos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3), c.getString(4));
            Log.d("Contacto recuperado por ID", contacto.toString());
        }
        return contacto;
    }


    /**
     * Método que borra todos los datos de la tabla USUARIOS.
     * Vias futuras
     *
     * @param baseDatos Objeto SQLiteDatabase
     */
    public void borrarContactos(SQLiteDatabase baseDatos) {
        baseDatos.delete("USUARIOS", null, null);
    }

    /**
     * Método que devuelve el número de usuarios en base de datos.
     * Vias futuras
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @return Número de usuarios.
     */
    public int cuentaUsuarios(SQLiteDatabase baseDatos) {
        int lineas;
        String sql = "SELECT COUNT(*) FROM USUARIOS";
        Cursor c = baseDatos.rawQuery(sql, null);
        c.moveToNext();
        lineas = c.getInt(0);
        //baseDatos.close();
        return lineas;

    }

    /**
     * Metodo que recorrera una lista de contactos y por cada uno llamara al metodo que los actualiza.
     *
     * @param baseDatos
     * @param allContacts
     */
    public void actualizaAllContactos(SQLiteDatabase baseDatos, List<Contactos> allContacts) {
        //Recorro la lista de usuarios que me devuleve el servidor

        for (int i = 0; i < allContacts.size(); i++) {

            Contactos contactoActual = allContacts.get(i);

            if (contactoActual.getId() != devolverMiContacto(baseDatos).getId()) {

                if (contactoPorID(baseDatos, contactoActual.getId()) != null) {
                    actualizaContacto(baseDatos, contactoActual);
                } else {
                    insertarUsuario(baseDatos, contactoActual);
                }
            }

        }
    }

    /**
     * Metodo que actualizara un contactos con los valores del contacto que se le pasa.
     *
     * @param baseDatos
     * @param contact
     */
    public void actualizaContacto(SQLiteDatabase baseDatos, Contactos contact) {
        ContentValues values = new ContentValues();
        values.put("nombre", contact.getNombre());
        values.put("estado", contact.getEstado());
        values.put("telefono", contact.getTelefono());
        values.put("idgcm", "null");
        baseDatos.update("USUARIOS", values, "id_usuario=" + contact.getId(), null);
        Log.d("contacto", contact.toString());
    }

}

