package com.grupoC.orgaedu.features.courses.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData; // Importar MutableLiveData

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Course;
import com.grupoC.orgaedu.data.repositories.CourseRepository;

import java.util.List;

public class CourseListViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> professorCourses; // Para cursos específicos del profesor

    public CourseListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseRepository = new CourseRepository(db.courseDao());
        allCourses = courseRepository.getAllCourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Course>> getProfessorCourses(int professorId) {
        if (professorCourses == null) {
            professorCourses = courseRepository.getCoursesByProfessor(professorId);
        }
        return professorCourses;
    }
}