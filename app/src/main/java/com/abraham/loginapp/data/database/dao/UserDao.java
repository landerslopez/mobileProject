package com.abraham.loginapp.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.abraham.loginapp.data.database.entities.User;
@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Query("SELECT * FROM usuarios WHERE username = :username AND password = :password LIMIT 1")
    User authenticateUser(String username, String password);

    @Query("SELECT COUNT(*) FROM usuarios WHERE username = :username")
    int countUsersByUsername(String username);

    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :email")
    int countUsersByEmail(String email);

    @Query("SELECT * FROM usuarios WHERE id = :userId LIMIT 1")
    LiveData<User> getUserById(int userId); // LiveData para observar cambios
}