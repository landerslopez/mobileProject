package com.abraham.loginapp.data.repositories;

import com.abraham.loginapp.data.database.AppDatabase;
import com.abraham.loginapp.data.database.dao.UserDao;
import com.abraham.loginapp.data.database.entities.User;

import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class UserRepository {
    private final UserDao userDao; // Referencia al DAO
    // Si tuvieras AppPreferences para guardar el estado de login, lo inyectarías aquí también.
    // private final AppPreferences appPreferences;

    public UserRepository(UserDao userDao) { // Inyectar el DAO en el constructor
        this.userDao = userDao;
        // this.appPreferences = appPreferences;
    }

    // Método para insertar un nuevo usuario
    public Future<Long> insertUser(User user) {
        // Ejecutar en un hilo de fondo usando el Executor de AppDatabase
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return userDao.insert(user);
            }
        });
    }

    // Método para autenticar un usuario
    public Future<User> authenticateUser(String username, String password) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return userDao.authenticateUser(username, password);
            }
        });
    }

    // Método para verificar si un nombre de usuario ya existe
    public Future<Boolean> userExists(String username) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return userDao.countUsersByUsername(username) > 0;
            }
        });
    }

    // Método para verificar si un correo ya existe
    public Future<Boolean> emailExists(String email) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return userDao.countUsersByEmail(email) > 0;
            }
        });
    }

    // NOTA: Para la contraseña, la validación de "única" no es una buena práctica de seguridad.
    // Las contraseñas deben ser únicas por naturaleza de su hashing, no por existir en la DB.
    // Esta lógica la eliminaríamos o la manejaríamos de otra forma (ej. salt + hash).
    // Si la necesitas mantener, podrías agregar un método similar:
    /*
    public Future<Boolean> passwordExists(String password) {
        return AppDatabase.databaseWriteExecutor.submit(() -> {
            // Esto solo es válido si la contraseña se almacena en texto plano (NO RECOMENDADO)
            return userDao.countPasswords(password) > 0;
        });
    }
    */
}