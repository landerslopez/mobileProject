package com.grupoC.orgaedu.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

import com.grupoC.orgaedu.data.database.dao.UserDao;
import com.grupoC.orgaedu.data.database.dao.CourseDao; // Importar CourseDao
import com.grupoC.orgaedu.data.database.entities.User;
import com.grupoC.orgaedu.data.database.entities.Course; // Importar Course

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Course.class}, version = 3, exportSchema = false) // Aumentar la versión a 3 y añadir Course.class
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract CourseDao courseDao(); // Añadir esta línea
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "organizaya_escolar.db")
                            // Callback para insertar datos iniciales al crear la DB por primera vez
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Callback para precargar la base de datos con datos iniciales.
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                CourseDao courseDao = INSTANCE.courseDao(); // Obtener CourseDao

                // Insertar usuarios de prueba
                User adminUser = new User();
                adminUser.setNombre("Administrador");
                adminUser.setCorreo("admin@ejemplo.com");
                adminUser.setUsername("admin");
                adminUser.setPassword("admin");
                adminUser.setRole("docente");
                long adminId = userDao.insert(adminUser); // Obtener el ID del admin insertado

                User juanUser = new User();
                juanUser.setNombre("Juan Pérez");
                juanUser.setCorreo("juan@ejemplo.com");
                juanUser.setUsername("juan");
                juanUser.setPassword("12345");
                juanUser.setRole("alumno");
                userDao.insert(juanUser);

//                Course mathCourse = new Course();
//                mathCourse.setNombreCurso("Matemáticas Avanzadas");
//                mathCourse.setDescripcion("Curso de cálculo y álgebra lineal.");
//                mathCourse.setIdProfesor((int) adminId); // Asignar al usuario admin
//                courseDao.insert(mathCourse);
//
//                Course historyCourse = new Course();
//                historyCourse.setNombreCurso("Historia Mundial I");
//                historyCourse.setDescripcion("Exploración de eventos históricos hasta el siglo XVIII.");
//                historyCourse.setIdProfesor((int) adminId); // Asignar al usuario admin
//                courseDao.insert(historyCourse);
            });
        }
    };
}