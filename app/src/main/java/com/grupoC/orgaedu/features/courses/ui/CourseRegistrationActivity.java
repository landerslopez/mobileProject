package com.grupoC.orgaedu.features.courses.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R; // Asegúrate de tener este layout
import com.grupoC.orgaedu.features.courses.viewmodel.CourseRegistrationViewModel;

public class CourseRegistrationActivity extends AppCompatActivity {

    private EditText editTextCourseName, editTextCourseDescription;
    private Button buttonRegisterCourse;
    private CourseRegistrationViewModel registrationViewModel;
    private int professorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration); // Crea este layout XML

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseDescription = findViewById(R.id.editTextCourseDescription);
        buttonRegisterCourse = findViewById(R.id.buttonRegisterCourse);

        registrationViewModel = new ViewModelProvider(this).get(CourseRegistrationViewModel.class);

        // Obtener el ID del profesor del intent
        professorId = getIntent().getIntExtra("professor_id", -1);
        if (professorId == -1) {
            Toast.makeText(this, "Error: ID de profesor no proporcionado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Observar el resultado del registro
        registrationViewModel.getRegistrationSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(CourseRegistrationActivity.this, "Curso registrado exitosamente.", Toast.LENGTH_SHORT).show();
                finish(); // Cerrar la actividad después de un registro exitoso
            }
        });

        // Observar mensajes de error
        registrationViewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(CourseRegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        buttonRegisterCourse.setOnClickListener(v -> {
            String courseName = editTextCourseName.getText().toString().trim();
            String description = editTextCourseDescription.getText().toString().trim();
            registrationViewModel.registerCourse(courseName, description, professorId);
        });
    }
}