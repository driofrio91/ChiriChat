package com.ChiriChat.SQLiteDataBaseModel;

/**
 * Created by Alejandro on 30/05/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Clase que gestiona la creación de la base de datos.
 *
 */
public class BDSQLite extends SQLiteOpenHelper{
    //Nombre de nuestra base de datos.
    private static final String  BD_NOMBRE = "BDChiriChat";
    //Versión de la BD.
    private static final int BD_VERSION = 1;

    //Sentencia SQL para crear la tabla de Usuarios
    private static final String SQLCREATECONTACTOS= "CREATE TABLE USUARIOS"
            + "(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "nombre TEXT,estado VARCHAR(50), telefono INTEGER NOT NULL)";

    private static final String SQLCREATEMENSAJES="CREATE TABLE MENSAJES"
            + "(id_mensaje INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + " texto TEXT, id_usuario INTEGER, id_conversacion INTEGER)";
    //	+ " FOREIGN KEY (id_usuario) REFERENCES USUARIOS(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE"
    //+ " FOREIGN KEY (id_mensaje) REFERENCES CONVERSACION(id_conversacion) ON DELETE CASCADE ON UPDATE CASCADE)";

    private static final String SQLCREATECONVERSACION= "CREATE TABLE CONVERSACION"
            + "(id_conversacion INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + " nombre TEXT, version INTEGER, ocultar INTEGER TEXT)";


    /**
     * Constructor de la base de datos.
     *
     * @param context
//     * @param BD_NOMBRE
//     * @param factory
//     * @param BD_VERSION
     */
    public BDSQLite(Context context, CursorFactory factory) {
        super(context, BD_NOMBRE, factory, BD_VERSION);

    }
    /**
     * Método que ejecuta la creación de las tablas.
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creamos la nueva base de datos
        db.execSQL(SQLCREATECONTACTOS);
        db.execSQL(SQLCREATEMENSAJES);
        db.execSQL(SQLCREATECONVERSACION);
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


        //Se crea la nueva versión de la tabla
        db.execSQL(SQLCREATECONTACTOS);
        db.execSQL(SQLCREATEMENSAJES);
        db.execSQL(SQLCREATECONVERSACION);
    }



}

