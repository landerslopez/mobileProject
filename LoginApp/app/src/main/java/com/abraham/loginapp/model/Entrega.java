package com.abraham.loginapp.model;

public class Entrega {
    private int id;
    private int tareaId;
    private int estudianteId;
    private String contenido;

    public Entrega(int id, int tareaId, int estudianteId, String contenido) {
        this.id = id;
        this.tareaId = tareaId;
        this.estudianteId = estudianteId;
        this.contenido = contenido;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTareaId() { return tareaId; }
    public void setTareaId(int tareaId) { this.tareaId = tareaId; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}
