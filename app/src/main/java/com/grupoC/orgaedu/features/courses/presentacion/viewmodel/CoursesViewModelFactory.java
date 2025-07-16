package com.grupoC.orgaedu.features.courses.presentacion.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.grupoC.orgaedu.features.courses.data.repository.CursoRepositoryImpl;

public class CoursesViewModelFactory implements ViewModelProvider.Factory {
    private final Context appContext;

    public CoursesViewModelFactory(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CoursesViewModel.class)) {
            return (T) new CoursesViewModel(
                    new CursoRepositoryImpl(appContext)
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

