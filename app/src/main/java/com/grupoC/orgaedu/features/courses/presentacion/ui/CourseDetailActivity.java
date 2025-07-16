package com.grupoC.orgaedu.features.courses.presentacion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModel;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModelFactory;

public class CourseDetailActivity extends AppCompatActivity {
    public static final String EXTRA_CURSO_ID = "cursoId";
    public static final String EXTRA_USER_ID  = "userId";

    private CoursesViewModel viewModel;
    private TextView tvTitle, tvDescription, tvDate;
    private Button btnEdit, btnDelete;
    private long cursoId;
    private CursoEntity currentCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // 0. Recuperar userId del Intent
        final String currentUserId = getIntent().getStringExtra(EXTRA_USER_ID);
        if (currentUserId == null) {
            Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 1. Instanciar vistas
        tvTitle       = findViewById(R.id.tvDetailTitle);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvDate        = findViewById(R.id.tvDetailDate);
        btnEdit       = findViewById(R.id.btnEdit);
        btnDelete     = findViewById(R.id.btnDelete);

        // 2. Inicializar ViewModel
        viewModel = new ViewModelProvider(
                this,
                new CoursesViewModelFactory(getApplicationContext())
        ).get(CoursesViewModel.class);

        // 3. Leer ID del curso
        cursoId = getIntent().getLongExtra(EXTRA_CURSO_ID, -1);
        if (cursoId == -1) {
            Toast.makeText(this, "ID de curso inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 4. Observar y mostrar datos
        viewModel.selectCurso(cursoId);
        viewModel.cursoSeleccionado.observe(this, curso -> {
            if (curso != null) {
                currentCurso = curso;
                tvTitle.setText(curso.getTitulo());
                tvDescription.setText(curso.getDescripcion());
                tvDate.setText(curso.getFecha());
            }
        });

        // 5. Botón Editar
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCourseActivity.class);
            intent.putExtra(CreateCourseActivity.EXTRA_USER_ID, currentUserId);
            intent.putExtra(CreateCourseActivity.EXTRA_CURSO_ID, cursoId);
            startActivity(intent);
        });

        // 6. Botón Eliminar
        btnDelete.setOnClickListener(v -> {
            viewModel.deleteCurso(cursoId, currentUserId);
            Toast.makeText(this, "Curso eliminado", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
