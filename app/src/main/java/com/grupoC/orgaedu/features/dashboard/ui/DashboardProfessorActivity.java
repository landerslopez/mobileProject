package com.grupoC.orgaedu.features.dashboard.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.dashboard.viewmodel.DashboardProfessorViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardProfessorActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private TextView textNombre;
    private TextView textMisionVision;
    private DashboardProfessorViewModel viewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docente);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.topAppBar);
        textNombre = findViewById(R.id.textNombre);
        textMisionVision = findViewById(R.id.textMisionVision);

        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(DashboardProfessorViewModel.class);

        viewModel.getUserName().observe(this, nombreUsuario -> {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                toolbar.setTitle("Bienvenido, " + nombreUsuario);
                textNombre.setText("Hola, " + nombreUsuario);
            } else {
                toolbar.setTitle("Bienvenido");
                textNombre.setText("Hola");
            }
        });

        String nombreUsuarioIntent = getIntent().getStringExtra("nombre_usuario");
        if (nombreUsuarioIntent != null) {
            viewModel.setUserName(nombreUsuarioIntent);
        }

        // Construir texto mision y vision con saltos de línea y negritas
        String misionVisionHtml = "<b>" + getString(R.string.title_mision) + ":</b><br/><br/>"
                + getString(R.string.text_mision) + "<br/><br/><br/>"
                + "<b>" + getString(R.string.title_vision) + ":</b><br/><br/>"
                + getString(R.string.text_vision);

        textMisionVision.setText(Html.fromHtml(misionVisionHtml, Html.FROM_HTML_MODE_LEGACY));

        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }
}
