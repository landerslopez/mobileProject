package com.grupoC.orgaedu.features.courses.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Course;
import com.grupoC.orgaedu.data.repositories.CourseRepository;

public class CourseDetailViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private LiveData<Course> courseDetails;

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseRepository = new CourseRepository(db.courseDao());
    }

    public void setCourseId(int courseId) {
        courseDetails = courseRepository.getCourseById(courseId);
    }

    public LiveData<Course> getCourseDetails() {
        return courseDetails;
    }
}