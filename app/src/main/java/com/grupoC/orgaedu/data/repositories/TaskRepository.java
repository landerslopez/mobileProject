package com.grupoC.orgaedu.data.repositories;
import androidx.lifecycle.LiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.dao.TaskDao;
import com.grupoC.orgaedu.data.database.entities.Task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(final TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Future<Long> insertarTask(final Task task) {
        return AppDatabase.databaseWriteExecutor.submit(() -> taskDao.insertar(task));
    }

    public void actualizarTask(final Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.actualizar(task));
    }

    public LiveData<List<Task>> obtenerTasksParaUsuario(final int idUsuario) {
        return taskDao.obtenerTareasParaUsuario(idUsuario);
    }

    public LiveData<List<Task>> obtenerTasksParaUsuarioPorCurso(final int idUsuario, final int idCurso) {
        return taskDao.obtenerTareasParaUsuarioPorCurso(idUsuario, idCurso);
    }

    public LiveData<List<Task>> obtenerTasksParaUsuarioPorRangoDeFechas(final int idUsuario, final Date fechaInicio, final Date fechaFin) {
        return taskDao.obtenerTareasParaUsuarioPorRangoDeFechas(idUsuario, fechaInicio, fechaFin);
    }

    public LiveData<Task> obtenerTaskPorId(final int idTask) {
        return taskDao.obtenerTareaPorId(idTask);
    }

    public void eliminarTask(final int idTask) {
        AppDatabase.databaseWriteExecutor.execute(() -> taskDao.eliminarTarea(idTask));
    }
}
