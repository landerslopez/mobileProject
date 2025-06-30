package com.example.gestion_curso.views.docente;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.tuapp.miclaseapp.R;

public class Docente extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        TextView textoNombre = findViewById(R.id.textNombre);


        // Establecer la Toolbar como ActionBar
        setSupportActionBar(toolbar);

        // Obtener el nombre desde el intent
        String nombreUsuario = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuario != null) {
            toolbar.setTitle("Bienvenido, " + nombreUsuario);
            textoNombre.setText("Hola, " + nombreUsuario);
        }

        // Listener para el icono de hamburguesa
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
