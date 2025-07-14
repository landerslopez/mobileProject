package com.abraham.loginapp.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.model.Asistencia;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorAsistencia
        extends RecyclerView.Adapter<AdaptadorAsistencia.VH> {

    private final List<Asistencia> lista = new ArrayList<>();

    public void setLista(List<Asistencia> nueva) {
        lista.clear();
        lista.addAll(nueva);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asistencia, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Asistencia a = lista.get(position);
        holder.tvFecha.setText(a.getFecha());
        holder.tvEstado.setText(a.getEstado());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView tvFecha, tvEstado;
        VH(View v) {
            super(v);
            tvFecha  = v.findViewById(R.id.tvFechaItem);
            tvEstado = v.findViewById(R.id.tvEstadoItem);
        }
    }
}

