package com.grupoC.orgaedu.features.grades.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.grupoC.orgaedu.R; // Asegúrate de tener este archivo R para los IDs de los layouts
import com.grupoC.orgaedu.data.database.entities.Grade;
import com.grupoC.orgaedu.features.grades.viewmodel.GradeViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GradeActivity extends AppCompatActivity {

    private GradeViewModel gradeViewModel;
    private EditText gradeTitleInput;
    private EditText gradeDescriptionInput;
    private EditText gradeScoreInput;
    private EditText gradeUserIdInput; // Para que el profesor seleccione a quién califica
    private EditText gradeCourseIdInput; // Para que el profesor seleccione el curso
    private Button addGradeButton;
    private ListView gradesListView;
    private ArrayAdapter<String> gradeAdapter;
    private List<Grade> currentGrades = new ArrayList<>();

    // Suponiendo que el rol del usuario se pasa por Intent o se obtiene de las preferencias
    private String currentUserRole = "alumno"; // "alumno" o "docente"
    private static final int CURRENT_USER_ID = 1; // ID del usuario actual (estudiante o profesor)

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade); // Necesitarás crear este layout

        gradeTitleInput = findViewById(R.id.grade_title_input);
        gradeDescriptionInput = findViewById(R.id.grade_description_input);
        gradeScoreInput = findViewById(R.id.grade_score_input);
        gradeUserIdInput = findViewById(R.id.grade_user_id_input);
        gradeCourseIdInput = findViewById(R.id.grade_course_id_input);
        addGradeButton = findViewById(R.id.add_grade_button);
        gradesListView = findViewById(R.id.grades_list_view);

        drawerLayout = findViewById(R.id.drawer_layout); // Asume que se comparte el mismo drawer layout
        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);

        setSupportActionBar(topAppBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                topAppBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                // Manejar la navegación de los ítems del menú aquí
                // ... (similar a TaskActivity) ...
                drawerLayout.closeDrawers();
                return true;
            }
        });

        gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        gradesListView.setAdapter(gradeAdapter);

        gradeViewModel = new ViewModelProvider(this).get(GradeViewModel.class);

        // Lógica para mostrar la interfaz según el rol
        if (currentUserRole.equals("alumno")) {
            // Un estudiante solo ve sus notas
            findViewById(R.id.professor_grade_input_layout).setVisibility(View.GONE); // Ocultar inputs de profesor
            addGradeButton.setVisibility(View.GONE); // Ocultar botón de añadir
            gradeViewModel.initializeGradesForUser(CURRENT_USER_ID);
            gradeViewModel.getAllGradesForUser().observe(this, grades -> {
                updateGradesList(grades);
            });
        } else if (currentUserRole.equals("docente")) {
            // Un profesor puede registrar notas y ver las que ha registrado
            findViewById(R.id.professor_grade_input_layout).setVisibility(View.VISIBLE); // Mostrar inputs de profesor
            addGradeButton.setVisibility(View.VISIBLE);
            gradeViewModel.initializeGradesRegisteredByProfessor(CURRENT_USER_ID); // Asume que CURRENT_USER_ID es el ID del profesor
            gradeViewModel.getGradesRegisteredByProfessor().observe(this, grades -> {
                updateGradesList(grades);
            });
        }

        gradeViewModel.getMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(GradeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        addGradeButton.setOnClickListener(v -> {
            if (currentUserRole.equals("docente")) {
                String title = gradeTitleInput.getText().toString().trim();
                String description = gradeDescriptionInput.getText().toString().trim();
                String scoreStr = gradeScoreInput.getText().toString().trim();
                String userIdStr = gradeUserIdInput.getText().toString().trim();
                String courseIdStr = gradeCourseIdInput.getText().toString().trim();

                if (title.isEmpty() || scoreStr.isEmpty() || userIdStr.isEmpty() || courseIdStr.isEmpty()) {
                    Toast.makeText(GradeActivity.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double score = Double.parseDouble(scoreStr);
                    int userId = Integer.parseInt(userIdStr);
                    int courseId = Integer.parseInt(courseIdStr);

                    Grade newGrade = new Grade();
                    newGrade.setTitulo(title);
                    newGrade.setDescripcion(description);
                    newGrade.setPuntuacion(score);
                    newGrade.setIdUsuario(userId);
                    newGrade.setIdCurso(courseId);
                    newGrade.setIdProfesor(CURRENT_USER_ID); // El profesor actual registra la nota
                    newGrade.setFechaRegistro(new Date());

                    gradeViewModel.createGrade(newGrade);

                    // Limpiar campos
                    gradeTitleInput.setText("");
                    gradeDescriptionInput.setText("");
                    gradeScoreInput.setText("");
                    gradeUserIdInput.setText("");
                    gradeCourseIdInput.setText("");

                } catch (NumberFormatException e) {
                    Toast.makeText(GradeActivity.this, "Por favor, ingresa valores numéricos válidos para puntuación, ID de usuario y ID de curso.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Para permitir actualizar o eliminar una nota (solo para profesores o admins, si aplica)
        // Similar a TaskActivity, puedes usar setOnItemClickListener y setOnItemLongClickListener
        gradesListView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (currentUserRole.equals("docente")) { // Solo los docentes pueden eliminar notas
                Grade gradeToDelete = currentGrades.get(position);
                gradeViewModel.deleteGrade(gradeToDelete.getId());
                Toast.makeText(GradeActivity.this, "Mantén presionado para eliminar", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    private void updateGradesList(List<Grade> grades) {
        currentGrades.clear();
        currentGrades.addAll(grades);

        gradeAdapter.clear();
        for (Grade grade : grades) {
            String gradeInfo = "Título: " + grade.getTitulo() + "\n" +
                    "Descripción: " + grade.getDescripcion() + "\n" +
                    "Puntuación: " + grade.getPuntuacion() + "\n" +
                    "Curso ID: " + grade.getIdCurso() + "\n" +
                    "Usuario ID: " + grade.getIdUsuario() + "\n" +
                    "Fecha: " + grade.getFechaRegistro();
            gradeAdapter.add(gradeInfo);
        }
        gradeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}