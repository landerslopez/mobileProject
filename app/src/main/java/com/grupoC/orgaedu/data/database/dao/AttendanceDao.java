package com.grupoC.orgaedu.data.database.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.grupoC.orgaedu.data.database.entities.Attendance;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert
    long insert(Attendance attendance);

    @Query("SELECT * FROM asistencias WHERE id_usuario = :userId ORDER BY fecha DESC")
    List<Attendance> getAttendanceByUserId(int userId);

    @Query("SELECT * FROM asistencias WHERE id_usuario = :userId AND fecha = :date LIMIT 1")
    Attendance getAttendanceByUserIdAndDate(int userId, long date); // `long` para trabajar con el timestamp de Date
}