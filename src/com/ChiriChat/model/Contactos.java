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
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Danny Riofrio Jimenez
 */
public class Contactos implements Parcelable{

    private int id_usuario;
    private String nombre;
    private String estado;
    private int telefono;
    private String idgcm;

    public Contactos(int id, String nombre, String estado, int telefono, String idgcm) {
        this.id_usuario = id;
        this.nombre = nombre;
        this.estado = estado;
        this.telefono = telefono;
        this.idgcm = idgcm;
    }
    
    public Contactos( String nombre, String estado, int telefono) {
        this.nombre = nombre;
        this.estado = estado;
        this.telefono = telefono;
    }

    public Contactos( int id, String nombre) {
        this.id_usuario=id;
        this.nombre = nombre;

    }

    public Contactos() {
    }

    public int getId() {
        return id_usuario;
    }

    public void setId(int id) {
        this.id_usuario = id;
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

    public String getIdgcm() {
        return idgcm;
    }

    public void setIdgcm(String idgcm) {
        this.idgcm = idgcm;
    }

    @Override
    public String toString() {
        return "{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", telefono=" + telefono +
                ", id_gcm='" + idgcm + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contactos)) return false;

        Contactos contactos = (Contactos) o;

        if (id_usuario != contactos.id_usuario) return false;
        if (telefono != contactos.telefono) return false;
        if (estado != null ? !estado.equals(contactos.estado) : contactos.estado != null) return false;
        if (idgcm != null ? !idgcm.equals(contactos.idgcm) : contactos.idgcm != null) return false;
        if (nombre != null ? !nombre.equals(contactos.nombre) : contactos.nombre != null) return false;

        return true;
    }




    ////////////////////////////////////////////
    ///////////////PARCELABLE///////////////////
    ////////////////////////////////////////////

    /**
     * Constructor que recibe un objeto Parcel y construye una instancia de la clase
     * @param in
     */
    public Contactos(Parcel in){

        this.id_usuario = in.readInt();
        this.nombre = in.readString();
        this.estado = in.readString();
        this.telefono = in.readInt();
        this.idgcm = in.readString();
    }


    /**
     * Describir los tipos de objetos especiales contenidas en representación marshalled de este Parcelable.
     * @return una máscara de bits que indica el conjunto de tipos de objetos especiales movilizados por el Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_usuario);
        dest.writeString(this.nombre);
        dest.writeString(this.estado);
        dest.writeInt(this.telefono);
        dest.writeString(this.idgcm);
    }

  /*  public void readFromParcerl(Parcel source){
        this.id = source.readInt();
        this.nombre = source.readString();
        this.estado = source.readString();
        this.telefono = source.readInt();

    }*/

    /**
     * Crear un Array nueva de la clase Parcelable.
     * @param size
     * @return
     */
    public static final Parcelable.Creator<Contactos> CREATOR = new Parcelable.Creator<Contactos>() {

        public Contactos createFromParcel(Parcel source) {
            return new Contactos(source);
        }
        public Contactos[] newArray(int size) {
            return new Contactos[size];
        }
    };



    ///////////////////////////////////
    //////Constructor JSON/////////////
    ///////////////////////////////////

    public Contactos(JSONObject json) {
        try {
            this.id_usuario = json.getInt("id_usuario");
            this.nombre = json.getString("nombre");
            this.estado = json.getString("estado");
            this.telefono = json.getInt("telefono");
            this.idgcm = json.getString("id_gcm");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
