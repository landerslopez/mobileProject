package com.abraham.loginapp.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abraham.loginapp.model.Calificacion;
import com.abraham.loginapp.model.Curso;
import com.abraham.loginapp.model.Entrega;
import com.abraham.loginapp.model.Tarea;
import com.abraham.loginapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "instituto.db";
    private static final int DB_VERSION = 2;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, username TEXT, password TEXT, role TEXT)");
        db.execSQL("CREATE TABLE cursos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, docente_id INTEGER)");
        db.execSQL("CREATE TABLE tareas (id INTEGER PRIMARY KEY AUTOINCREMENT, curso_id INTEGER, titulo TEXT, descripcion TEXT, fecha_entrega TEXT)");
        db.execSQL("CREATE TABLE entregas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarea_id INTEGER, estudiante_id INTEGER, contenido TEXT)");
        db.execSQL("CREATE TABLE calificaciones (id INTEGER PRIMARY KEY AUTOINCREMENT, tarea_id INTEGER, estudiante_id INTEGER, nota INTEGER)");

        // Insertar usuario docente por defecto
        db.execSQL("INSERT INTO usuarios (nombre, correo, username, password, role) " +
                "VALUES ('Abraham Pacaya', 'admin@correo.com', 'admin', 'admin', 'docente')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS cursos");
        db.execSQL("DROP TABLE IF EXISTS tareas");
        db.execSQL("DROP TABLE IF EXISTS entregas");
        db.execSQL("DROP TABLE IF EXISTS calificaciones");
        onCreate(db);
    }

    // ------------------ USUARIOS ------------------

    public User login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            user.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            user.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }


    public long registrarUsuario(String nombre, String correo, String username, String password, String role) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("username", username);
        values.put("password", password);
        values.put("role", role);
        return db.insert("usuarios", null, values);
    }

    public List<User> obtenerEstudiantes() {
        SQLiteDatabase db = getReadableDatabase();
        List<User> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE role = 'estudiante'", null);
        while (cursor.moveToNext()) {
            User u = new User();
            u.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            u.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            u.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            u.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            u.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            u.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            lista.add(u);
        }
        cursor.close();
        return lista;
    }

    public User obtenerUsuarioPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            User u = new User();
            u.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            u.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            u.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            u.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            u.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            u.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
            cursor.close();
            return u;
        }
        cursor.close();
        return null;
    }

    // ------------------ CURSOS ------------------

    public List<Curso> obtenerCursosPorDocente(int docenteId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Curso> cursos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM cursos WHERE docente_id = ?", new String[]{String.valueOf(docenteId)});
        while (cursor.moveToNext()) {
            cursos.add(new Curso(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("docente_id"))
            ));
        }
        cursor.close();
        return cursos;
    }

    // Este método se debe agregar dentro de la clase Database
    public void insertarCurso(Curso curso) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", curso.getNombre());
        values.put("docente_id", curso.getDocenteId());

        db.insert("cursos", null, values);
        db.close();
    }

    // ------------------ TAREAS ------------------

    public boolean insertarTarea(int cursoId, String titulo, String descripcion, String fechaEntrega) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curso_id", cursoId);
        values.put("titulo", titulo);
        values.put("descripcion", descripcion);
        values.put("fecha_entrega", fechaEntrega);
        long result = db.insert("tareas", null, values);
        return result != -1;
    }

    public List<Tarea> obtenerTareasPorCurso(int cursoId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Tarea> tareas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tareas WHERE curso_id = ?", new String[]{String.valueOf(cursoId)});
        while (cursor.moveToNext()) {
            tareas.add(new Tarea(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fecha_entrega"))
            ));
        }
        cursor.close();
        return tareas;
    }

    // ------------------ ENTREGAS ------------------

    public List<Entrega> obtenerEntregasPorCurso(int cursoId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Entrega> entregas = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT e.* FROM entregas e JOIN tareas t ON e.tarea_id = t.id WHERE t.curso_id = ?",
                new String[]{String.valueOf(cursoId)}
        );
        while (cursor.moveToNext()) {
            entregas.add(new Entrega(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("tarea_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("contenido"))
            ));
        }
        cursor.close();
        return entregas;
    }

    // ------------------ CALIFICACIONES ------------------

    public List<Calificacion> obtenerCalificacionesPorCurso(int cursoId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Calificacion> calificaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT c.* FROM calificaciones c JOIN tareas t ON c.tarea_id = t.id WHERE t.curso_id = ?",
                new String[]{String.valueOf(cursoId)}
        );
        while (cursor.moveToNext()) {
            calificaciones.add(new Calificacion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("tarea_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("nota"))
            ));
        }
        cursor.close();
        return calificaciones;
    }

    // Dentro de tu clase Database.java

    public boolean actualizarNota(int tareaId, int estudianteId, int nuevaNota) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nota", nuevaNota);

        int filasAfectadas = db.update(
                "calificaciones",                             // Tabla
                values,                                       // Nuevos valores
                "tarea_id = ? AND estudiante_id = ?",         // WHERE
                new String[]{
                        String.valueOf(tareaId),
                        String.valueOf(estudianteId)
                }
        );

        db.close();
        return filasAfectadas > 0;
    }

    public String obtenerNombreEstudiantePorId(int estudianteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nombre = "";

        Cursor cursor = db.rawQuery(
                "SELECT nombre FROM usuarios WHERE id = ? AND role = 'estudiante'",
                new String[]{String.valueOf(estudianteId)}
        );

        if (cursor.moveToFirst()) {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        }

        cursor.close();
        return nombre;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ?", new String[]{username});
        boolean exists = cursor.moveToFirst();  // Devuelve true si encuentra un resultado
        cursor.close();
        return exists;
    }



}
