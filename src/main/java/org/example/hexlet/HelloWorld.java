package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.controller.UsersController;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.dto.MainPage;

import java.time.LocalDateTime;

import static io.javalin.rendering.template.TemplateUtil.model;

public final class HelloWorld {
    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }

    private static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.before(ctx -> System.out.println(LocalDateTime.now()));

        app.get(NamedRoutes.mainPath(), ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            var page = new MainPage(visited);
            ctx.cookie("visited", String.valueOf(true));
            ctx.render("layout/page.jte", model("page", page));
        });
        app.get(NamedRoutes.usersPath(), UsersController::index);
        app.get(NamedRoutes.buildUserPath(), UsersController::build);
        app.get(NamedRoutes.userPath("{id}"), UsersController::show);
        app.post(NamedRoutes.usersPath(), UsersController::create);
        app.get(NamedRoutes.userEdit("{id}"), UsersController::edit);
        app.post(NamedRoutes.userPath("{id}"), UsersController::update);

        app.get(NamedRoutes.coursesPath(), CoursesController::index);
        app.get(NamedRoutes.buildCoursePath(), CoursesController::build);
        app.get(NamedRoutes.coursePath("{id}"), CoursesController::show);
        app.post(NamedRoutes.coursesPath(), CoursesController::create);
        app.get(NamedRoutes.courseEdit("{id}"), CoursesController::edit);
        app.post(NamedRoutes.coursePath("{id}"), CoursesController::update);

        return app;
    }

}

