package com.grupoC.orgaedu.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.grupoC.orgaedu.data.database.entities.Course;

import java.util.List;

/**
 * Data Access Object para la entidad Course.
 * Define las operaciones de lectura/escritura sobre la tabla "courses".
 */
@Dao
public interface CourseDao {

    /**
     * Obtiene todos los cursos de la base de datos.
     *
     * @return LiveData que emite la lista completa de Course.
     */
    @Query("SELECT * FROM courses")
    LiveData<List<Course>> getAllCourses();

    /**
     * Obtiene un curso por su identificador.
     *
     * @param id Identificador único del curso.
     * @return LiveData que emite el Course correspondiente, o null si no existe.
     */
    @Query("SELECT * FROM courses WHERE id = :id")
    LiveData<Course> getCourseById(String id);

    /**
     * Inserta un curso en la base de datos. Si ya existe un registro con el mismo ID,
     * reemplaza la entrada anterior (OnConflictStrategy.REPLACE).
     *
     * @param course Objeto Course a insertar o actualizar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    /**
     * Elimina un curso de la base de datos.
     *
     * @param course Objeto Course a borrar (debe existir previamente).
     */
    @Delete
    void deleteCourse(Course course);
}
