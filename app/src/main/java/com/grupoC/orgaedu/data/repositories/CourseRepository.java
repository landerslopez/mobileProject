package com.grupoC.orgaedu.data.repositories;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.dao.CourseDao;
import com.grupoC.orgaedu.data.database.entities.Course;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CourseRepository {
    private final CourseDao courseDao;

    public CourseRepository(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Future<Long> insertCourse(Course course) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return courseDao.insert(course);
            }
        });
    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDao.getAllCourses();
    }

    public LiveData<List<Course>> getCoursesByProfessor(int professorId) {
        return courseDao.getCoursesByProfessor(professorId);
    }

    public LiveData<Course> getCourseById(int courseId) {
        return courseDao.getCourseById(courseId);
    }
}