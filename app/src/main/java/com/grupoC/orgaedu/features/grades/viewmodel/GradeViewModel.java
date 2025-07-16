package com.grupoC.orgaedu.features.grades.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Grade;
import com.grupoC.orgaedu.data.repositories.GradeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GradeViewModel extends AndroidViewModel {
    private final GradeRepository gradeRepository;
    private LiveData<List<Grade>> allGradesForUser;
    private LiveData<List<Grade>> allGradesRegisteredByProfessor;
    private final MutableLiveData<String> _message = new MutableLiveData<>();

    public LiveData<String> getMessage() {
        return _message;
    }

    public GradeViewModel(@NonNull final Application application) {
        super(application);
        final AppDatabase db = AppDatabase.getDatabase(application);
        gradeRepository = new GradeRepository(db.gradeDao());
    }

    public void initializeGradesForUser(final int userId) {
        allGradesForUser = gradeRepository.obtenerGradesParaUsuario(userId);
    }

    public LiveData<List<Grade>> getAllGradesForUser() {
        return allGradesForUser;
    }

    public LiveData<List<Grade>> getGradesForUserByCourse(final int userId, final int courseId) {
        return gradeRepository.obtenerGradesParaUsuarioPorCurso(userId, courseId);
    }

    public void createGrade(final Grade grade) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                final long result = gradeRepository.insertarGrade(grade).get();
                if (result > 0) {
                    _message.postValue("¡Nota registrada exitosamente!");
                } else {
                    _message.postValue("Fallo al registrar la nota.");
                }
            } catch (final ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _message.postValue("Error al registrar la nota: " + e.getMessage());
            }
        });
    }

    public void updateGrade(final Grade grade) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            gradeRepository.actualizarGrade(grade);
            _message.postValue("¡Nota actualizada exitosamente!");
        });
    }

    public void deleteGrade(final int gradeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            gradeRepository.eliminarGrade(gradeId);
            _message.postValue("¡Nota eliminada exitosamente!");
        });
    }

    public LiveData<Grade> getGradeById(final int gradeId) {
        return gradeRepository.obtenerGradePorId(gradeId);
    }

    // Métodos para profesores
    public void initializeGradesRegisteredByProfessor(final int professorId) {
        allGradesRegisteredByProfessor = gradeRepository.obtenerGradesRegistradasPorProfesor(professorId);
    }

    public LiveData<List<Grade>> getGradesRegisteredByProfessor() {
        return allGradesRegisteredByProfessor;
    }
}