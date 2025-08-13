package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.example.hexlet.util.NamedRoutes;
import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.EditCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.repository.CourseRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.util.List;

public class CoursesController  {
    public static void index(Context ctx) throws SQLException {
        var term = ctx.queryParam("term");

        List<Course> courses;
        if (term != null) {
            courses = CourseRepository.search(term);
        } else {
            courses = CourseRepository.getEntities();
            term = "";
        }

        var coursesExist = !CourseRepository.getEntities().isEmpty();
        var page = new CoursesPage(courses, term, coursesExist);

        ctx.render("courses/courses.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParam("id");
        var course = CourseRepository.find(id) // Ищем пользователя в базе по id
                .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
        var page = new CoursePage(course);

        ctx.render("courses/course.jte", model("page", page));
    }

    public static void build(Context ctx) {
        var page = new BuildCoursePage();
        ctx.render("courses/courseBuild.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        String toDeleteCourse = ctx.formParam("_delete");
        if (toDeleteCourse == null) {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            try {
                ctx.formParamAsClass("name", String.class)
                        .check(value -> value.length() > 2, "Название курса должно быть длиннее 2-х символов")
                        .get();
                ctx.formParamAsClass("description", String.class)
                        .check(value -> value.length() > 10, "Описание курса должно быть длиннее 10-ти символов")
                        .get();

                var course = new Course(name, description);
                CourseRepository.save(course);
                ctx.redirect(NamedRoutes.coursesPath());
            } catch (ValidationException e) {
                var page = new BuildCoursePage(name, description, e.getErrors());
                ctx.status(422);
                ctx.render("courses/courseBuild.jte", model("page", page));
            }
        } else {
            var id = ctx.formParam("_delete");
            CourseRepository.delete(id);
            ctx.redirect(NamedRoutes.coursesPath());
        }
    }

    public static void edit(Context ctx) throws SQLException {
        var id = ctx.pathParam("id");
        var course = CourseRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new EditCoursePage(course, null);
        ctx.render("courses/edit.jte", model("page", page));
    }

    public static void update(Context ctx) throws SQLException {
        var id = ctx.pathParam("id");
        var name = ctx.formParam("name");
        var description = ctx.formParam("description");

        try {
            ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() > 2, "Название курса должно быть длиннее 2-х символов")
                    .get();
            ctx.formParamAsClass("description", String.class)
                    .check(value -> value.length() > 10, "Описание курса должно быть длиннее 10-ти символов")
                    .get();

            var course = CourseRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
            course.setName(name);
            course.setDescription(description);
            CourseRepository.update(course);
            ctx.redirect(NamedRoutes.coursePath(id));
        } catch (ValidationException e) {
            Course course = new Course(name, description);
            course.setId(Long.parseLong(id));
            var page = new EditCoursePage(course, e.getErrors());
            ctx.status(422);
            ctx.render("courses/edit.jte", model("page", page));
        }
    }

}
