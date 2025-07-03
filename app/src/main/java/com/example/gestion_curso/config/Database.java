package com.example.gestion_curso.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestion_curso.model.User;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "rol TEXT NOT NULL)";
        db.execSQL(sql);

        // Datos precargados
        db.execSQL("INSERT INTO usuarios (username, password, rol) VALUES ('admin', 'admin123', 'docente')");
        db.execSQL("INSERT INTO usuarios (username, password, rol) VALUES ('juan', 'alumno123', 'alumno')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("rol", role);
        long result = db.insert("usuarios", null, values);
        return result != -1;
    }

    public User authenticate(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            String role = cursor.getString(cursor.getColumnIndexOrThrow("rol"));
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            return user;
        }
        return null;
    }

}
