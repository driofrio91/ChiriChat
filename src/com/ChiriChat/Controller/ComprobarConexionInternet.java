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
