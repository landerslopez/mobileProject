package com.abraham.loginapp.features.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.abraham.loginapp.R;
import com.abraham.loginapp.data.database.entities.User; // Ya no importamos AppDatabase aquí
import com.abraham.loginapp.features.auth.viewmodel.LoginViewModel; // Importamos el ViewModel
import com.abraham.loginapp.features.dashboard.ui.DashboardStudentActivity;
import com.abraham.loginapp.features.dashboard.ui.DashboardProfessorActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private MaterialButton loginButton;
    private LoginViewModel loginViewModel; // Instancia del ViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.edit_username);
        passwordInput = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);

        // Inicializar el ViewModel usando ViewModelProvider
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        TextView goToRegister = findViewById(R.id.goToRegister);
        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        // --- Observadores del LiveData del ViewModel ---

        // Observa si el login fue exitoso
        loginViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) { // isSuccess puede ser null si no ha habido intento de login
                // Navegar solo si el login fue exitoso y tenemos un usuario autenticado
                User loggedInUser = loginViewModel.getLoggedInUser().getValue();
                if (loggedInUser != null) {
                    Intent intent;
                    if ("docente".equals(loggedInUser.getRole())) {
                        intent = new Intent(LoginActivity.this, DashboardProfessorActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, DashboardStudentActivity.class);
                    }
                    intent.putExtra("nombre_usuario", loggedInUser.getNombre());
                    startActivity(intent);
                    finish(); // Cerrar LoginActivity
                }
            }
            // Si isSuccess es false, el mensaje de error se manejará con otro observador
        });

        // Observa los mensajes de error
        loginViewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });


        // --- Listener del botón de Login ---
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            loginViewModel.login(username, password); // Llamada al ViewModel
        });
    }
}