package com.abraham.loginapp.features.auth.viewmodel;

import android.app.Application;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abraham.loginapp.data.database.AppDatabase;
import com.abraham.loginapp.data.database.entities.User;
import com.abraham.loginapp.data.repositories.UserRepository;

import java.util.concurrent.ExecutionException;

public class RegisterViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    private final MutableLiveData<Boolean> _registerSuccess = new MutableLiveData<>();
    public LiveData<Boolean> getRegisterSuccess() {
        return _registerSuccess;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userRepository = new UserRepository(db.userDao());
    }

    public void registerUser(String fullName, String email, String username, String password, String role) {
        _errorMessage.postValue(null); // Limpiar mensajes de error previos
        _registerSuccess.postValue(false); // Resetear el estado de éxito

        // Validaciones de entrada (la mayoría se movieron del Activity al ViewModel)
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            _errorMessage.postValue("Completa todos los campos.");
            return;
        }

        if (fullName.length() > 50) {
            _errorMessage.postValue("El nombre completo debe tener máximo 50 caracteres.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.endsWith("@gmail.com")) {
            _errorMessage.postValue("Correo inválido o no es de Gmail.");
            return;
        }

        // Validación de contraseña única no es segura. Si el objetivo es que no se repita en DB,
        // esto es incorrecto para contraseñas hasheadas. Si es texto plano (NO RECOMENDADO),
        // aún es una mala práctica de seguridad. Lo dejo como lo tenías, pero con advertencia.
        // if (userRepository.passwordExists(password).get()) { // Esto requeriría un método en UserRepository
        //    _errorMessage.postValue("La contraseña ya está registrada. Usa una diferente.");
        //    return;
        // }

        // Ejecutar las validaciones de existencia en un hilo de fondo
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                if (userRepository.userExists(username).get()) {
                    _errorMessage.postValue("El nombre de usuario ya está registrado.");
                    return;
                }
                if (userRepository.emailExists(email).get()) {
                    _errorMessage.postValue("El correo electrónico ya está registrado.");
                    return;
                }

                // Si todas las validaciones pasan, proceder con la inserción
                User newUser = new User();
                newUser.setNombre(fullName);
                newUser.setCorreo(email);
                newUser.setUsername(username);
                newUser.setPassword(password); // ¡ADVERTENCIA: No se debe almacenar contraseñas en texto plano! Usa hashing.
                newUser.setRole(role);

                long result = userRepository.insertUser(newUser).get();
                if (result != -1) {
                    _registerSuccess.postValue(true);
                } else {
                    _errorMessage.postValue("Error al registrar el usuario.");
                }

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                _errorMessage.postValue("Error en el sistema de registro.");
            }
        });
    }
}