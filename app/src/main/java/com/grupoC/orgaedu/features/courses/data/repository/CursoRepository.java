package com.grupoC.orgaedu.features.courses.data.repository;

import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;
import java.util.List;

public interface CursoRepository {
    long insertCurso(CursoEntity curso);
    List<CursoEntity> getCursosPorUsuario(String userId);
    CursoEntity getCursoById(long id);
    int updateCurso(CursoEntity curso);
    int deleteCurso(long id);
}
