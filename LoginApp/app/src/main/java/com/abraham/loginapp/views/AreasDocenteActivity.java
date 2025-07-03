package com.abraham.loginapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Curso;

import java.util.List;

public class AreasDocenteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCursos;
    private CursoDocenteAdapter adapter;
    private Database db;
    private int docenteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas_docente);

        // ❗ Asegurarse de usar la misma clave que en Docente.java
        docenteId = getIntent().getIntExtra("docenteId", -1);

        recyclerViewCursos = findViewById(R.id.recyclerViewCursos);
        recyclerViewCursos.setLayoutManager(new LinearLayoutManager(this));

        db = new Database(this);

        if (docenteId != -1) {
            List<Curso> cursos = db.obtenerCursosPorDocente(docenteId);
            adapter = new CursoDocenteAdapter(this, cursos, docenteId); // ✅ pasar docenteId aquí
            recyclerViewCursos.setAdapter(adapter);
        } else {
            // Puedes mostrar un mensaje de error o terminar la actividad
            // si el ID del docente no fue recibido correctamente
        }

        Button btnAgregarCurso = findViewById(R.id.btnAgregarCurso);
        Button btnVolver = findViewById(R.id.btnVolver);

// Redirige a una pantalla para agregar cursos (si no tienes aún, podemos crearla)
        btnAgregarCurso.setOnClickListener(v -> {
            Intent intent = new Intent(this, AgregarCursoActivity.class);
            intent.putExtra("docente_id", docenteId);
            startActivity(intent);
        });

// Vuelve a la actividad anterior
        btnVolver.setOnClickListener(v -> finish());

    }
}
