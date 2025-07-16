package com.grupoC.orgaedu.features.dashboard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.dashboard.viewmodel.DashboardStudentViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class DashboardStudentActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre;
    private TextView textMensajeMotivacional;
    private DashboardStudentViewModel viewModel;
    private NavigationView navigationView;  // Añadido NavigationView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre);
        textMensajeMotivacional = findViewById(R.id.textMensajeMotivacional);
        navigationView = findViewById(R.id.nav_view);  // Inicializar NavigationView

        setSupportActionBar(toolbar);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardStudentViewModel.class);

        // Observar nombre de usuario
        viewModel.getUserName().observe(this, nombreUsuario -> {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                toolbar.setTitle("Hola, " + nombreUsuario);
                textNombre.setText("Hola, " + nombreUsuario);
            } else {
                toolbar.setTitle("Bienvenido");
                textNombre.setText("Bienvenido");
            }
        });

        // Obtener nombre de usuario desde Intent y setear en ViewModel
        String nombreUsuarioIntent = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuarioIntent != null) {
            viewModel.setUserName(nombreUsuarioIntent);
        }

        // Mensaje motivacional
        String mensaje = "<b>¡Sigue adelante!</b><br/><br/>" +
                "Cada esfuerzo que haces hoy es un paso más cerca de tus sueños. " +
                "No te desanimes por los obstáculos, porque son oportunidades para aprender y crecer.<br/><br/>" +
                "Recuerda que el éxito no se mide solo por las notas, sino por la dedicación y la constancia que pones en tu camino.<br/><br/>" +
                "¡Tú puedes lograr todo lo que te propongas!<br/><br/>" +
                "<b>¡FELICES FIESTAS PATRIAS!</b>";

        textMensajeMotivacional.setText(android.text.Html.fromHtml(mensaje, android.text.Html.FROM_HTML_MODE_LEGACY));

        // Abrir drawer al tocar icono de menú
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Manejar selección de menú
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            int id = item.getItemId();
            if (id == R.id.nav_inicio) {
                // Aquí puedes agregar la lógica para navegar a Inicio
                // startActivity(new Intent(this, InicioActivity.class));
                return true;
            } else if (id == R.id.nav_mis_cursos) {
                // Navegar a Mis Clases
                // startActivity(new Intent(this, MisClasesActivity.class));
                return true;
            } else if (id == R.id.nav_calificaciones) {
                // Navegar a Calificaciones
                // startActivity(new Intent(this, CalificacionesActivity.class));
                return true;
            } else if (id == R.id.nav_asistencia) {
                // Navegar a Asistencia
                // startActivity(new Intent(this, AsistenciaActivity.class));
                return true;
            }
            return false;
        });
    }
}
