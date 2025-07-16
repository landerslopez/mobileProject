package com.grupoC.orgaedu.features.notas;

public class Nota {
    private String curso;
    private String descripcion;
    private double calificacion;

    public Nota(String curso, String descripcion, double calificacion) {
        this.curso = curso;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
    }

    public String getCurso() { return curso; }
    public String getDescripcion() { return descripcion; }
    public double getCalificacion() { return calificacion; }
}

