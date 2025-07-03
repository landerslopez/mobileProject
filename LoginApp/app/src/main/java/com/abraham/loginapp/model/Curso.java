package com.abraham.loginapp.model;

import java.io.Serializable;

public class Curso implements Serializable {
    private int id;
    private String nombre;
    private int docenteId;

    // Constructor vacío requerido por algunas funciones (por ejemplo, Firebase, adapters, etc.)
    public Curso() {
    }

    // Constructor principal
    public Curso(int id, String nombre, int docenteId) {
        this.id = id;
        this.nombre = nombre;
        this.docenteId = docenteId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDocenteId() {
        return docenteId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }
}
