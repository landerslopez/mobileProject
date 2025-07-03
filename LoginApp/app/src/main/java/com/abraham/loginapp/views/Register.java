package com.abraham.loginapp.views;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    private TextInputEditText editFullName, editEmail, usernameInput, passwordInput;
    private AutoCompleteTextView roleDropdown;
    private MaterialButton registerButton;
    private Database dbHelper;

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

        dbHelper = new Database(this);

        String[] roles = {"docente", "alumno"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                roles
        );
        roleDropdown.setAdapter(adapter);
        roleDropdown.setOnClickListener(v -> roleDropdown.showDropDown());

        registerButton.setOnClickListener(v -> {
            String fullName = editFullName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String role = roleDropdown.getText().toString().trim();

            // Verificar campos vacíos
            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validación 1: nombre completo máximo 50 caracteres
            if (fullName.length() > 50) {
                Toast.makeText(this, "El nombre completo debe tener máximo 50 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validación 2: usuario único
            if (dbHelper.userExists(username)) {
                Toast.makeText(this, "El nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validación 3: correo formato válido y Gmail
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.endsWith("@gmail.com")) {
                Toast.makeText(this, "Correo inválido o no es de Gmail", Toast.LENGTH_SHORT).show();
                return;
            }


            // Si pasa todas las validaciones
            long resultado = dbHelper.registrarUsuario(fullName, email, username, password, role);
            boolean success = resultado != -1;

            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
            finish(); // Cierra la pantalla de registro
        });
    }
}