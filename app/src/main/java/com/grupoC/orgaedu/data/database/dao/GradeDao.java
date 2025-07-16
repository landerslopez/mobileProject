package com.grupoC.orgaedu.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupoC.orgaedu.data.database.entities.Grade;

import java.util.List;

@Dao
public interface GradeDao {
    @Insert
    long insertar(Grade grade);

    @Update
    void actualizar(Grade grade);

    @Query("DELETE FROM notas WHERE id = :gradeId")
    void eliminar(int gradeId);

    @Query("SELECT * FROM notas WHERE id_usuario = :userId ORDER BY fecha_registro DESC")
    LiveData<List<Grade>> obtenerNotasParaUsuario(int userId);

    @Query("SELECT * FROM notas WHERE id_usuario = :userId AND id_curso = :courseId ORDER BY fecha_registro DESC")
    LiveData<List<Grade>> obtenerNotasParaUsuarioPorCurso(int userId, int courseId);

    @Query("SELECT * FROM notas WHERE id = :gradeId LIMIT 1")
    LiveData<Grade> obtenerNotaPorId(int gradeId);

    @Query("SELECT * FROM notas WHERE id_profesor = :idProfesor ORDER BY fecha_registro DESC")
    LiveData<List<Grade>> obtenerNotasRegistradasPorProfesor(int idProfesor);
}
