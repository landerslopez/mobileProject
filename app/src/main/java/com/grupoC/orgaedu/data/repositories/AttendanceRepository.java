package com.grupoC.orgaedu.data.repositories;

import com.grupoC.orgaedu.data.database.AppDatabase;
import com.grupoC.orgaedu.data.database.dao.AttendanceDao;
import com.grupoC.orgaedu.data.database.entities.Attendance;

import java.util.List;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AttendanceRepository {
    private final AttendanceDao attendanceDao;

    public AttendanceRepository(AttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    public Future<Long> insertAttendance(Attendance attendance) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return attendanceDao.insert(attendance);
            }
        });
    }

    public Future<List<Attendance>> getAttendanceByUserId(int userId) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<List<Attendance>>() {
            @Override
            public List<Attendance> call() throws Exception {
                return attendanceDao.getAttendanceByUserId(userId);
            }
        });
    }

    public Future<Attendance> getAttendanceByUserIdAndDate(int userId, Date date) {
        return AppDatabase.databaseWriteExecutor.submit(new Callable<Attendance>() {
            @Override
            public Attendance call() throws Exception {
                return attendanceDao.getAttendanceByUserIdAndDate(userId, date.getTime());
            }
        });
    }
}
