package org.example.hexlet;

public class NamedRoutes {

    public static String mainPath() {
        return "/";
    }

    public static String usersPath() {
        return "/users";
    }

    public static String buildUserPath() {
        return "/users/build";
    }

    public static String userPath(String idString) {
        return "/users/" + idString;
    }

    public static String userPath(Long idLong) {
        String idString = String.valueOf(idLong);
        return userPath(idString);
    }

    public static String userEdit(String idString) {
        return "/users/" + idString + "/edit";
    }

    public static String userEdit(Long idLong) {
        String idString = String.valueOf(idLong);
        return userEdit(idString);
    }

    public static String coursesPath() {
        return "/courses";
    }

    public static String buildCoursePath() {
        return "/courses/build";
    }

    public static String coursePath(String idString) {
        return "/courses/" + idString;
    }

    public static String coursePath(Long idLong) {
        String idString = String.valueOf(idLong);
        return coursePath(idString);
    }

    public static String courseEdit(String idString) {
        return "/courses/" + idString + "/edit";
    }

    public static String courseEdit(Long idLong) {
        String idString = String.valueOf(idLong);
        return courseEdit(idString);
    }
}
