package com.abraham.loginapp.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abraham.loginapp.R;
import com.abraham.loginapp.config.Database;
import com.abraham.loginapp.model.Asistencia;

import java.util.List;

public class AsistenciaFragment extends Fragment {

    private static final String ARG_USER_ID = "ARG_USER_ID";

    private AdaptadorAsistencia adapter;
    private Database db;
    private int userId;

    /** Usar este método para crear el fragment con el userId */
    public static AsistenciaFragment newInstance(int userId) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        AsistenciaFragment fragment = new AsistenciaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Infla el layout con el RecyclerView
        return inflater.inflate(R.layout.fragment_asistencia, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        // Recupera el userId de los argumentos
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID, -1);
        }

        // Inicializa RecyclerView y su adaptador
        RecyclerView rv = view.findViewById(R.id.recyclerAsistencia);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AdaptadorAsistencia();
        rv.setAdapter(adapter);

        // Carga datos desde la base de datos
        db = new Database(requireContext());
        List<Asistencia> lista = db.obtenerAsistencias(userId);
        adapter.setLista(lista);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Cierra la BD si lo deseas
        if (db != null) {
            db.close();
        }
    }
}
