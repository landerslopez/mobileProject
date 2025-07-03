package com.abraham.loginapp.model;

public class Tarea {
    private int id;
    private int cursoId;
    private String titulo;
    private String descripcion;
    private String comentario;

    public Tarea(int id, int cursoId, String titulo, String descripcion, String comentario) {
        this.id = id;
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.comentario = comentario;
    }

    public int getId() { return id; }
    public int getCursoId() { return cursoId; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getComentario() { return comentario; }

    public void setId(int id) { this.id = id; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
