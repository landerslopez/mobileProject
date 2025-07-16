package com.grupoC.orgaedu.features.courses.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tuapp.features.courses.data.model.CursoEntity;

import java.util.ArrayList;
import java.util.List;

public class CursosDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "app_estudios.db";
    private static final int    DB_VERSION = 1;

    public static final String TABLE_CURSOS    = "cursos";
    public static final String COL_ID          = "id";
    public static final String COL_TITULO      = "titulo";
    public static final String COL_DESCRIPCION = "descripcion";
    public static final String COL_FECHA       = "fecha";
    public static final String COL_USER_ID     = "userId";

    public CursosDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_CURSOS + " ("
                + COL_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITULO      + " TEXT NOT NULL, "
                + COL_DESCRIPCION + " TEXT, "
                + COL_FECHA       + " TEXT, "
                + COL_USER_ID     + " TEXT NOT NULL"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURSOS);
        onCreate(db);
    }

    // INSERT
    public long insertCurso(CursoEntity c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITULO,      c.getTitulo());
        cv.put(COL_DESCRIPCION, c.getDescripcion());
        cv.put(COL_FECHA,       c.getFecha());
        cv.put(COL_USER_ID,     c.getUserId());
        return db.insert(TABLE_CURSOS, null, cv);
    }

    // QUERY por usuario
    public List<CursoEntity> getCursosPorUsuario(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                TABLE_CURSOS,
                null,
                COL_USER_ID + " = ?",
                new String[]{ userId },
                null, null, COL_FECHA + " DESC"
        );

        List<CursoEntity> lista = new ArrayList<>();
        while (c.moveToNext()) {
            CursoEntity curso = new CursoEntity(
                    c.getLong(c.getColumnIndexOrThrow(COL_ID)),
                    c.getString(c.getColumnIndexOrThrow(COL_TITULO)),
                    c.getString(c.getColumnIndexOrThrow(COL_DESCRIPCION)),
                    c.getString(c.getColumnIndexOrThrow(COL_FECHA)),
                    c.getString(c.getColumnIndexOrThrow(COL_USER_ID))
            );
            lista.add(curso);
        }
        c.close();
        return lista;
    }

    // UPDATE
    public int updateCurso(CursoEntity c) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITULO,      c.getTitulo());
        cv.put(COL_DESCRIPCION, c.getDescripcion());
        cv.put(COL_FECHA,       c.getFecha());
        return db.update(
                TABLE_CURSOS,
                cv,
                COL_ID + " = ?",
                new String[]{ String.valueOf(c.getId()) }
        );
    }

    // DELETE
    public int deleteCurso(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(
                TABLE_CURSOS,
                COL_ID + " = ?",
                new String[]{ String.valueOf(id) }
        );
    }
}
