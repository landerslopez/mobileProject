package com.grupoC.orgaedu.features.tasks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.appbar.MaterialToolbar;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.data.database.entities.Task;
import com.grupoC.orgaedu.features.tasks.viewmodel.TaskViewModel;

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
    private List<Task> currentTasks = new ArrayList<>();

    private static final int CURRENT_USER_ID = 1;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskTitleInput = findViewById(R.id.task_title_input);
        taskDescriptionInput = findViewById(R.id.task_description_input);
        addTaskButton = findViewById(R.id.add_task_button);
        tasksListView = findViewById(R.id.tasks_list_view);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);

        setSupportActionBar(topAppBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                topAppBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_inicio) {
                    Toast.makeText(TaskActivity.this, "Inicio seleccionado", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_areas) {
                    Toast.makeText(TaskActivity.this, "Áreas seleccionadas", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_asistencia) {
                    Toast.makeText(TaskActivity.this, "Asistencia seleccionada", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_tareas) {
                    Toast.makeText(TaskActivity.this, "Tareas seleccionadas", Toast.LENGTH_SHORT).show();
                    // Si ya estás en TaskActivity, no necesitas iniciarla de nuevo
                    // Puedes añadir una comprobación si es necesario:
                    // if (!(TaskActivity.this instanceof TaskActivity)) {
                    //     startActivity(new Intent(TaskActivity.this, TaskActivity.class));
                    // }
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        tasksListView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.initializeTasksForUser(CURRENT_USER_ID);

        taskViewModel.getAllTasksForUser().observe(this, tasks -> {
            currentTasks.clear();
            currentTasks.addAll(tasks);

            taskAdapter.clear();
            for (Task task : tasks) {
                taskAdapter.add(task.getTitulo() + " - " + task.getEstadoEntrega() + (task.isEstadoCompletado() ? " (Completada)" : ""));
            }
            taskAdapter.notifyDataSetChanged();
        });

        taskViewModel.getMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(TaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

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
            newTask.setFechaVencimiento(new Date(System.currentTimeMillis() + 86400000L * 7));
            newTask.setIdCurso(1);
            newTask.setIdUsuarioAsignado(CURRENT_USER_ID);
            newTask.setEstadoEntrega("Pendiente");
            newTask.setEstadoCompletado(false);

            taskViewModel.createTask(newTask);

            taskTitleInput.setText("");
            taskDescriptionInput.setText("");
        });

        tasksListView.setOnItemClickListener((parent, view, position, id) -> {
            Task selectedTask = currentTasks.get(position);
            Toast.makeText(TaskActivity.this, "Tarea seleccionada: " + selectedTask.getTitulo(), Toast.LENGTH_SHORT).show();

            selectedTask.setEstadoCompletado(!selectedTask.isEstadoCompletado());
            taskViewModel.updateTask(selectedTask);
        });

        tasksListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task taskToDelete = currentTasks.get(position);
            taskViewModel.deleteTask(taskToDelete.getId());
            Toast.makeText(TaskActivity.this, "Mantén presionado para eliminar", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}