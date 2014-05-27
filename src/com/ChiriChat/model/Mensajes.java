package com.ChiriChat.model;/**
 * Created by neosistec on 13/05/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Danny Riofrio Jimenez
 */
public class Mensajes implements Parcelable{

    private String cadena;
    private int idMensaje;

    public Mensajes(String cadena, int idMensaje) {
        this.cadena = cadena;
        this.idMensaje = idMensaje;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    @Override
    public String toString() {
        return cadena;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idMensaje);
        dest.writeString(this.cadena);
    }

    public void readFromParcerl(Parcel source){
        this.idMensaje = source.readInt();
        this.cadena = source.readString();

    }
}
