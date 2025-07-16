package com.grupoC.orgaedu.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cursos")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre_curso")
    private String nombreCurso;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "id_profesor")
    private int idProfesor; // Foreign key to User table for the professor


    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIdProfesor() { return idProfesor; }
    public void setIdProfesor(int idProfesor) { this.idProfesor = idProfesor; }
}
