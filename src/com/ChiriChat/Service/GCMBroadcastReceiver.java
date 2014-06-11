package com.ChiriChat.Service;/**
 * Created by neosistec on 11/06/2014.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.ChiriChat.model.Mensajes;

public class GCMBroadcastReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ComponentName comp =
                new ComponentName(context.getPackageName(),
                        GCMIntentService.class.getName());

        Log.d("ComponentName",comp.toString());

        Mensajes men = new Mensajes(intent.getExtras().get("mensaje").toString());

        Log.d("Contexto =>", men.toString());
//        Log.d("Contexto =>" , intent.getExtras().get("contacto").toString());

        startWakefulService(context, (intent.setComponent(comp)));

        setResultCode(Activity.RESULT_OK);
    }
}
