package com.grupoC.orgaedu.features.courses.presentacion.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;
import com.grupoC.orgaedu.features.courses.data.repository.CursoRepository;
import com.grupoC.orgaedu.features.courses.data.repository.CursoRepositoryImpl;

import java.util.List;

public class CoursesViewModel extends ViewModel {
    private final CursoRepository repo;
    private final MutableLiveData<List<CursoEntity>> _cursos = new MutableLiveData<>();
    public LiveData<List<CursoEntity>> cursos = _cursos;

    private final MutableLiveData<CursoEntity> _cursoSeleccionado = new MutableLiveData<>();
    public LiveData<CursoEntity> cursoSeleccionado = _cursoSeleccionado;

    // Constructor package‐private: sólo el Factory debe usarlo
    CoursesViewModel(CursoRepository repo) {
        this.repo = repo;
    }

    public void loadCursos(String userId) {
        List<CursoEntity> lista = repo.getCursosPorUsuario(userId);
        _cursos.setValue(lista);
    }

    public void createCurso(CursoEntity curso) {
        long id = repo.insertCurso(curso);
        curso.setId(id);
        loadCursos(curso.getUserId());
    }

    public void selectCurso(long id) {
        CursoEntity c = repo.getCursoById(id);
        _cursoSeleccionado.setValue(c);
    }

    public void updateCurso(CursoEntity curso) {
        repo.updateCurso(curso);
        loadCursos(curso.getUserId());
    }

    public void deleteCurso(long id, String userId) {
        repo.deleteCurso(id);
        loadCursos(userId);
    }
}
