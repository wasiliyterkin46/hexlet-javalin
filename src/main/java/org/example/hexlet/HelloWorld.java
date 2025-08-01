package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // Обработчик маршрута для динамического формирования страницы с перечислением курсов
        app.get("/courses", ctx -> {

            Course c1 = new Course("Курс 1", "Курс про жизнь");
            c1.setId(1L);
            Course c2 = new Course("Курс 2", "Курс про смерть");
            c2.setId(2L);

            var courses = List.of(c1, c2);
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("index.jte", model("page", page));
        });

        // Обработчик маршрута для динамического формирования страниц с информацией о курсах
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = new Course("Название курса " + id, "Описание курса " + id);
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.start(7070);
    }
}

