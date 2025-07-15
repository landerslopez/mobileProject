package com.abraham.loginapp.features.dashboard.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider; // Importar ViewModelProvider

import com.abraham.loginapp.R;
import com.abraham.loginapp.features.dashboard.viewmodel.DashboardProfessorViewModel; // Crear este ViewModel
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardProfessorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre; // Cambiado a textNombre para reflejar el ID del layout
    private DashboardProfessorViewModel viewModel; // Instancia del ViewModel

    @SuppressLint("MissingInflatedId") // Asegúrate de que los IDs existan en activity_docente.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente); // Asumo que activity_docente es el layout para el docente

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre);

        setSupportActionBar(toolbar);

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardProfessorViewModel.class);

        // Observar el LiveData del nombre de usuario en el ViewModel
        viewModel.getUserName().observe(this, nombreUsuario -> {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                toolbar.setTitle("Bienvenido, " + nombreUsuario);
                textNombre.setText("Hola, " + nombreUsuario);
            } else {
                toolbar.setTitle("Bienvenido");
                textNombre.setText("Bienvenido");
            }
        });

        // Simplemente para demostración, obtener el nombre del Intent y pasarlo al ViewModel
        // En una app real, el ViewModel podría obtener esto del UserRepository o SharedPreferences
        String nombreUsuarioIntent = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuarioIntent != null) {
            viewModel.setUserName(nombreUsuarioIntent);
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