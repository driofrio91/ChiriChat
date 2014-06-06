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


public class GestionBaseDatosContactos {
    /**
     * Método que inserta los usuarios fijos en base de datos.
     *
     * @param baseDatos Objeto SQLiteDatabase
     */
    public void insertarUsuarios(SQLiteDatabase baseDatos) {
        if (baseDatos != null) {
            /*String sql = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Alejandro', 'Probando Contactos', 679965326)";*/
            String sql2 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Danny', 'Probando Contactos2', 679965526)";
            String sql3 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Felix', 'Probando Contactos3', 679966666)";
            String sql4 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Alvaro', 'Probando Contactos4', 679965533)";
            String sql5 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Sergio', 'Probando Contactos5', 679965522)";
            String sql6 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'David', 'Probando Contactos6', 679965511)";
            String sql7 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Jose', 'Probando Contactos7', 679965577)";
            String sql8 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Toni', 'Probando Contactos8', 679965588)";
            String sql9 = "INSERT INTO USUARIOS (nombre, estado, telefono)"
                    + "values ( 'Maria', 'Probando Contactos9', 679965599)";
            //baseDatos.execSQL(sql);
//            baseDatos.execSQL(sql2);
//            baseDatos.execSQL(sql3);
//            baseDatos.execSQL(sql4);
//            baseDatos.execSQL(sql5);
//            baseDatos.execSQL(sql6);
//            baseDatos.execSQL(sql7);
//            baseDatos.execSQL(sql8);
//            baseDatos.execSQL(sql9);

            //baseDatos.close();
        }
    }

    public void insertarUsuario(SQLiteDatabase baseDatos, Contactos contacto, boolean isLocal) {

        int local = (isLocal) ? 1 : 0;

        if (contacto == null) {
            String sql = "INSERT INTO USUARIOS (id_usuario, nombre, estado, telefono, islocal)" +
                    "values (" + contacto.getId() + ", " +
                    " '" + contacto.getNombre() + "', " +
                    " '" + contacto.getEstado() + "', " +
                    contacto.getTelefono() + ", " +
                    local + ")";
            baseDatos.execSQL(sql);
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
        String sql = "SELECT * FROM USUARIOS WHERE isLocal = 1";
        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            contacto = new Contactos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3));
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
                "telefono"};
        Cursor c = baseDatos.query("USUARIOS", valores_recuperar, " isLocal = 0", null,
                null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Contactos contactos = new Contactos(c.getInt(0), c.getString(1),
                        c.getString(2), c.getInt(3));
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
                    c.getString(2), c.getInt(3));
            Log.d("Contacto recuperado por telefono", contacto.toString());
        }
        return contacto;
    }

    /**
     * Metodo que me devolvera el contacto buscando por ID de contacto
     * @param baseDatos
     * @param id_usuario
     * @return
     */
    public static Contactos contactoPorID(SQLiteDatabase baseDatos, int id_usuario) {
        Contactos contacto = null;
        String sql = "SELECT * FROM USUARIOS WHERE id_usuario=" + id_usuario;
        Cursor c = baseDatos.rawQuery(sql, null);

        if (c.moveToFirst()) {
            contacto = new Contactos(c.getInt(0), c.getString(1),
                    c.getString(2), c.getInt(3));
            Log.d("Contacto recuperado por ID", contacto.toString());
        }
        return contacto;
    }


    /**
     * Método que borra todos los datos de la tabla USUARIOS.
     *
     * @param baseDatos Objeto SQLiteDatabase
     */
    public void borrarContactos(SQLiteDatabase baseDatos) {
        baseDatos.delete("USUARIOS", null, null);
    }

    /**
     * Método que devuelve el número de usuarios en base de datos.
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

}

