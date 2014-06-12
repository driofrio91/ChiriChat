package com.ChiriChat.Service;

import android.app.Notification;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.util.Log;
import com.ChiriChat.Controller.ListChats;
import com.ChiriChat.Controller.ListConversation;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosContactos;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosMensajes;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;
import com.ChiriChat.model.Mensajes;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GCMIntentService extends IntentService {


    private static final int NOTIF_ALERTA_ID = 1;

    private BDSQLite bd = BDSQLite.getInstance(this);
    private SQLiteDatabase baseDatos = bd.getWritableDatabase();
    private SQLiteDatabase baseDatosL = bd.getReadableDatabase();
    private GestionBaseDatosConversaciones GBDConversacion = new GestionBaseDatosConversaciones();
    private GestionBaseDatosMensajes GBDMensajes = new GestionBaseDatosMensajes();
    private GestionBaseDatosContactos GBDContactos = new GestionBaseDatosContactos();
    private Contactos miContacto = GBDContactos.devolverMiContacto(baseDatosL);
    private Conversaciones conver;

    public GCMIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                JSONObject newMensaje = null;

                try {
                    newMensaje = new JSONObject(extras.getString("mensaje"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Mensajes men = new Mensajes(newMensaje);

                //Insertando el contacto nuevo

                Contactos contacto = GBDContactos.contactoPorID(baseDatosL, men.getIdUsuario());

                if (contacto == null) {
                    contacto = new Contactos(men.getIdUsuario(), "desconocido" + men.getIdConversacion());
                    GBDContactos.insertarUsuario(baseDatos, contacto, false);
                }

                //Insertando la conversacion nueva

                ArrayList<Contactos> contactConver = new ArrayList<Contactos>();

                contactConver.add(GBDContactos.devolverMiContacto(baseDatosL));
                contactConver.add(GBDContactos.contactoPorID(baseDatosL, men.getIdUsuario()));

                conver = GBDConversacion.recuperarConversacion(baseDatosL, contactConver, men.getIdConversacion());
                if (conver == null) {
                    conver = new Conversaciones(men.getIdConversacion(), contacto.getNombre(), contactConver, 0);

                    GBDConversacion.crearConversacion(baseDatos, conver);
                }


                //Inseratbdo el mensaje nuevo
                GBDMensajes.insertarMensaje(baseDatos, men);

                mostrarNotification(men, contacto);
            }
        }

        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }


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

        Intent intent;

        if(!contacto.getNombre().equals("desconocido")){

            intent = new Intent(this, ListConversation.class);
            Bundle b = new Bundle();
            b.putParcelable("conversacion", conver);
            intent.putExtras(b);
        }else{

            intent = new Intent(this, ListChats.class);

        }




        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, intent, 0);


        n.setContentIntent(contIntent);


        mNotificationManager.notify(NOTIF_ALERTA_ID, n.build());

    }

}
