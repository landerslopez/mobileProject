package com.grupoC.orgaedu.features.notas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grupoC.orgaedu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CursoNotasAdapter extends RecyclerView.Adapter<CursoNotasAdapter.CursoViewHolder> {
    private Map<String, List<Nota>> notasPorCurso;

    public CursoNotasAdapter(Map<String, List<Nota>> notasPorCurso) {
        this.notasPorCurso = notasPorCurso;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_curso, parent, false);
        return new CursoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        String curso = new ArrayList<>(notasPorCurso.keySet()).get(position);
        List<Nota> notas = notasPorCurso.get(curso);

        holder.txtCurso.setText(curso);
        holder.recyclerNotas.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerNotas.setAdapter(new NotasAdapter(notas));
    }

    @Override
    public int getItemCount() {
        return notasPorCurso.size();
    }

    static class CursoViewHolder extends RecyclerView.ViewHolder {
        TextView txtCurso;
        RecyclerView recyclerNotas;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCurso = itemView.findViewById(R.id.txtCurso);
            recyclerNotas = itemView.findViewById(R.id.recyclerNotas);
        }
    }
}
