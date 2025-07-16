package com.grupoC.orgaedu.features.notas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.grupoC.orgaedu.R;

public class AgregarNotaActivity extends AppCompatActivity {

    private EditText edtCurso, edtDescripcion, edtNota;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        edtCurso = findViewById(R.id.edtCurso);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtNota = findViewById(R.id.edtNota);
        btnGuardar = findViewById(R.id.btnGuardarNota);

        btnGuardar.setOnClickListener(view -> {
            String curso = edtCurso.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();
            String notaStr = edtNota.getText().toString().trim();

            if (TextUtils.isEmpty(curso)) {
                edtCurso.setError("Ingrese el curso");
                return;
            }
            if (TextUtils.isEmpty(descripcion)) {
                edtDescripcion.setError("Ingrese una descripción");
                return;
            }
            if (TextUtils.isEmpty(notaStr)) {
                edtNota.setError("Ingrese una nota");
                return;
            }

            double notaValor = Double.parseDouble(notaStr);
            if (notaValor < 0 || notaValor > 20) {
                edtNota.setError("La nota debe ser entre 0 y 20");
                return;
            }

            // Crear Intent para devolver datos
            Intent resultIntent = new Intent();
            resultIntent.putExtra("curso", curso);
            resultIntent.putExtra("descripcion", descripcion);
            resultIntent.putExtra("nota", notaValor);

            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Nota registrada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
