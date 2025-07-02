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
    //private TextInputEditText usernameInput, passwordInput;
    private MaterialButton loginButton;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.edit_username);
        passwordInput = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        dbHelper = new Database(this);

        TextView goToRegister = findViewById(R.id.goToRegister);
        goToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            User user = dbHelper.authenticate(username, password);

            if (user != null) {
                Intent intent;
                if ("docente".equals(user.getRole())) {
                    intent = new Intent(this, Docente.class);
                } else {
                    intent = new Intent(this, Alumno.class);
                }
                // Pasar el nombre completo del usuario a la siguiente pantalla
                intent.putExtra("nombre_usuario", user.getNombre());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}