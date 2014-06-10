package com.ChiriChat.Service;/**
 * Created by neosistec on 10/06/2014.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.ChiriChat.Controller.ListChats;

/**
 * @author Danny Riofrio Jimenez
 */
public class ProgressReceiver extends BroadcastReceiver {

    private Context ctx;

    public ProgressReceiver(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(MyIntentService.ACTION_PROGRESO)) {
            int prog = intent.getIntExtra("progreso", 0);
            Log.i("Extra del onRecive", "" + prog);
        }
        else if(intent.getAction().equals(MyIntentService.ACTION_FIN)) {
            Toast.makeText(ctx, "Tarea finalizada!", Toast.LENGTH_LONG).show();
        }
    }
}
