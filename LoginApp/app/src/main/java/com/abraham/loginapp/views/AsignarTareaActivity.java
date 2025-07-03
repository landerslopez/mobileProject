package com.abraham.loginapp.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AsignarTareaActivity extends AppCompatActivity {

    private EditText editTitulo, editDescripcion;
    private Button btnGuardar;
    private int cursoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_tarea);

        editTitulo = findViewById(R.id.editTituloTarea);
        editDescripcion = findViewById(R.id.editDescripcionTarea);
        btnGuardar = findViewById(R.id.btnGuardarTarea);

        cursoId = getIntent().getIntExtra("curso_id", -1);

        if (cursoId == -1) {
            Toast.makeText(this, "Error: curso no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnGuardar.setOnClickListener(v -> {
            String titulo = editTitulo.getText().toString().trim();
            String descripcion = editDescripcion.getText().toString().trim();

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener fecha actual (opcional)
            String fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            Database db = new Database(this);
            boolean exito = db.insertarTarea(cursoId, titulo, descripcion, fecha);
            db.close();

            if (exito) {
                Toast.makeText(this, "Tarea asignada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al asignar tarea", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
