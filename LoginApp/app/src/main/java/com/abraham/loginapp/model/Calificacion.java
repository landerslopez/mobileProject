package com.abraham.loginapp.model;

public class Calificacion {
    private int id;
    private int tareaId;
    private int estudianteId;
    private int nota;
    private String nombreEstudiante; // NUEVO CAMPO

    public Calificacion(int id, int tareaId, int estudianteId, int nota) {
        this.id = id;
        this.tareaId = tareaId;
        this.estudianteId = estudianteId;
        this.nota = nota;
    }

    // Nuevo constructor con nombre del estudiante
    public Calificacion(int id, int tareaId, int estudianteId, int nota, String nombreEstudiante) {
        this.id = id;
        this.tareaId = tareaId;
        this.estudianteId = estudianteId;
        this.nota = nota;
        this.nombreEstudiante = nombreEstudiante;
    }

    public int getId() { return id; }
    public int getTareaId() { return tareaId; }
    public int getEstudianteId() { return estudianteId; }
    public int getNota() { return nota; }

    // Nuevo getter
    public String getNombreEstudiante() { return nombreEstudiante; }

    // Nuevo setter
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
