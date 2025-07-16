package com.grupoC.orgaedu.features.courses.presentacion.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CursoEntity curso);
    }

    private List<CursoEntity> cursos;
    private final OnItemClickListener listener;

    public CourseAdapter(List<CursoEntity> cursos, OnItemClickListener listener) {
        this.cursos = cursos;
        this.listener = listener;
    }

    public void updateData(List<CursoEntity> nuevaLista) {
        this.cursos = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.bind(cursos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cursos != null ? cursos.size() : 0;
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitulo;
        private final TextView tvDescripcion;
        private final TextView tvFecha;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo      = itemView.findViewById(R.id.tvCourseTitle);
            tvDescripcion = itemView.findViewById(R.id.tvCourseDescription);
            tvFecha       = itemView.findViewById(R.id.tvCourseDate);
        }

        public void bind(CursoEntity curso, OnItemClickListener listener) {
            tvTitulo.setText(curso.getTitulo());
            tvDescripcion.setText(curso.getDescripcion());
            tvFecha.setText(curso.getFecha());
            itemView.setOnClickListener(v -> listener.onItemClick(curso));
        }
    }
}
