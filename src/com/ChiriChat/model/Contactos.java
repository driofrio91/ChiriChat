package com.ChiriChat.model;/**
 * Created by neosistec on 13/05/2014.
 */

/**
 * @author Danny Riofrio Jimenez
 */
public class Contactos {

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
}
