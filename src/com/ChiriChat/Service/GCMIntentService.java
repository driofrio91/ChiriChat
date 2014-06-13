
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

package com.ChiriChat.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import com.ChiriChat.Controller.Register;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que extiende IntentService, es una tarear asincronica que seguira ejecutandose
 * cuando la aplicacion este cerrada.
 */
public class GCMIntentService extends IntentService {


    private static final int NOTIF_ALERTA_ID = 1;
    //Instancias de la base de datos y de las clases que tiene los metodos que acceden al base de datos
    private BDSQLite bd = BDSQLite.getInstance(this);
    private SQLiteDatabase baseDatos = bd.getWritableDatabase();
    private SQLiteDatabase baseDatosL = bd.getReadableDatabase();
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosMensajes GBDMensajes = new GestionBaseDatosMensajes();
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private Conversaciones conver;

    public GCMIntentService() {
        super("GCMIntentService");
    }

    /**
     * Metodo que se ejecutara siempre, ya que el servicio no para de hacerlo.
     * Es funcionamiento de IntenService es muy parecido a la AsyncTask.
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        //Se obtiene una instancia del GCM
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        //Se obtiene los extras del intent.
        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            //Si hay extras comprobaremos que sehan de tipo mensaje
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //Contruiremos un objeto JSON con los datos de los extras.
                JSONObject newMensaje = null;
                try {
                    newMensaje = new JSONObject(extras.getString("mensaje"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Con el objeto JSON crearemos un objeto mensaje.
                Mensajes men = new Mensajes(newMensaje);

                //Buscamos el contacto que envia el mensaje en la base de datos

                Contactos contacto = GBDContactos.contactoPorID(baseDatosL, men.getIdUsuario());

                if (contacto == null) {
                    //Si el contacto no existe en la base da datos local, countruiremos un contacto nuevo
                    //con nombre desconocido
                    contacto = new Contactos(men.getIdUsuario(), "desconocido" + men.getIdConversacion());
                    //almacenamos el contacto en la base da datos
                    GBDContactos.insertarUsuario(baseDatos, contacto);
                }


                //Creamos una lista con los contactos de la conversacion.
                ArrayList<Contactos> contactConver = new ArrayList<Contactos>();
                //Recuperamos mi contacto de la base da datos y recuperamos el contacto que envia el mensaje
                contactConver.add(GBDContactos.devolverMiContacto(baseDatosL));
                contactConver.add(GBDContactos.contactoPorID(baseDatosL, men.getIdUsuario()));
                //Recuperaremos la conversacion existe
                conver = GBDConversacion.recuperarConversacion(baseDatosL, contactConver, men.getIdConversacion());
                if (conver == null) {
                    //Si la conversacion no existe conuntruira una nueva
                    conver = new Conversaciones(men.getIdConversacion(), contacto.getNombre(), contactConver, 0);
                    //Insertamios la conversacion en la base da datos
                    GBDConversacion.crearConversacion(baseDatos, conver);
                }

                //Inseratbdo el mensaje nuevo
                GBDMensajes.insertarMensaje(baseDatos, men);
                //mosramos la notificacion
                mostrarNotification(men, contacto);
            }
        }
        //Esta linea es muy importante ya que sin esta linea el dispositivo consumira bateria
        //exesivamente.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Mostramos la notificacion, pasandole un objeto mensaje y un objeto contacto, nos abrira
     * la actividad register por si tenemos la ctividad de lista de chats abierta nos la actualize
     * al volver a entrar por Register
     * @param msg
     * @param contacto
     */
    private void mostrarNotification(Mensajes msg, Contactos contacto) {

        String longText = contacto.getNombre()+" : "+msg.getCadena();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d("cadena del mensaje notificacion", msg.getCadena());

        Notification.Builder n =
                new Notification.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_action_chat)
                        .setContentTitle("ChiriChat")
                        .setContentText(msg.getCadena())
                        .setAutoCancel(true)
                        .setWhen(new Date().getTime())
                        .setStyle(new Notification.BigTextStyle().bigText(longText))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(this, Register.class);




        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, intent, 0);


        n.setContentIntent(contIntent);


        mNotificationManager.notify(NOTIF_ALERTA_ID, n.build());

    }

}
