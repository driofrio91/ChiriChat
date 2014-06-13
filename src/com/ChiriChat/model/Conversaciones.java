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


package com.ChiriChat.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Clase Conversaciones
 */
public class Conversaciones extends ArrayList<Conversaciones> implements Parcelable{

    private int id_conversacion;
    private String nombre;
    private ArrayList <Contactos> contactos;
    private int ocultar;


    public Conversaciones(String nombre, ArrayList<Contactos> contactos) {
        this.nombre = nombre;
        this.contactos = contactos;
    }

    public Conversaciones(int id_conversacion, String nombre, ArrayList<Contactos> contactos, int ocultar) {
        this.id_conversacion = id_conversacion;
        this.nombre = nombre;
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
                + ", nombre=" + nombre + ", version="
                + ", contactos=" + contactos + ", ocultar=" + ocultar + "]";
    }

    /**
     * Constructor que recibe un objeto Parcel y construye una instancia de la clase
     * @param parcel
     */
    public Conversaciones(Parcel parcel) {
        this.id_conversacion = parcel.readInt();
        this.nombre = parcel.readString();
        contactos = new ArrayList<Contactos>();
        parcel.readList(contactos, Contactos.class.getClassLoader());
        this.ocultar = parcel.readInt();
    }

    /**
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(this.id_conversacion);
        dest.writeString(this.nombre);
        dest.writeList(this.contactos);
        dest.writeInt(this.ocultar);

    }

    public static final Parcelable.Creator<Conversaciones> CREATOR = new Creator<Conversaciones>() {

        public Conversaciones createFromParcel(Parcel source) {
            return new Conversaciones(source);
        }
        public Conversaciones[] newArray(int size) {
            return new Conversaciones[size];
        }
    };


    //////////////////////////////
    //////////JSON///////////////
    ////////////////////////////
    public Conversaciones(JSONObject json) {
        try {
            this.id_conversacion = json.getInt("id_conver");

            this.nombre = json.getString("nombre");

            this.contactos = new ArrayList<Contactos>();

            JSONArray JSONArrayContactos = json.getJSONArray("participantes");

            for (int i = 0; i < JSONArrayContactos.length(); i++) {

                Contactos contacto = new Contactos(JSONArrayContactos.getJSONObject(i));
                contactos.add(contacto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

