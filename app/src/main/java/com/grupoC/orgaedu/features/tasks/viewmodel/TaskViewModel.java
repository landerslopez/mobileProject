package com.grupoC.orgaedu.features.tasks.viewmodel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Task;
import com.grupoC.orgaedu.data.repositories.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class TaskViewModel extends AndroidViewModel{
    private final TaskRepository taskRepository;
    private LiveData<List<Task>> allTasksForUser;
    private final MutableLiveData<String> _message = new MutableLiveData<>();

    public LiveData<String> getMessage() {
        return _message;
    }

    public TaskViewModel(@NonNull final Application application) {
        super(application);
        final AppDatabase db = AppDatabase.getDatabase(application);
        taskRepository = new TaskRepository(db.taskDao());
    }

    public void initializeTasksForUser(final int userId) {
        allTasksForUser = taskRepository.obtenerTasksParaUsuario(userId);
    }

    public LiveData<List<Task>> getAllTasksForUser() {
        return allTasksForUser;
    }

    public LiveData<List<Task>> getTasksForUserByCourse(final int userId, final int courseId) {
        return taskRepository.obtenerTasksParaUsuarioPorCurso(userId, courseId);
    }

    public LiveData<List<Task>> getTasksForUserByDateRange(final int userId, final Date startDate, final Date endDate) {
        return taskRepository.obtenerTasksParaUsuarioPorRangoDeFechas(userId, startDate, endDate);
    }

    public void createTask(final Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                final long result = taskRepository.insertarTask(task).get();
                if (result > 0) {
                    _message.postValue("¡Tarea creada exitosamente!");
                } else {
                    _message.postValue("Fallo al crear la tarea.");
                }
            } catch (final ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _message.postValue("Error al crear la tarea: " + e.getMessage());
            }
        });
    }

    public void updateTask(final Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskRepository.actualizarTask(task);
            _message.postValue("¡Tarea actualizada exitosamente!");
        });
    }

    public void deleteTask(final int taskId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskRepository.eliminarTask(taskId);
            _message.postValue("¡Tarea eliminada exitosamente!");
        });
    }

    public LiveData<Task> getTaskById(final int taskId) {
        return taskRepository.obtenerTaskPorId(taskId);
    }
}
