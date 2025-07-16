package com.grupoC.orgaedu.features.courses.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Course;
import com.grupoC.orgaedu.data.repositories.CourseRepository;

import java.util.concurrent.ExecutionException;

public class CourseRegistrationViewModel extends AndroidViewModel {

    private final CourseRepository courseRepository;
    private final MutableLiveData<Boolean> _registrationSuccess = new MutableLiveData<>();
    public LiveData<Boolean> getRegistrationSuccess() {
        return _registrationSuccess;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    public CourseRegistrationViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseRepository = new CourseRepository(db.courseDao());
    }

    public void registerCourse(String courseName, String description, int professorId) {
        _errorMessage.postValue(null); // Clear previous errors

        if (courseName.isEmpty() || description.isEmpty()) {
            _errorMessage.postValue("Por favor, completa todos los campos.");
            _registrationSuccess.postValue(false);
            return;
        }

        Course newCourse = new Course();
        newCourse.setNombreCurso(courseName);
        newCourse.setDescripcion(description);
        newCourse.setIdProfesor(professorId);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                long result = courseRepository.insertCourse(newCourse).get();
                if (result > 0) { // If insertion was successful, Room returns the new row ID
                    _registrationSuccess.postValue(true);
                } else {
                    _errorMessage.postValue("Error al registrar el curso. Inténtalo de nuevo.");
                    _registrationSuccess.postValue(false);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _errorMessage.postValue("Error en el sistema de registro de cursos.");
                _registrationSuccess.postValue(false);
            }
        });
    }
}