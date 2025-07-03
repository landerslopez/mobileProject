package com.abraham.loginapp.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.model.Curso;

import java.util.List;

public class CursoDocenteAdapter extends RecyclerView.Adapter<CursoDocenteAdapter.CursoViewHolder> {

    private Context context;
    private List<Curso> listaCursos;
    private int docenteId; // ✅ Agregado

    public CursoDocenteAdapter(Context context, List<Curso> listaCursos, int docenteId) {
        this.context = context;
        this.listaCursos = listaCursos;
        this.docenteId = docenteId; // ✅ Guardar docenteId
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_curso_docente, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = listaCursos.get(position);
        holder.textNombreCurso.setText(curso.getNombre());

        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleCursoDocente.class);
            intent.putExtra("curso_id", curso.getId());
            intent.putExtra("curso_nombre", curso.getNombre());
            intent.putExtra("docente_id", docenteId); // ✅ Pasar también el ID del docente
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaCursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombreCurso;
        Button btnVerDetalles;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombreCurso = itemView.findViewById(R.id.textNombreCurso);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
        }
    }
}
