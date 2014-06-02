package com.ChiriChat.model;

/**
 * Created by Alejandro on 30/05/2014.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Conversaciones {

    private int id_conversacion;
    private String nombre;
    private long version;
    private ArrayList <Contactos> contactos;
    private int ocultar;


    public Conversaciones() {
    }

    public Conversaciones(int id_conversacion, String nombre, long version, ArrayList<Contactos> contactos, int ocultar) {
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;
        this.version = version;
        this.contactos = contactos;
        this.ocultar = ocultar;
    }

    public int getId_conversacion() {
        return id_conversacion;
    }

    public void setId_conversacion(int id_conversacion) {
        this.id_conversacion = id_conversacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public ArrayList<Contactos> getContactos() {
        return contactos;
    }

    public void setContactos(ArrayList<Contactos> contactos) {
        this.contactos = contactos;
    }

    public int getOcultar() {
        return ocultar;
    }

    public void setOcultar(int ocultar) {
        this.ocultar = ocultar;
    }

    @Override
    public String toString() {
        return "conversaciones [id_conversacion=" + id_conversacion
                + ", nombre=" + nombre + ", version=" + version
                + ", contactos=" + contactos + ", ocultar=" + ocultar + "]";
    }

/*    public Conversaciones(Parcel parcel) {
        this.id_conversacion = parcel.readInt();
        this.nombre = parcel.readString();
        this.version = parcel.readLong();
        this.contactos = parcel.readArrayList(contactos);
        this.ocultar = ocultar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<Conversaciones> CREATOR = new Creator<Conversaciones>() {

        public Conversaciones createFromParcel(Parcel source) {
            return new Conversaciones(source);
        }
        public Conversaciones[] newArray(int size) {
            return new Conversaciones[size];
        }
    };*/
}

