package com.grupoC.orgaedu.features.dashboard.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.dashboard.viewmodel.DashboardStudentViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardStudentActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre;
    private TextView textMensajeMotivacional; // Aquí cambia el nombre para coincidir con el XML
    private DashboardStudentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno); // Layout para el estudiante

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre);
        textMensajeMotivacional = findViewById(R.id.textMensajeMotivacional); // ID corregido

        setSupportActionBar(toolbar);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardStudentViewModel.class);

        // Observar nombre de usuario para actualizar UI
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

        // Setear mensaje motivacional formateado con saltos de línea y negritas usando HTML
        String mensaje = "<b>¡Sigue adelante!</b><br/><br/>" +
                "Cada esfuerzo que haces hoy es un paso más cerca de tus sueños. " +
                "No te desanimes por los obstáculos, porque son oportunidades para aprender y crecer.<br/><br/>" +
                "Recuerda que el éxito no se mide solo por las notas, sino por la dedicación y la constancia que pones en tu camino.<br/><br/>" +
                "¡Tú puedes lograr todo lo que te propongas!<br/><br/>" +
                "<b>¡FELICES FIESTAS PATRIAS!</b>";

        textMensajeMotivacional.setText(android.text.Html.fromHtml(mensaje, android.text.Html.FROM_HTML_MODE_LEGACY));

        // Listener para abrir drawer al tocar el icono de menú
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }
}
