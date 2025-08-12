package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.dto.main.VisitPage;
import org.example.hexlet.dto.sessions.AuthorizationPage;
import org.example.hexlet.util.NamedRoutes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

        addLoggingTimeRequest(app);
        addHandlers(app);

        return app;
    }

    private static Javalin addLoggingTimeRequest(Javalin app) {
        app.before(ctx -> System.out.println(LocalDateTime.now()));
        return app;
    }

    private static Javalin addHandlers(Javalin app) {
        app.get(NamedRoutes.mainPath(), ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited"));
            var pageVisited = new VisitPage(visited);
            ctx.cookie("visited", "true");

            String nickname = ctx.sessionAttribute("currentUser");
            AuthorizationPage authorizationPage = new AuthorizationPage(nickname);

            Map<String, Object> model = new HashMap<>();
            model.put("pageVisited", pageVisited);
            model.put("authorizationPage", authorizationPage);

            ctx.render("index.jte", model);
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

        app.get(NamedRoutes.builtSessionsPath(), SessionsController::built);
        app.post(NamedRoutes.sessionsPath(), SessionsController::createOrDestroy);

        return app;
    }
}

