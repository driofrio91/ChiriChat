package com.ChiriChat.model;/**
 * Created by neosistec on 13/05/2014.
 */

/**
 * @author Danny Riofrio Jimenez
 */
public class Mensajes {

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
}
