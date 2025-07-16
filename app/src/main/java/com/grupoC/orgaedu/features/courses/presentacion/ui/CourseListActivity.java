package com.grupoC.orgaedu.features.courses.presentacion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModel;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModelFactory;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {
    // Clave para pasar el userId por Intents
    public static final String EXTRA_USER_ID = "userId";

    private CoursesViewModel viewModel;
    private RecyclerView    recyclerView;
    private CourseAdapter   adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // 0. Recuperar userId (debe venir desde quien lanzó esta Activity)
        final String currentUserId = getIntent().getStringExtra(EXTRA_USER_ID);
        if (currentUserId == null) {
            Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 1. Instanciar ViewModel + Factory
        viewModel = new ViewModelProvider(
                this,
                new CoursesViewModelFactory(getApplicationContext())
        ).get(CoursesViewModel.class);

        // 2. Configurar RecyclerView y Adapter con click al detalle
        recyclerView = findViewById(R.id.recyclerCourses);
        adapter = new CourseAdapter(
                new ArrayList<>(),
                curso -> {
                    Intent intent = new Intent(this, CourseDetailActivity.class);
                    intent.putExtra(CourseDetailActivity.EXTRA_CURSO_ID, curso.getId());
                    intent.putExtra(EXTRA_USER_ID, currentUserId);
                    startActivity(intent);
                }
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 3. FAB para crear un nuevo curso
        FloatingActionButton fab = findViewById(R.id.fabAddCourse);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(this, CreateCourseActivity.class);
            i.putExtra(CreateCourseActivity.EXTRA_USER_ID, currentUserId);
            startActivity(i);
        });

        // 4. Observar cambios en la lista de cursos
        viewModel.cursos.observe(this, lista -> adapter.updateData(lista));

        // 5. Cargar cursos del usuario actual
        viewModel.loadCursos(currentUserId);
    }
}
