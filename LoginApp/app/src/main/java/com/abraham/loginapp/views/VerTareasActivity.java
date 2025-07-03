package com.abraham.loginapp.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.adapters.TareaAdapter;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Tarea;

import java.util.List;

public class VerTareasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TareaAdapter tareaAdapter;
    private int cursoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas);

        recyclerView = findViewById(R.id.recyclerTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cursoId = getIntent().getIntExtra("curso_id", -1);

        if (cursoId == -1) {
            Toast.makeText(this, "ID de curso no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarTareas();
    }

    private void cargarTareas() {
        Database db = new Database(this);
        List<Tarea> tareas = db.obtenerTareasPorCurso(cursoId);

        if (tareas.isEmpty()) {
            Toast.makeText(this, "No hay tareas asignadas a este curso", Toast.LENGTH_SHORT).show();
        }

        tareaAdapter = new TareaAdapter(tareas);
        recyclerView.setAdapter(tareaAdapter);
    }
}
