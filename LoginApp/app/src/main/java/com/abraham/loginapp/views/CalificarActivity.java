package com.abraham.loginapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Calificacion;

import java.util.List;

public class CalificarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CalificacionesAdapter adapter;
    private int cursoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);

        recyclerView = findViewById(R.id.recyclerCalificaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cursoId = getIntent().getIntExtra("curso_id", -1);

        cargarCalificaciones();
    }

    private void cargarCalificaciones() {
        Database db = new Database(this);
        List<Calificacion> lista = db.obtenerCalificacionesPorCurso(cursoId);
        adapter = new CalificacionesAdapter(lista);
        recyclerView.setAdapter(adapter);
    }

    class CalificacionesAdapter extends RecyclerView.Adapter<CalificacionesAdapter.ViewHolder> {

        private List<Calificacion> lista;

        CalificacionesAdapter(List<Calificacion> lista) {
            this.lista = lista;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtNombre;
            EditText editNota;
            Button btnActualizar;

            ViewHolder(View itemView) {
                super(itemView);
                txtNombre = itemView.findViewById(R.id.textNombreEstudiante);
                editNota = itemView.findViewById(R.id.editTextNota);
                btnActualizar = itemView.findViewById(R.id.btnActualizarNota);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calificacion, parent, false);
            return new ViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Calificacion c = lista.get(position);
            Database db = new Database(CalificarActivity.this);

            String nombreEstudiante = db.obtenerNombreEstudiantePorId(c.getEstudianteId());
            holder.txtNombre.setText(nombreEstudiante);
            holder.editNota.setText(String.valueOf(c.getNota()));

            holder.btnActualizar.setOnClickListener(v -> {
                try {
                    int nuevaNota = Integer.parseInt(holder.editNota.getText().toString());
                    boolean exito = db.actualizarNota(c.getTareaId(), c.getEstudianteId(), nuevaNota);
                    if (exito) {
                        Toast.makeText(CalificarActivity.this, "Nota actualizada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CalificarActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(CalificarActivity.this, "Nota inválida", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return lista.size();
        }
    }
}
