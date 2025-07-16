package com.grupoC.orgaedu.features.attendance.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.Attendance;
import com.grupoC.orgaedu.data.repositories.AttendanceRepository;
import com.grupoC.orgaedu.data.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AttendanceViewModel extends AndroidViewModel {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository; // Para obtener el usuario logueado

    private final MutableLiveData<Boolean> _attendanceRecorded = new MutableLiveData<>();
    public LiveData<Boolean> getAttendanceRecorded() {
        return _attendanceRecorded;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    private final MutableLiveData<List<Attendance>> _userAttendance = new MutableLiveData<>();
    public LiveData<List<Attendance>> getUserAttendance() {
        return _userAttendance;
    }

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        attendanceRepository = new AttendanceRepository(db.attendanceDao());
        userRepository = new UserRepository(db.userDao());
    }

    public void recordAttendance(int userId, Date date, String status) {
        _errorMessage.postValue(null);
        _attendanceRecorded.postValue(false);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // Opcional: Verificar si ya hay una asistencia registrada para ese día
                Attendance existingAttendance = attendanceRepository.getAttendanceByUserIdAndDate(userId, date).get();
                if (existingAttendance != null) {
                    _errorMessage.postValue("Ya has registrado asistencia para hoy.");
                    return;
                }

                Attendance newAttendance = new Attendance();
                newAttendance.setIdUsuario(userId);
                newAttendance.setFecha(date);
                newAttendance.setEstado(status); // Por ejemplo, "presente"

                long result = attendanceRepository.insertAttendance(newAttendance).get();
                if (result != -1) {
                    _attendanceRecorded.postValue(true);
                } else {
                    _errorMessage.postValue("Error al registrar la asistencia.");
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _errorMessage.postValue("Error en el sistema de asistencia.");
            }
        });
    }

    public void loadUserAttendance(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                List<Attendance> attendanceList = attendanceRepository.getAttendanceByUserId(userId).get();
                _userAttendance.postValue(attendanceList);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _errorMessage.postValue("Error al cargar la asistencia.");
            }
        });
    }

}