package com.grupoC.orgaedu.features.dashboard.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.dashboard.viewmodel.DashboardProfessorViewModel;
import com.grupoC.orgaedu.features.tasks.ui.TaskActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class DashboardProfessorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre;
    private DashboardProfessorViewModel viewModel;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(DashboardProfessorViewModel.class);

        viewModel.getUserName().observe(this, nombreUsuario -> {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                toolbar.setTitle("Bienvenido, " + nombreUsuario);
                textNombre.setText("Hola, " + nombreUsuario);
            } else {
                toolbar.setTitle("Bienvenido");
                textNombre.setText("Bienvenido");
            }
        });

        String nombreUsuarioIntent = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuarioIntent != null) {
            viewModel.setUserName(nombreUsuarioIntent);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_inicio) {
                    Toast.makeText(DashboardProfessorActivity.this, "Inicio seleccionado", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_areas) {
                    Toast.makeText(DashboardProfessorActivity.this, "Áreas seleccionadas", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_asistencia) {
                    Toast.makeText(DashboardProfessorActivity.this, "Asistencia seleccionada", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_tareas) {
                    Toast.makeText(DashboardProfessorActivity.this, "Tareas seleccionadas", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DashboardProfessorActivity.this, TaskActivity.class));
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}