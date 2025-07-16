package com.grupoC.orgaedu.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "asistencias",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_usuario",
                        onDelete = CASCADE),
                // @ForeignKey(entity = Course.class, // Asumiendo que tendrás una entidad Course
                //             parentColumns = "id",
                //             childColumns = "id_curso",
                //             onDelete = CASCADE)
        })
public class Attendance {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_usuario")
    private int idUsuario;

    // @ColumnInfo(name = "id_curso") // Si asocias la asistencia a un curso
    // private int idCurso;

    @ColumnInfo(name = "fecha")
    private Date fecha; // Usaremos TypeConverter para Date

    @ColumnInfo(name = "estado") // Por ejemplo: "presente", "ausente", "tardanza"
    private String estado;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    // public int getIdCurso() { return idCurso; }
    // public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}