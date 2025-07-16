package com.grupoC.orgaedu.features.courses.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R; // Asegúrate de tener este layout
import com.grupoC.orgaedu.data.database.entities.Course;
import com.grupoC.orgaedu.features.courses.viewmodel.CourseDetailViewModel;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView textViewCourseName, textViewCourseDescription, textViewProfessorId;
    private CourseDetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail); // Crea este layout XML

        textViewCourseName = findViewById(R.id.textViewCourseName);
        textViewCourseDescription = findViewById(R.id.textViewCourseDescription);
        textViewProfessorId = findViewById(R.id.textViewProfessorId); // Mostrar el ID del profesor

        detailViewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);

        int courseId = getIntent().getIntExtra("course_id", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Error: ID de curso no proporcionado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        detailViewModel.setCourseId(courseId);
        detailViewModel.getCourseDetails().observe(this, course -> {
            if (course != null) {
                textViewCourseName.setText(course.getNombreCurso());
                textViewCourseDescription.setText(course.getDescripcion());
                textViewProfessorId.setText("ID Profesor: " + course.getIdProfesor());
                // Aquí podrías hacer otra consulta para obtener el nombre del profesor usando el ID
                // (requeriría una modificación en CourseDetailViewModel y UserRepo para obtener usuario por ID)
            } else {
                Toast.makeText(CourseDetailActivity.this, "Curso no encontrado.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}