package com.abraham.loginapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;

public class IntermedioActivity extends AppCompatActivity {

    private Button continuarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermedio);

        // Obtener datos del login
        int idUsuario = getIntent().getIntExtra("id_usuario", -1); // 🔁 IMPORTANTE
        String nombreUsuario = getIntent().getStringExtra("nombre_usuario");
        String tipoUsuario = getIntent().getStringExtra("tipo_usuario");

        TextView tipoText = findViewById(R.id.tipo_text);
        TextView nombreText = findViewById(R.id.nombre_text);

        tipoText.setText("Tipo de usuario: " + tipoUsuario);
        nombreText.setText("Nombre: " + nombreUsuario);

        // Botón continuar
        continuarButton = findViewById(R.id.continuar_button);
        continuarButton.setOnClickListener(v -> {
            Intent intent;
            if ("docente".equalsIgnoreCase(tipoUsuario)) {
                intent = new Intent(this, Docente.class);
            } else {
                intent = new Intent(this, Alumno.class);
            }

            // Pasamos los extras necesarios
            intent.putExtra("id_usuario", idUsuario); // 🔁 PARA DOCENTE
            intent.putExtra("nombre_usuario", nombreUsuario);
            startActivity(intent);
            finish();
        });
    }
}
