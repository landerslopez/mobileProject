package com.grupoC.orgaedu.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.grupoC.orgaedu.data.database.entities.Course; // Asegúrate de que la ruta a tu entidad Course sea correcta

import java.util.List;

@Dao // ¡Esta anotación es crucial!
public interface CourseDao {

    /**
     * Inserta un nuevo curso en la base de datos.
     * @param course El objeto Course a insertar.
     * @return El ID de la fila insertada.
     */
    @Insert
    long insert(Course course);

    /**
     * Obtiene todos los cursos de la base de datos, ordenados por nombre.
     * @return LiveData de una lista de todos los cursos.
     */
    @Query("SELECT * FROM cursos ORDER BY nombre_curso ASC")
    LiveData<List<Course>> getAllCourses();

    /**
     * Obtiene los cursos asociados a un profesor específico.
     * @param professorId El ID del profesor.
     * @return LiveData de una lista de cursos del profesor.
     */
    @Query("SELECT * FROM cursos WHERE id_profesor = :professorId ORDER BY nombre_curso ASC")
    LiveData<List<Course>> getCoursesByProfessor(int professorId);

    /**
     * Obtiene los detalles de un curso específico por su ID.
     * @param courseId El ID del curso.
     * @return LiveData del objeto Course.
     */
    @Query("SELECT * FROM cursos WHERE id = :courseId LIMIT 1")
    LiveData<Course> getCourseById(int courseId);
}