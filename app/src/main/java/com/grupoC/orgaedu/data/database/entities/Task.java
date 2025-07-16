package com.grupoC.orgaedu.data.database.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
@Entity(tableName = "tareas")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titulo")
    private String titulo;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @ColumnInfo(name = "id_curso")
    private int idCurso;

    @ColumnInfo(name = "id_usuario_asignado")
    private int idUsuarioAsignado;

    @ColumnInfo(name = "estado_entrega")
    private String estadoEntrega; // Ej: "Pendiente", "Entregado", "Atrasado"

    @ColumnInfo(name = "estado_completado")
    private boolean estadoCompletado; // True si la tarea está completada, false si no.

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(final Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(final int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdUsuarioAsignado() {
        return idUsuarioAsignado;
    }

    public void setIdUsuarioAsignado(final int idUsuarioAsignado) {
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(final String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public boolean isEstadoCompletado() {
        return estadoCompletado;
    }

    public void setEstadoCompletado(final boolean estadoCompletado) {
        this.estadoCompletado = estadoCompletado;
    }

}
