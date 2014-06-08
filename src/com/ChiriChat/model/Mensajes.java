package com.ChiriChat.model;/**
 * Created by danny on 13/05/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Mensajes(String cadena, int idUsuario) {
        this.cadena = cadena;
        this.enviado = false;
        this.idUsuario = idUsuario;
    }

    public Mensajes(String cadena) {
        this.cadena = cadena;
    }

    public Mensajes(JSONObject json) {
        try {
            this.idMensaje = json.getInt("id");
            this.idConversacion = json.getInt("idConver");
            this.cadena = json.getString("texto");
            this.enviado = true;
            this.idUsuario = json.getInt("idUsuario");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


       /* @Override
    public String toString() {
        return cadena;
    }*/

    @Override
    public String toString() {
        return "Mensajes{" +
                "idMensaje=" + idMensaje +
                ", idConversacion=" + idConversacion +
                ", cadena='" + cadena + '\'' +
                ", enviado=" + enviado +
                ", idUsuario=" + idUsuario +
                '}';
    }


    ///////////////////////////////////
    ///////////PARCELABLE//////////////
    ///////////////////////////////////

    /**
     * Constructor que recibe un objeto Parcel y construye una instancia de la clase
     * @param in
     */
    public Mensajes(Parcel in) {
        this.idMensaje = in.readInt();
        this.idConversacion = in.readInt();
        this.cadena = in.readString();
        this.enviado = (in.readInt() == 0) ? false : true;
        this.idUsuario = in.readInt();
    }


    /**
     * Describir los tipos de objetos especiales contenidas en representación marshalled de este Parcelable.
     * @return una máscara de bits que indica el conjunto de tipos de objetos especiales movilizados por el Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Escribir el objeto en un fichero
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idMensaje);
        dest.writeInt(this.idConversacion);
        dest.writeString(this.cadena);
        dest.writeInt(this.enviado ? 1 : 0);
    }
/*
    public void readFromParcerl(Parcel source){
        this.idMensaje = source.readInt();
        this.cadena = source.readString();

    }*/


    /**
     *  Objeto que implementa la interfaz Parcelable.Creator
     */
    public static final Parcelable.Creator<Mensajes> CREATOR = new Parcelable.Creator<Mensajes>() {
        /**
         * Crea una nueva instancia de la clase Parcelable, instancia desde la parcela determinada cuyos datos habían
         * sido previamente escrito por Parcelable.writeToParcel () .
         * @param source
         * @return
         */
        public Mensajes createFromParcel(Parcel source) {
            return new Mensajes(source);
        }

        /**
         * Crear un Array nueva de la clase Parcelable.
         * @param size
         * @return
         */
        public Mensajes[] newArray(int size) {
            return new Mensajes[size];
        }
    };
}
