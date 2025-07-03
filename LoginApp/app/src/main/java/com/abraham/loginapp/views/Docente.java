package com.abraham.loginapp.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.abraham.loginapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Docente extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private int docenteId = -1; // Se inicializa con -1 por defecto

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        // Referencias de UI
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        TextView textoNombre = findViewById(R.id.textNombre);

        // Establecer Toolbar como ActionBar
        setSupportActionBar(toolbar);

        // Recuperar datos del Intent
        String nombreUsuario = getIntent().getStringExtra("nombre_usuario");
        docenteId = getIntent().getIntExtra("id_usuario", -1); // <- Este es el ID importante

        // Verificar y mostrar el nombre
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            toolbar.setTitle("Bienvenido, " + nombreUsuario);
            textoNombre.setText("Hola, " + nombreUsuario);
        } else {
            toolbar.setTitle("Bienvenido, Docente");
        }

        // Abrir menú al presionar el ícono de hamburguesa
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        // Configurar menú lateral
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            } else if (itemId == R.id.nav_areas) {
                if (docenteId != -1) {
                    Intent intent = new Intent(Docente.this, AreasDocenteActivity.class);
                    intent.putExtra("docenteId", docenteId); // <- nombre de clave consistente
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error: ID del docente no encontrado", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            } else if (itemId == R.id.nav_asistencia) {
                Toast.makeText(this, "Asistencia seleccionada", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            return false;
        });
    }
}
