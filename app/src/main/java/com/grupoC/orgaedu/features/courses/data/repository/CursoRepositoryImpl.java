package com.grupoC.orgaedu.features.courses.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grupoC.orgaedu.features.courses.data.local.CursosDbHelper;
import com.grupoC.orgaedu.features.courses.data.model.CursoEntity;

import java.util.List;

public class CursoRepositoryImpl implements CursoRepository {
    private final CursosDbHelper dbHelper;

    public CursoRepositoryImpl(Context context) {
        this.dbHelper = new CursosDbHelper(context);
    }

    @Override
    public long insertCurso(CursoEntity curso) {
        return dbHelper.insertCurso(curso);
    }

    @Override
    public List<CursoEntity> getCursosPorUsuario(String userId) {
        return dbHelper.getCursosPorUsuario(userId);
    }

    @Override
    public CursoEntity getCursoById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                CursosDbHelper.TABLE_CURSOS,
                null,
                CursosDbHelper.COL_ID + " = ?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );
        CursoEntity curso = null;
        if (c.moveToFirst()) {
            curso = new CursoEntity(
                    c.getLong(c.getColumnIndexOrThrow(CursosDbHelper.COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(CursosDbHelper.COL_TITULO)),
                    c.getString(c.getColumnIndexOrThrow(CursosDbHelper.COL_DESCRIPCION)),
                    c.getString(c.getColumnIndexOrThrow(CursosDbHelper.COL_FECHA)),
                    c.getString(c.getColumnIndexOrThrow(CursosDbHelper.COL_USER_ID))
            );
        }
        c.close();
        return curso;
    }

    @Override
    public int updateCurso(CursoEntity curso) {
        return dbHelper.updateCurso(curso);
    }

    @Override
    public int deleteCurso(long id) {
        return dbHelper.deleteCurso(id);
    }
}

