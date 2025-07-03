package com.abraham.loginapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Curso;

public class AgregarCursoActivity extends AppCompatActivity {

    private EditText editNombreCurso;
    private Button btnGuardarCurso, btnCancelar;
    private Database db;
    private int docenteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_curso);

        editNombreCurso = findViewById(R.id.editNombreCurso);
        btnGuardarCurso = findViewById(R.id.btnGuardarCurso);
        btnCancelar = findViewById(R.id.btnCancelarCurso);

        db = new Database(this);

        // Obtener ID del docente que está agregando el curso
        docenteId = getIntent().getIntExtra("docenteId", -1);

        btnGuardarCurso.setOnClickListener(v -> {
            String nombreCurso = editNombreCurso.getText().toString().trim();

            if (!nombreCurso.isEmpty()) {
                Curso nuevoCurso = new Curso();
                nuevoCurso.setNombre(nombreCurso);
                nuevoCurso.setDocenteId(docenteId);
                db.insertarCurso(nuevoCurso);


                Toast.makeText(this, "Curso agregado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AreasDocenteActivity.class);
                intent.putExtra("docente_id", docenteId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Ingrese el nombre del curso", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}
