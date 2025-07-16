package com.grupoC.orgaedu.data.repositories;

import androidx.lifecycle.LiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.dao.GradeDao;
import com.grupoC.orgaedu.data.database.entities.Grade;

import java.util.List;
import java.util.concurrent.Future;

public class GradeRepository {
    private final GradeDao gradeDao;

    public GradeRepository(final GradeDao gradeDao) {
        this.gradeDao = gradeDao;
    }

    public Future<Long> insertarGrade(final Grade grade) {
        return AppDatabase.databaseWriteExecutor.submit(() -> gradeDao.insertar(grade));
    }

    public void actualizarGrade(final Grade grade) {
        AppDatabase.databaseWriteExecutor.execute(() -> gradeDao.actualizar(grade));
    }

    public void eliminarGrade(final int gradeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> gradeDao.eliminar(gradeId));
    }

    public LiveData<List<Grade>> obtenerGradesParaUsuario(final int userId) {
        return gradeDao.obtenerNotasParaUsuario(userId);
    }

    public LiveData<List<Grade>> obtenerGradesParaUsuarioPorCurso(final int userId, final int courseId) {
        return gradeDao.obtenerNotasParaUsuarioPorCurso(userId, courseId);
    }

    public LiveData<Grade> obtenerGradePorId(final int gradeId) {
        return gradeDao.obtenerNotaPorId(gradeId);
    }

    public LiveData<List<Grade>> obtenerGradesRegistradasPorProfesor(final int idProfesor) {
        return gradeDao.obtenerNotasRegistradasPorProfesor(idProfesor);
    }
}