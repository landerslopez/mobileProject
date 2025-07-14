package com.abraham.loginapp.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abraham.loginapp.model.User;
import com.abraham.loginapp.model.Asistencia;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME    = "app.db";
    private static final int    DATABASE_VERSION = 2;

    public Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1) Tabla de usuarios
        db.execSQL(
                "CREATE TABLE usuarios (" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " nombre TEXT NOT NULL," +
                        " correo TEXT NOT NULL," +
                        " username TEXT NOT NULL UNIQUE," +
                        " password TEXT NOT NULL," +
                        " rol TEXT NOT NULL" +
                        ")"
        );
        // datos precargados
        db.execSQL("INSERT INTO usuarios (nombre,correo,username,password,rol) " +
                "VALUES('Administrador','admin@ejemplo.com','admin','admin','docente')");
        db.execSQL("INSERT INTO usuarios (nombre,correo,username,password,rol) " +
                "VALUES('Juan Pérez','juan@ejemplo.com','juan','12345','alumno')");

        // 2) Tabla de asistencia
        db.execSQL(
                "CREATE TABLE asistencia (" +
                        " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " user_id INTEGER," +
                        " fecha TEXT NOT NULL," +
                        " estado TEXT NOT NULL," +
                        " FOREIGN KEY(user_id) REFERENCES usuarios(id)" +
                        ")"
        );
        // (Opcional: datos de ejemplo)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Si incrementas DATABASE_VERSION, define aquí migraciones reales.
        // Para simplificar, borramos y recreamos todo:
        db.execSQL("DROP TABLE IF EXISTS asistencia");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }


    // ----------------------
    // CRUD Usuarios
    // ----------------------

    public boolean insertarUsuario(
            String nombre,
            String correo,
            String username,
            String password,
            String rol
    ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre",   nombre);
        cv.put("correo",   correo);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("rol",      rol);
        long id = db.insert("usuarios", null, cv);
        db.close();
        return id != -1;
    }

    public User autenticar(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM usuarios WHERE username=? AND password=?",
                new String[]{ username, password }
        );
        if (c.moveToFirst()) {
            User u = new User();
            u.setId(      c.getInt( c.getColumnIndexOrThrow("id")));
            u.setNombre(  c.getString(c.getColumnIndexOrThrow("nombre")));
            u.setCorreo(  c.getString(c.getColumnIndexOrThrow("correo")));
            u.setUsername(username);
            u.setPassword(password);
            u.setRole(    c.getString(c.getColumnIndexOrThrow("rol")));
            c.close();
            db.close();
            return u;
        }
        c.close();
        db.close();
        return null;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT id FROM usuarios WHERE username=?",
                new String[]{ username }
        );
        boolean ex = c.getCount() > 0;
        c.close();
        db.close();
        return ex;
    }

    public boolean emailExists(String correo) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT id FROM usuarios WHERE correo=?",
                new String[]{ correo }
        );
        boolean ex = c.getCount() > 0;
        c.close();
        db.close();
        return ex;
    }

    // ----------------------
    // CRUD Asistencia
    // ----------------------

    public long agregarAsistencia(
            int userId,
            String fecha,
            String estado
    ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("fecha",    fecha);
        cv.put("estado",   estado);
        long id = db.insert("asistencia", null, cv);
        db.close();
        return id;
    }

    public List<Asistencia> obtenerAsistencias(int userId) {
        List<Asistencia> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM asistencia WHERE user_id=? ORDER BY fecha DESC",
                new String[]{ String.valueOf(userId) }
        );
        while (c.moveToNext()) {
            Asistencia a = new Asistencia();
            a.setId(     c.getInt( c.getColumnIndexOrThrow("id")));
            a.setUsuarioId(
                    c.getInt( c.getColumnIndexOrThrow("user_id")));
            a.setFecha(  c.getString(c.getColumnIndexOrThrow("fecha")));
            a.setEstado( c.getString(c.getColumnIndexOrThrow("estado")));
            lista.add(a);
        }
        c.close();
        db.close();
        return lista;
    }
}
