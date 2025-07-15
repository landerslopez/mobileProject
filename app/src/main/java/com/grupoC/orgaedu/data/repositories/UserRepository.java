package com.grupoC.orgaedu.data.repositories;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.dao.UserDao;
import com.grupoC.orgaedu.data.database.entities.User;

import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class UserRepository {
    private final UserDao userDao;
    // private final AppPreferences appPreferences;

    public UserRepository(UserDao userDao) {
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
}