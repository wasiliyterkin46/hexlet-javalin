package org.example.hexlet.repository;

import org.example.hexlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static List<User> entities = new ArrayList<>();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        entities.add(user);
    }

    public static List<User> search(String term) {
        return entities.stream()
                .filter(entity -> entity.getName().toLowerCase().startsWith(term.toLowerCase()))
                .toList();
    }

    public static Optional<User> find(Long id) {
        return entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
    }

    public static Optional<User> find(String idString) {
        Long idLong = Long.parseLong(idString);
        return find(idLong);
    }

    public static void delete(Long id) {
        entities.removeIf(user -> user.getId() == id);
    }

    public static void delete(String idString) {
        Long idLong = Long.parseLong(idString);
        delete(idLong);
    }

    public static void removeAll() {
        entities = new ArrayList<>();
    }

    public static List<User> getEntities() {
        return entities;
    }
}
