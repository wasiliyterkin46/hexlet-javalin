package org.example.hexlet.repository;

import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepository {
    private static List<Course> entities = new ArrayList<>();

    public static void save(Course course) {
        course.setId((long) entities.size() + 1);
        entities.add(course);
    }

    public static List<Course> search(String term) {
        return entities.stream()
                .filter(entity -> entity.getName().toLowerCase().startsWith(term.toLowerCase()))
                .toList();
    }

    public static Optional<Course> find(Long id) {
        return entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }

    public static Optional<Course> find(String idString) {
        Long idLong = Long.parseLong(idString);
        return find(idLong);
    }

    public static void delete(Long id) {
        entities.removeIf(course -> course.getId() == id);
    }

    public static void delete(String idString) {
        Long idLong = Long.parseLong(idString);
        delete(idLong);
    }

    public static void removeAll() {
        entities = new ArrayList<>();
    }

    public static List<Course> getEntities() {
        return entities;
    }
}
