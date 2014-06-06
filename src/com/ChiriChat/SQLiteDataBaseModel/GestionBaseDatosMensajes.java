package com.ChiriChat.SQLiteDataBaseModel;

/**
 * Created by Alejandro on 30/05/2014.
 */
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ChiriChat.model.Mensajes;


/**
 * Clase que gestiona los mensajes en base de datos.
 *
 *
 */
public class GestionBaseDatosMensajes {


    /**
     * Metodo que insertara un mensaje enla base de datos.
     * @param baseDatos
     * @param mensaje
     */
    public void insertarMensaje(SQLiteDatabase baseDatos, Mensajes mensaje) {
        String sql = "INSERT INTO MENSAJES (id_mensaje, texto, id_usuario, id_conversacion, enviado) "
                + "values ("+ mensaje.getIdMensaje()+ ",  "
                + " '"+mensaje.getCadena()+ "', "
                + mensaje.getIdUsuario() + ", "
                + mensaje.getIdConversacion()+", "
                + mensaje.isEnviado()+")";
        baseDatos.execSQL(sql);
    }


    /**
     * Método que recibe un entero(id_conversacion) y busca mensajes por conversción.
     * Se recuperan todos los mensajes pertenecientes a esa conversación.
     *
     * @param baseDatos Objeto SQLiteDatabase
     * @param conversacion Id_conversación para realizar la busqueda de mensajes.
     * @return lista de mensajes
     */
    public ArrayList<Mensajes> recuperarMensajes(SQLiteDatabase baseDatos,int conversacion) {

        ArrayList<Mensajes> lista_mensajes = new ArrayList<Mensajes>();
        String[] valores_recuperar = { "id_mensaje", "texto", "id_usuario",
                "id_conversacion" };
        Cursor c = baseDatos.query("MENSAJES", valores_recuperar, "id_conversacion ="+conversacion, null,
                null, null, null, null);
        if(c.moveToFirst()){
        do {
            Mensajes mensajes = new Mensajes(c.getInt(0), c.getString(1),
                    c.getInt(2), c.getInt(3));
            lista_mensajes.add(mensajes);
        } while (c.moveToNext());
        //baseDatos.close();
        c.close();}
        return lista_mensajes;
    }

	public Mensajes recuperarMensaje(SQLiteDatabase baseDatos,int conversacion) {
		//int primermensaje=0;
		Mensajes mensaje= null;
		String[] valores_recuperar = { "id_mensaje", "texto", "id_usuario",
				"id_conversacion" };
		Cursor c = baseDatos.query("MENSAJES", valores_recuperar, "id_conversacion= "+conversacion, null,
				null, null, null, null);

		c.moveToLast();
		mensaje = new Mensajes(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3));
		Log.w("MENSAJE RECUPERADO",""+mensaje.toString());

		return mensaje;
	}

    /**
     * Método que devuelve numero de filas de una conversación.
     *
     * @param baseDatos Objeto SQLiteDatabase.
     * @param usuario El id del contacto que envia el mensaje
     * @param conversacion Conversación a la que pertenece el mensaje.
     * @return
     */
    public int contarMensajes(SQLiteDatabase baseDatos, int usuario,
                              int conversacion) {
        int lineas;
        String sql = "SELECT COUNT(*) FROM MENSAJES WHERE id_usuario="
                + usuario + " AND id_conversacion=" + conversacion;
        Cursor c = baseDatos.rawQuery(sql, null);
        c.moveToNext();
        lineas = c.getInt(0);
        return lineas;
    }
}
