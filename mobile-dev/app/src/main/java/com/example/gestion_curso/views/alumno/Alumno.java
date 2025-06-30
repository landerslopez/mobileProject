package com.example.gestion_curso.views.alumno;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.tuapp.miclaseapp.R;

public class Alumno extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        TextView textoNombre = findViewById(R.id.textNombre);

        setSupportActionBar(toolbar);

        // Obtener el nombre del usuario desde el Intent
        String nombreUsuario = getIntent().getStringExtra("nombre_usuario");

        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            toolbar.setTitle("Hola, " + nombreUsuario);
            textoNombre.setText("Hola, " + nombreUsuario);
        } else {
            toolbar.setTitle("Bienvenido");
        }

        // Manejar clic en el ícono de menú
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}