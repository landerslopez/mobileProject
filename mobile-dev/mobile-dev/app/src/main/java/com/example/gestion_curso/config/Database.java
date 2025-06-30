package com.example.gestion_curso.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestion_curso.model.User;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 2; // ¡IMPORTANTE! Incrementamos la versión

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT NOT NULL, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "rol TEXT NOT NULL)";
        db.execSQL(sql);

        // Insertar datos de prueba
        db.execSQL("INSERT INTO usuarios (nombre, correo, username, password, rol) " +
                "VALUES ('Administrador', 'admin@ejemplo.com', 'admin', 'admin123', 'docente')");
        db.execSQL("INSERT INTO usuarios (nombre, correo, username, password, rol) " +
                "VALUES ('Juan Pérez', 'juan@ejemplo.com', 'juan', 'alumno123', 'alumno')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public boolean insertUser(String nombre, String correo, String username, String password, String rol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("username", username);
        values.put("password", password);
        values.put("rol", rol);
        long result = db.insert("usuarios", null, values);
        return result != -1;
    }

    public User authenticate(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            user.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            user.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("rol")));
            return user;
        }
        return null;
    }
}
