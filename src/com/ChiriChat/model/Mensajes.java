package com.ChiriChat.model;/**
 * Created by neosistec on 13/05/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Danny Riofrio Jimenez
 */
public class Mensajes implements Parcelable{

    private int idMensaje;
    private int idConversacion;
    private String cadena;
    private boolean enviado;
    private int idUsuario;

    public Mensajes(int idMensaje, String cadena, int idUsuario,int idConversacion) {
        this.idMensaje = idMensaje;
        this.idConversacion = idConversacion;
        this.cadena = cadena;
        this.enviado = false;
        this.idUsuario = idUsuario;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public int getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
