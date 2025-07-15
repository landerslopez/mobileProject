package com.grupoC.orgaedu.features.auth.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
// Ya no necesitamos importar AppDatabase aquí
import com.grupoC.orgaedu.features.auth.viewmodel.RegisterViewModel; // Importamos el ViewModel
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editFullName, editEmail, usernameInput, passwordInput;
    private AutoCompleteTextView roleDropdown;
    private MaterialButton registerButton;
    private RegisterViewModel registerViewModel; // Instancia del ViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFullName = findViewById(R.id.edit_username);
        editEmail = findViewById(R.id.etCorreo);
        usernameInput = findViewById(R.id.edit_user);
        passwordInput = findViewById(R.id.edit_password);
        roleDropdown = findViewById(R.id.auto_complete_roles);
        registerButton = findViewById(R.id.btnRegistrar);

        // Inicializar el ViewModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        String[] roles = {"docente", "alumno"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                roles
        );
        roleDropdown.setAdapter(adapter);
        roleDropdown.setOnClickListener(v -> roleDropdown.showDropDown());

        // --- Observadores del LiveData del ViewModel ---

        registerViewModel.getRegisterSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish(); // Cierra esta actividad y regresa a la anterior (Login)
            }
            // Si es false, el mensaje de error se maneja con otro observador
        });

        registerViewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // --- Listener del botón de Registro ---
        registerButton.setOnClickListener(v -> {
            String fullName = editFullName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String role = roleDropdown.getText().toString().trim();

            // Delegar la lógica de registro y validación al ViewModel
            registerViewModel.registerUser(fullName, email, username, password, role);
        });

        ImageButton btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            // No es necesario crear un nuevo Intent si solo quieres volver a la actividad anterior.
            // finish() es suficiente si LoginActivity está en la pila de atrás.
            finish();
        });
    }
}