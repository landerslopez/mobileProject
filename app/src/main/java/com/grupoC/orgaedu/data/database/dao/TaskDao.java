package com.grupoC.orgaedu.data.database.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.grupoC.orgaedu.data.database.entities.Task;

import java.util.Date;
import java.util.List;
@Dao
public interface TaskDao {
    @Insert
    long insertar(Task task);

    @Update
    void actualizar(Task task);

    @Query("SELECT * FROM tareas WHERE id_usuario_asignado = :idUsuario ORDER BY fecha_vencimiento ASC")
    LiveData<List<Task>> obtenerTareasParaUsuario(int idUsuario);

    @Query("SELECT * FROM tareas WHERE id_usuario_asignado = :idUsuario AND id_curso = :idCurso ORDER BY fecha_vencimiento ASC")
    LiveData<List<Task>> obtenerTareasParaUsuarioPorCurso(int idUsuario, int idCurso);

    @Query("SELECT * FROM tareas WHERE id_usuario_asignado = :idUsuario AND fecha_vencimiento BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha_vencimiento ASC")
    LiveData<List<Task>> obtenerTareasParaUsuarioPorRangoDeFechas(int idUsuario, Date fechaInicio, Date fechaFin);

    @Query("SELECT * FROM tareas WHERE id = :idTarea LIMIT 1")
    LiveData<Task> obtenerTareaPorId(int idTarea);

    @Query("DELETE FROM tareas WHERE id = :idTarea")
    void eliminarTarea(int idTarea);
}
