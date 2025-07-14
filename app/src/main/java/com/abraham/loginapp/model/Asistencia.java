package com.abraham.loginapp.model;

public class Asistencia {
    private int id;
    private int userId;      // antes usuarioId
    private String fecha;
    private String estado;

    /** Constructor vacío (para frameworks, serialización, etc.) */
    public Asistencia() { }

    /** Constructor sin id, para insertar nuevos registros */
    public Asistencia(int userId, String fecha, String estado) {
        this.userId = userId;
        this.fecha  = fecha;
        this.estado = estado;
    }

    /** Constructor completo, por si recuperas también el id desde la BD */
    public Asistencia(int id, int userId, String fecha, String estado) {
        this.id     = id;
        this.userId = userId;
        this.fecha  = fecha;
        this.estado = estado;
    }

    // ————— Getters y setters —————

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "id=" + id +
                ", userId=" + userId +
                ", fecha='" + fecha + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
