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


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que gestiona la creación de la base de datos.
 */
public class BDSQLite extends SQLiteOpenHelper {
    //Nombre de nuestra base de datos.
    private static final String BD_NOMBRE = "BDChiriChat";
    //Versión de la BD.
    private static final int BD_VERSION = 1;
    private static BDSQLite mInstance = null;
    
    private Context mCxt;
    
    //Sentencia SQL para crear la tabla de Usuarios
    private static final String SQL_CREATE_CONTACTOS = "CREATE TABLE IF NOT EXISTS USUARIOS"
            + "(id_usuario INTEGER PRIMARY KEY NOT NULL, "
            + "nombre TEXT, "
            + "estado VARCHAR(50), "
            + "telefono INTEGER NOT NULL, "
            + "idgcm TEXT)";

    private static final String SQL_CREATE_CONVERSACION = "CREATE TABLE IF NOT EXISTS CONVERSACION"
            + "(id_conversacion INTEGER PRIMARY KEY NOT NULL, "
            + "nombre TEXT, "
            + "ocultar INTEGER TEXT)";

    private static final String SQL_CREATE_MENSAJES = "CREATE TABLE IF NOT EXISTS MENSAJES"
            + "(id_mensaje INTEGER PRIMARY KEY NOT NULL, "
            + "texto TEXT, "
            + "id_usuario INTEGER, "
            + "id_conversacion INTEGER , "
            + "enviado INTEGER, "
            + "FOREIGN KEY (id_usuario) REFERENCES USUARIOS(id_usuario), "
            + "FOREIGN KEY (id_conversacion) REFERENCES CONVERSACION(id_conversacion))";

    private static final String SQL_CREATE_USU_CONV = "CREATE TABLE IF NOT EXISTS USU_CONV "
            + "(id_conversacion INTEGER NOT NULL, "
            + "id_usuario INTEGER  NULL, "
            + "PRIMARY KEY (id_conversacion, id_usuario)"
            + "FOREIGN KEY (id_conversacion) REFERENCES CONVERSACION(id_conversacion) ON DELETE CASCADE,"
            + "FOREIGN KEY (id_usuario) REFERENCES USUARIOS(id_usuario) ON DELETE CASCADE)";



    public static BDSQLite getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new BDSQLite(ctx.getApplicationContext());
        }
        return mInstance;
    }
    
    /**
     * Constructor de la base de datos.
     *
     * @param context //     * @param BD_NOMBRE
     *                //     * @param factory
     *                //     * @param BD_VERSION
     */
    public BDSQLite(Context context) {
        super(context, BD_NOMBRE, null, BD_VERSION);
        this.mCxt= context;
    }

    /**
     * Método que ejecuta la creación de las tablas.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creamos la nueva base de datos
        db.execSQL(SQL_CREATE_CONTACTOS);
        db.execSQL(SQL_CREATE_CONVERSACION);
        db.execSQL(SQL_CREATE_MENSAJES);
        db.execSQL(SQL_CREATE_USU_CONV);

    }

    /**
     * Método que realiza la actualización de la base de datos.
     * Este método se ejecutara cuando se cambie el número de la base
     * de datos del constructor.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS USUARIOS");
        db.execSQL("DROP TABLE IF EXISTS MENSAJES");
        db.execSQL("DROP TABLE IF EXISTS CONVERSACION");
        db.execSQL("DROP TABLE IF EXISTS USU_CONV");


        //Se crea la nueva versión de la tabla
        db.execSQL(SQL_CREATE_CONTACTOS);
        db.execSQL(SQL_CREATE_CONVERSACION);
        db.execSQL(SQL_CREATE_MENSAJES);
        db.execSQL(SQL_CREATE_USU_CONV);
    }




}

