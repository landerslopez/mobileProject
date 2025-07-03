package com.example.gestion_curso.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestion_curso.config.Database;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tuapp.miclaseapp.R;

public class Register extends AppCompatActivity {

    private TextInputEditText usernameInput, passwordInput;
    private AutoCompleteTextView roleDropdown;
    private MaterialButton registerButton;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.edit_username);
        passwordInput = findViewById(R.id.edit_password);
        roleDropdown = findViewById(R.id.auto_complete_roles);

        registerButton = findViewById(R.id.btnRegistrar);
        dbHelper = new Database(this);


        String[] roles = {"docente", "alumno"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                roles
        );
        roleDropdown.setAdapter(adapter);

// Opcional, fuerza a mostrar el dropdown al hacer clic
        roleDropdown.setOnClickListener(v -> roleDropdown.showDropDown());
        registerButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String role = roleDropdown.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertUser(username, password, role);
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish(); // Cierra y vuelve al login
            } else {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}