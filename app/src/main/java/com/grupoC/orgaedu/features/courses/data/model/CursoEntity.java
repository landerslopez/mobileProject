package com.grupoC.orgaedu.features.courses.data.model;

public class CursoEntity {
    private long id;
    private String titulo;
    private String descripcion;
    private String fecha;     // ISO-8601, p.ej. "2025-07-15"
    private String userId;    // profesor o estudiante

    public CursoEntity() { }

    public CursoEntity(long id, String titulo, String descripcion, String fecha, String userId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.userId = userId;
    }

    // Getters y setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
