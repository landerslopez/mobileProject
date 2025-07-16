package com.grupoC.orgaedu.features.courses.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grupoC.orgaedu.R; // Asegúrate de tener este layout
import com.grupoC.orgaedu.data.database.entities.Course;
import com.grupoC.orgaedu.data.database.entities.User; // Asumiendo que pasas el objeto User o su ID
import com.grupoC.orgaedu.features.courses.viewmodel.CourseListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // Para que el profesor añada cursos
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private CourseListViewModel courseListViewModel;
    private CourseAdapter courseAdapter; // Crearás este adaptador
    private RecyclerView recyclerViewCourses;
    private FloatingActionButton fabAddCourse; // Solo visible para profesores
    private User currentUser; // Para almacenar el usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list); // Crea este layout XML

        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        fabAddCourse = findViewById(R.id.fabAddCourse);

        courseListViewModel = new ViewModelProvider(this).get(CourseListViewModel.class);

        // Obtener el usuario actual del intent (pasado desde LoginActivity/Dashboard)
        currentUser = (User) getIntent().getSerializableExtra("logged_in_user"); // Asegúrate de pasar el objeto User como Serializable

        // Configurar RecyclerView
        courseAdapter = new CourseAdapter(new ArrayList<>(), course -> {
            // Acción al hacer clic en un curso (ej. ir a CourseDetailActivity)
            Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
            intent.putExtra("course_id", course.getId());
            startActivity(intent);
        });
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCourses.setAdapter(courseAdapter);

        // Observar los cursos según el rol del usuario
        if (currentUser != null) {
            if ("docente".equals(currentUser.getRole())) {
                fabAddCourse.setVisibility(View.VISIBLE); // Mostrar FAB para profesores
                courseListViewModel.getProfessorCourses(currentUser.getId()).observe(this, courses -> {
                    courseAdapter.updateCourses(courses);
                    if (courses.isEmpty()) {
                        Toast.makeText(this, "No has registrado ningún curso aún.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else { // Alumno
                fabAddCourse.setVisibility(View.GONE); // Ocultar FAB para alumnos
                courseListViewModel.getAllCourses().observe(this, courses -> {
                    courseAdapter.updateCourses(courses);
                    if (courses.isEmpty()) {
                        Toast.makeText(this, "No hay cursos disponibles en este momento.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Error: No se pudo cargar la información del usuario.", Toast.LENGTH_LONG).show();
            finish(); // Cerrar la actividad si no hay usuario
        }

        fabAddCourse.setOnClickListener(v -> {
            // Ir a la actividad para registrar un nuevo curso
            Intent intent = new Intent(CourseListActivity.this, CourseRegistrationActivity.class);
            intent.putExtra("professor_id", currentUser.getId()); // Pasar el ID del profesor
            startActivity(intent);
        });
    }
}