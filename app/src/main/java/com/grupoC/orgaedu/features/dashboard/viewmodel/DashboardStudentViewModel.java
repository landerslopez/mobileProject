package com.grupoC.orgaedu.features.dashboard.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardStudentViewModel extends ViewModel {
    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    public LiveData<String> getUserName() {
        return _userName;
    }

    // Método para establecer el nombre de usuario (ej. llamado desde la Activity)
    public void setUserName(String name) {
        _userName.setValue(name);
    }

    // Aquí iría la lógica para cargar resúmenes de cursos, tareas, etc.
    // Que llamarían a sus respectivos repositorios.
    // public void loadDashboardData() { ... }
}