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
     * Metodo que insertara un usuario en la base de datos.
     *
     * @param baseDatos
     * @param contacto
     * @param isLocal
     */
    public void insertarUsuario(SQLiteDatabase baseDatos, Contactos contacto, boolean isLocal) {

    	Contactos contactoExistente = contactoPorID(baseDatos, contacto.getId());
    	
        if (!(contactoExistente instanceof Contactos)) {
			
        	int local = (isLocal) ? 1 : 0;

            if (contacto != null && contacto != devolverMiContacto(baseDatos)) {
                Log.d("Contacto a insertaner en el metodo de insertar", contacto.toString());
                String sql = "INSERT INTO USUARIOS (id_usuario, nombre, estado, telefono, islocal)" +
                        "values (" + contacto.getId() + ", " +
                        " '" + contacto.getNombre() + "', " +
                        " '" + contacto.getEstado() + "', " +
                        contacto.getTelefono() + ", " +
                        local + ")";
                Log.d("Insertando","--------------------------------------------");
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
        String sql = "SELECT * FROM USUARIOS WHERE isLocal = '1'";
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

