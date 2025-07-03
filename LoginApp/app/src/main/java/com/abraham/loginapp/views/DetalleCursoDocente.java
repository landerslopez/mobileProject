package com.abraham.loginapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;

public class DetalleCursoDocente extends AppCompatActivity {

    private int cursoId;
    private String cursoNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curso_docente);

        // Obtener extras desde el intent
        cursoId = getIntent().getIntExtra("curso_id", -1);
        cursoNombre = getIntent().getStringExtra("curso_nombre");

        // Referencias a la interfaz
        TextView txtTituloCurso = findViewById(R.id.textCursoTitulo);
        Button btnAsignarTarea = findViewById(R.id.btnAsignarTarea);
        Button btnVerEntregas = findViewById(R.id.btnVerEntregas);
        Button btnCalificar = findViewById(R.id.btnCalificar);
        Button btnAsistencia = findViewById(R.id.btnAsistencia);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Mostrar el nombre del curso en el título
        if (cursoNombre != null) {
            txtTituloCurso.setText("Opciones para: " + cursoNombre);
        }

        // Acción: Asignar Tarea
        btnAsignarTarea.setOnClickListener(v -> {
            Intent intent = new Intent(this, AsignarTareaActivity.class);
            intent.putExtra("curso_id", cursoId);
            startActivity(intent);
        });

        // Acción: Ver Tareas Asignadas (antes llamado VerEntregas)
        btnVerEntregas.setOnClickListener(v -> {
            Intent intent = new Intent(this, VerTareasActivity.class);  // Cambiar si usas otro nombre
            intent.putExtra("curso_id", cursoId);
            startActivity(intent);
        });

        // Acción: Calificar (a implementar)
        btnCalificar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CalificarActivity.class);
            intent.putExtra("curso_id", cursoId);
            startActivity(intent);
        });

        // Acción: Tomar Asistencia (a implementar)
        btnAsistencia.setOnClickListener(v -> {
            Intent intent = new Intent(this, TomarAsistenciaActivity.class);
            intent.putExtra("curso_id", cursoId);
            startActivity(intent);
        });

        // Acción: Volver
        btnVolver.setOnClickListener(v -> finish());
    }
}
