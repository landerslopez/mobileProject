package com.grupoC.orgaedu.features.notas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grupoC.orgaedu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CursoNotasAdapter cursoNotasAdapter;
    private List<Nota> todasLasNotas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        recyclerView = findViewById(R.id.recyclerNotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregarNota);
        fabAgregar.setOnClickListener(v -> agregarNota());

        // Cargar notas iniciales
        todasLasNotas = obtenerNotas();
        actualizarNotasEnVista();
    }

    // 🔥 Método para abrir pantalla de agregar nota
    private void agregarNota() {
        Intent intent = new Intent(this, AgregarNotaActivity.class);
        startActivityForResult(intent, 1);
    }

    // 🔥 Recibir la nota agregada y actualizar lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String curso = data.getStringExtra("curso");
            String descripcion = data.getStringExtra("descripcion");
            int notaValor = data.getIntExtra("nota", 0);

            Nota nuevaNota = new Nota(curso, descripcion, notaValor);
            todasLasNotas.add(nuevaNota);

            actualizarNotasEnVista();
        }
    }

    // Agrupa las notas por curso y actualiza el adapter
    private void actualizarNotasEnVista() {
        Map<String, List<Nota>> notasAgrupadas = new HashMap<>();
        for (Nota nota : todasLasNotas) {
            if (!notasAgrupadas.containsKey(nota.getCurso())) {
                notasAgrupadas.put(nota.getCurso(), new ArrayList<>());
            }
            notasAgrupadas.get(nota.getCurso()).add(nota);
        }

        cursoNotasAdapter = new CursoNotasAdapter(notasAgrupadas);
        recyclerView.setAdapter(cursoNotasAdapter);
    }

    private List<Nota> obtenerNotas() {
        List<Nota> lista = new ArrayList<>();
        lista.add(new Nota("Matemáticas", "Examen Parcial", 18));
        lista.add(new Nota("Historia", "Trabajo grupal", 15));
        lista.add(new Nota("Química", "Práctica de laboratorio", 17));
        return lista;
    }
}
