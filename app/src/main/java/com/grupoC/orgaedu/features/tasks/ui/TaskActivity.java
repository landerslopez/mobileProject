package com.grupoC.orgaedu.features.tasks.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.R; // Asegúrate de que R es correcto para tu proyecto
import com.grupoC.orgaedu.data.database.entities.Task; // Importa Task desde su paquete en inglés
import com.grupoC.orgaedu.features.tasks.viewmodel.TaskViewModel; // Importa TaskViewModel desde su paquete en inglés

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;
    private EditText taskTitleInput;
    private EditText taskDescriptionInput;
    private Button addTaskButton;
    private ListView tasksListView;
    private ArrayAdapter<String> taskAdapter;
    private List<Task> currentTasks = new ArrayList<>(); // Para mantener la referencia a los objetos Task

    // Suponemos un ID de usuario para esta demostración.
    // En una aplicación real, lo obtendrías del usuario logueado.
    private static final int CURRENT_USER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Inicializar vistas
        taskTitleInput = findViewById(R.id.task_title_input);
        taskDescriptionInput = findViewById(R.id.task_description_input);
        addTaskButton = findViewById(R.id.add_task_button);
        tasksListView = findViewById(R.id.tasks_list_view);

        // Configurar el ArrayAdapter para el ListView
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        tasksListView.setAdapter(taskAdapter);

        // Inicializar el TaskViewModel
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.initializeTasksForUser(CURRENT_USER_ID); // ¡Importante inicializar con el ID de usuario!

        // Observar la lista de tareas del ViewModel
        taskViewModel.getAllTasksForUser().observe(this, tasks -> {
            currentTasks.clear(); // Limpiar la lista actual
            currentTasks.addAll(tasks); // Actualizar con las nuevas tareas

            taskAdapter.clear(); // Limpiar el adaptador
            for (Task task : tasks) {
                taskAdapter.add(task.getTitulo() + " - " + task.getEstadoEntrega() + (task.isEstadoCompletado() ? " (Completada)" : ""));
            }
            taskAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
        });

        // Observar mensajes del ViewModel (éxito/error)
        taskViewModel.getMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(TaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón para añadir tarea
        addTaskButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString().trim();
            String description = taskDescriptionInput.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(TaskActivity.this, "El título de la tarea no puede estar vacío.", Toast.LENGTH_SHORT).show();
                return;
            }

            Task newTask = new Task();
            newTask.setTitulo(title);
            newTask.setDescripcion(description);
            newTask.setFechaVencimiento(new Date(System.currentTimeMillis() + 86400000L * 7)); // Vence en 7 días
            newTask.setIdCurso(1); // ID de curso de ejemplo
            newTask.setIdUsuarioAsignado(CURRENT_USER_ID);
            newTask.setEstadoEntrega("Pendiente");
            newTask.setEstadoCompletado(false);

            taskViewModel.createTask(newTask);

            // Limpiar los campos después de añadir
            taskTitleInput.setText("");
            taskDescriptionInput.setText("");
        });

        // Opcional: Manejar clics en los elementos de la lista (para editar/eliminar)
        tasksListView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = currentTasks.get(position);
            // Aquí podrías abrir una nueva Activity para editar la tarea,
            // o mostrar un diálogo para confirmar eliminación/cambiar estado.
            Toast.makeText(TaskActivity.this, "Tarea seleccionada: " + selectedTask.getTitulo(), Toast.LENGTH_SHORT).show();

            // Ejemplo: marcar como completada/incompleta al hacer click
            selectedTask.setEstadoCompletado(!selectedTask.isEstadoCompletado());
            taskViewModel.updateTask(selectedTask);
        });

        // Opcional: Manejar clics largos para eliminar
        tasksListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task taskToDelete = currentTasks.get(position);
            taskViewModel.deleteTask(taskToDelete.getId());
            Toast.makeText(TaskActivity.this, "Mantén presionado para eliminar", Toast.LENGTH_SHORT).show(); // Mensaje de confirmación visual
            return true; // Consume el evento de clic largo
        });
    }
}
