package com.grupoC.orgaedu.features.courses.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grupoC.orgaedu.R;
import com.grupoC.orgaedu.data.database.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courses;
    private OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public CourseAdapter(List<Course> courses, OnCourseClickListener listener) {
        this.courses = courses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false); // Crea este layout XML
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewCourseName.setText(currentCourse.getNombreCurso());
        holder.textViewCourseDescription.setText(currentCourse.getDescripcion());
        // Puedes añadir más campos aquí, como el nombre del profesor si lo obtienes.

        holder.itemView.setOnClickListener(v -> listener.onCourseClick(currentCourse));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateCourses(List<Course> newCourses) {
        this.courses = newCourses;
        notifyDataSetChanged();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCourseName;
        private final TextView textViewCourseDescription;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
            textViewCourseDescription = itemView.findViewById(R.id.textViewCourseDescription);
        }
    }
}