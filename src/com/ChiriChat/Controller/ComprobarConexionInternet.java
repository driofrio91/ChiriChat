package com.ChiriChat.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ComprobarConexionInternet {
	private Context ctx;
	public ComprobarConexionInternet(Context context){
		this.ctx = context;
	}
	
	public boolean available(){
		ConnectivityManager conex = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(conex != null){
			NetworkInfo[] info = conex.getAllNetworkInfo();

			for (int i = 0; i < info.length; i++) {
				if(info[i].getState() == NetworkInfo.State.CONNECTED && info[i].isConnectedOrConnecting()){
					return true;
				}
			}			
		}
		Toast t= Toast.makeText(ctx, "No tenemos conexi�n", Toast.LENGTH_LONG);
		t.show();
		return false;
	}
}
