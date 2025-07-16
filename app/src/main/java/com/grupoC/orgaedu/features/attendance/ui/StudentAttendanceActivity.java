package com.grupoC.orgaedu.features.attendance.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.attendance.viewmodel.AttendanceViewModel;

import java.util.Date;

public class StudentAttendanceActivity extends AppCompatActivity {

    private AttendanceViewModel attendanceViewModel;
    private TextView welcomeText;
    private Button recordAttendanceButton;
    private int loggedInUserId; // Deberías pasar el ID del usuario logueado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance); // Necesitas crear este layout

        welcomeText = findViewById(R.id.welcome_text);
        recordAttendanceButton = findViewById(R.id.record_attendance_button);

        // Recuperar el ID del usuario logueado. Esto es crucial.
        // Por ejemplo, si lo pasas desde LoginActivity o DashboardStudentActivity
        loggedInUserId = getIntent().getIntExtra("user_id", -1);
        String userName = getIntent().getStringExtra("user_name"); // Recuperar el nombre también

        if (loggedInUserId == -1) {
            Toast.makeText(this, "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (userName != null) {
            welcomeText.setText("¡Bienvenido, " + userName + "! Registra tu asistencia.");
        }

        attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        // Observar el estado del registro de asistencia
        attendanceViewModel.getAttendanceRecorded().observe(this, isRecorded -> {
            if (isRecorded != null && isRecorded) {
                Toast.makeText(StudentAttendanceActivity.this, "Asistencia registrada con éxito!", Toast.LENGTH_SHORT).show();
                // Opcional: Deshabilitar el botón de registro para hoy o actualizar UI
            }
        });

        // Observar mensajes de error
        attendanceViewModel.getErrorMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(StudentAttendanceActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        recordAttendanceButton.setOnClickListener(v -> {
            // Lógica para registrar asistencia. Aquí se asume "presente"
            attendanceViewModel.recordAttendance(loggedInUserId, new Date(), "presente");
        });

    }
}