package com.ChiriChat.model;/**
 * Created by neosistec on 13/05/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author Danny Riofrio Jimenez
 */
public class Contactos implements Parcelable{

    private int id;
    private String nombre;
    private String estado;
    private int telefono;

    public Contactos(int id, String nombre, String estado, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.telefono = telefono;
    }

    public Contactos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    ////////////////////////////////////////////
    ///////////////PARCELABLE///////////////////
    ////////////////////////////////////////////

    public Contactos(Parcel in){

        this.id = in.readInt();
        this.nombre = in.readString();
        this.estado = in.readString();
        this.telefono = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.estado);
        dest.writeInt(this.telefono);
    }

    public void readFromParcerl(Parcel source){
        this.id = source.readInt();
        this.nombre = source.readString();
        this.estado = source.readString();
        this.telefono = source.readInt();

    }


    public static final Parcelable.Creator<Contactos> CREATOR = new Creator<Contactos>() {

        public Contactos createFromParcel(Parcel source) {
            return new Contactos(source);
        }
        public Contactos[] newArray(int size) {
            return new Contactos[size];
        }
    };

    @Override
    public String toString() {
        return "Contactos{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
