package com.grupoC.orgaedu.features.dashboard.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider; // Importar ViewModelProvider

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.dashboard.viewmodel.DashboardStudentViewModel; // Crear este ViewModel
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardStudentActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre; // Cambiado a textNombre para reflejar el ID del layout
    private DashboardStudentViewModel viewModel; // Instancia del ViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno); // Asumo que activity_alumno es el layout para el estudiante

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre); // Usar el ID correcto

        setSupportActionBar(toolbar);

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardStudentViewModel.class);

        // Observar el LiveData del nombre de usuario en el ViewModel
        // El nombre de usuario se puede pasar al ViewModel al iniciar la sesión.
        viewModel.getUserName().observe(this, nombreUsuario -> {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                toolbar.setTitle("Hola, " + nombreUsuario);
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

        // Manejar clic en el ícono de menú
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}