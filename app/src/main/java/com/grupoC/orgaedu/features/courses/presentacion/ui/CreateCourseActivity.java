package com.grupoC.orgaedu.features.courses.presentacion.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModel;
import com.grupoC.orgaedu.features.courses.presentacion.viewmodel.CoursesViewModelFactory;

public class CreateCourseActivity extends AppCompatActivity {
    public static final String EXTRA_CURSO_ID = "cursoId";
    public static final String EXTRA_USER_ID   = "userId";  // clave para el Intent

    private CoursesViewModel viewModel;
    private EditText etTitle, etDescription, etDate;
    private Button btnSave;

    private Long editingId = null;  // si viene id, estamos en edición

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        // 0. Recuperar el userId del Intent
        final String currentUserId = getIntent().getStringExtra(EXTRA_USER_ID);
        // opcional: validar
        if (currentUserId == null) {
            Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 1. ViewModel
        viewModel = new ViewModelProvider(
                this,
                new CoursesViewModelFactory(getApplicationContext())
        ).get(CoursesViewModel.class);

        // 2. Referencias UI
        etTitle       = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate        = findViewById(R.id.etDate);
        btnSave       = findViewById(R.id.btnSave);

        // 3. ¿Estamos editando?
        if (getIntent().hasExtra(EXTRA_CURSO_ID)) {
            editingId = getIntent().getLongExtra(EXTRA_CURSO_ID, -1);
            viewModel.selectCurso(editingId);
            viewModel.cursoSeleccionado.observe(this, curso -> {
                if (curso != null) {
                    etTitle.setText(curso.getTitulo());
                    etDescription.setText(curso.getDescripcion());
                    etDate.setText(curso.getFecha());
                }
            });
            btnSave.setText("Actualizar");
        }

        // 4. Guardar (crear o actualizar)
        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc  = etDescription.getText().toString().trim();
            String date  = etDate.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date)) {
                Toast.makeText(this, "Título y fecha son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editingId != null) {
                // actualizar
                CursoEntity curso = new CursoEntity(
                        editingId, title, desc, date, currentUserId
                );
                viewModel.updateCurso(curso);
                Toast.makeText(this, "Curso actualizado", Toast.LENGTH_SHORT).show();
            } else {
                // crear
                CursoEntity curso = new CursoEntity(
                        0, title, desc, date, currentUserId
                );
                viewModel.createCurso(curso);
                Toast.makeText(this, "Curso creado", Toast.LENGTH_SHORT).show();
            }
            finish();  // volvemos a la lista
        });
    }
}
