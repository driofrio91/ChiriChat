package com.ChiriChat.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.ChiriChat.Controller.ListChats;

/**
 * @author Danny Riofrio Jimenez
 */
public class MyIntentService extends IntentService {

    public static final String ACTION_PROGRESO = "PROGRESO";
    public static final String ACTION_FIN = "FIN";
    private static final int NOTIF_ALERTA_ID = 1;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int iter = intent.getIntExtra("iteraciones", 0);

        for (int i = 1; i <= iter; i++) {
            tareaLarga();

            //Comunicamos el progreso
            Intent bcIntent = new Intent(ListChats.class.getName());
            bcIntent.setAction(ACTION_PROGRESO);
            bcIntent.putExtra("progreso", i * 10);
            Log.d(ACTION_PROGRESO, "-------------------------------------------------------");
            sendBroadcast(bcIntent);
            mostrarNotification(String.valueOf(i * 10), i);
        }

        Intent bcIntent = new Intent();
        bcIntent.setAction(ACTION_FIN);
        sendBroadcast(bcIntent);
    }

    private void tareaLarga() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }

    private void mostrarNotification(String msg, int idUser) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_action_chat)
                        .setContentTitle("Mesaje del usuario " + idUser)
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(this, ListChats.class);
        PendingIntent contIntent = PendingIntent.getActivity(this,
                0, intent, Intent.FILL_IN_ACTION);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[6];
// Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Event tracker details:");
// Moves events into the big view
        for (int i = 0; i < events.length; i++) {

            inboxStyle.addLine(events[i]);
        }
// Moves the big view style object into the notification object.
        mBuilder.setStyle(inboxStyle);

        mBuilder.setContentIntent(contIntent);


        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());

    }
}
