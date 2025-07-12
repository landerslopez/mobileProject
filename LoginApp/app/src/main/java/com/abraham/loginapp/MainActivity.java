package com.abraham.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private CheckBox rememberMeCheckBox;
    private TextInputEditText usernameEditText, passwordEditText;
    private MaterialButton loginButton;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar los elementos de la interfaz desde activity_login.xml

        usernameEditText = findViewById(R.id.edit_username);
        passwordEditText = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        dbHelper = new Database(this);  // Inicializar la base de datos

        // Configurar el botón de login
        loginButton.setOnClickListener(v -> loginUser());  // Llamar al método loginUser cuando el usuario hace clic en "Acceder"
    }

    // Método para manejar el inicio de sesión
    private void loginUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Verificar si las credenciales son correctas usando la base de datos
        User user = dbHelper.authenticate(username, password);
        if (user != null) {
            // Si las credenciales son correctas, redirigir al usuario a la siguiente actividad
            Intent intent = new Intent(MainActivity.this, MainActivity.class);  // Redirige a MainActivity
            startActivity(intent);
            finish();  // Finalizar esta actividad para que el usuario no regrese al login
        } else {
            // Si las credenciales no son correctas, mostrar un mensaje de error
            Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}
