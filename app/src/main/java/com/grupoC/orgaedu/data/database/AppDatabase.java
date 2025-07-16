package com.grupoC.orgaedu.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.grupoC.orgaedu.data.database.converters.DateConverter;
import com.grupoC.orgaedu.data.database.dao.TaskDao;
import com.grupoC.orgaedu.data.database.entities.Task;
import com.grupoC.orgaedu.data.database.dao.UserDao;
import com.grupoC.orgaedu.data.database.entities.User;
import com.grupoC.orgaedu.data.database.dao.GradeDao;
import com.grupoC.orgaedu.data.database.entities.Grade;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Task.class,Grade.class}, version = 6, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract GradeDao gradeDao();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "orgaedu.db")
                            // Callback para insertar datos iniciales al crear la DB por primera vez
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback para precargar la base de datos con datos iniciales.
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                final UserDao userDao = INSTANCE.userDao();
                final TaskDao taskDao = INSTANCE.taskDao();
                final GradeDao gradeDao = INSTANCE.gradeDao();

                // Insertar usuarios de prueba
                User adminUser = new User();
                adminUser.setNombre("Administrador");
                adminUser.setCorreo("admin@ejemplo.com");
                adminUser.setUsername("admin");
                adminUser.setPassword("admin");
                adminUser.setRole("docente");

                User juanUser = new User();
                juanUser.setNombre("Juan Pérez");
                juanUser.setCorreo("juan@ejemplo.com");
                juanUser.setUsername("juan");
                juanUser.setPassword("12345");
                juanUser.setRole("alumno");

            });
        }
    };
}