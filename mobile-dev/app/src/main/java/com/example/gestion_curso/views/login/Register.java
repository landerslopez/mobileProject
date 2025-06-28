package com.example.gestion_curso.views.login;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_curso.config.Database;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tuapp.miclaseapp.R;

public class Register extends AppCompatActivity {

    private TextInputEditText editFullName, editEmail, usernameInput, passwordInput;
    private AutoCompleteTextView roleDropdown;
    private MaterialButton registerButton;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFullName = findViewById(R.id.edit_username); // Nombre completo
        editEmail = findViewById(R.id.etCorreo); // Correo
        usernameInput = findViewById(R.id.edit_user); // Nombre de usuario
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

            if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.insertUser(fullName, email, username, password, role);
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}