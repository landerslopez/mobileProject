package com.abraham.loginapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.User;
import com.google.android.material.button.MaterialButton;

public class Login extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private MaterialButton loginButton;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referencias a la UI
        usernameInput = findViewById(R.id.edit_username);
        passwordInput = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        dbHelper = new Database(this);

        // Ir al registro
        TextView goToRegister = findViewById(R.id.goToRegister);
        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });

        // Acción del botón login
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            // Autenticación
            User user = dbHelper.login(username, password);

            if (user != null) {
                // Pasamos también el ID del usuario
                Intent intent = new Intent(this, IntermedioActivity.class);
                intent.putExtra("id_usuario", user.getId()); // 🔁 ESTE FALTABA
                intent.putExtra("nombre_usuario", user.getNombre());
                intent.putExtra("tipo_usuario", user.getRole());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
