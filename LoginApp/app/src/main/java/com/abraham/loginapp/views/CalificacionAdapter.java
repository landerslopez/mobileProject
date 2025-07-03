package com.abraham.loginapp.views;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Calificacion;

import java.util.List;

public class CalificacionAdapter extends RecyclerView.Adapter<CalificacionAdapter.ViewHolder> {

    private Context context;
    private List<Calificacion> calificaciones;
    private int tareaId;


    public CalificacionAdapter(Context context, List<Calificacion> calificaciones, int tareaId) {
        this.context = context;
        this.calificaciones = calificaciones;
        this.tareaId = tareaId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calificacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalificacionAdapter.ViewHolder holder, int position) {
        Calificacion c = calificaciones.get(position);

        // Si no tienes getNombreEstudiante(), muestra el ID u otro dato
        holder.txtNombre.setText("Estudiante ID: " + c.getEstudianteId());

        holder.txtNota.setText("Nota: " + c.getNota());

        holder.itemView.setOnClickListener(v -> mostrarDialogoNota(c, holder));
    }

    @Override
    public int getItemCount() {
        return calificaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtNota;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.textNombreEstudiante);
            txtNota = itemView.findViewById(R.id.textNota);
        }
    }

    private void mostrarDialogoNota(Calificacion calificacion, ViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar nota");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Nueva nota (0-100)");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            try {
                int nuevaNota = Integer.parseInt(input.getText().toString());

                if (nuevaNota < 0 || nuevaNota > 100) {
                    Toast.makeText(context, "Nota inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                Database db = new Database(context);
                boolean actualizado = db.actualizarNota(tareaId, calificacion.getEstudianteId(), nuevaNota); // ✅ correcto


                if (actualizado) {
                    calificacion.setNota(nuevaNota);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Nota actualizada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
