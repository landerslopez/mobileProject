package com.grupoC.orgaedu.features.auth.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.entities.User;
import com.grupoC.orgaedu.data.repositories.UserRepository;

import java.util.concurrent.ExecutionException;

public class LoginViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    // LiveData para observar el resultado del login (true si es exitoso, false si falla)
    private final MutableLiveData<Boolean> _loginSuccess = new MutableLiveData<>();
    public LiveData<Boolean> getLoginSuccess() {
        return _loginSuccess;
    }

    // LiveData para observar el usuario autenticado (si el login fue exitoso)
    private final MutableLiveData<User> _loggedInUser = new MutableLiveData<>();
    public LiveData<User> getLoggedInUser() {
        return _loggedInUser;
    }

    // LiveData para mensajes de error o información
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        // Inicializar el UserRepository, pasándole el DAO
        // En un proyecto real, se usaría una librería de DI (Hilt/Dagger) para esto.
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = new UserRepository(db.userDao());
    }

    public void login(String username, String password) {
        // Limpiar mensajes de error previos
        _errorMessage.postValue(null);

        // Validaciones básicas antes de consultar la DB
        if (username.isEmpty() || password.isEmpty()) {
            _errorMessage.postValue("Por favor, ingresa usuario y contraseña.");
            _loginSuccess.postValue(false);
            return;
        }

        // Llamar al repositorio en un hilo de fondo
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                User user = userRepository.authenticateUser(username, password).get(); // .get() espera sincrónicamente el resultado
                if (user != null) {
                    _loggedInUser.postValue(user);
                    _loginSuccess.postValue(true);
                } else {
                    _errorMessage.postValue("Credenciales inválidas.");
                    _loginSuccess.postValue(false);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _errorMessage.postValue("Error en el sistema de autenticación.");
                _loginSuccess.postValue(false);
            }
        });
    }
}