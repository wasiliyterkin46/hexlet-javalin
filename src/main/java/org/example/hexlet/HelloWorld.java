package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.controller.CoursesController;
import org.example.hexlet.dto.main.VisitPage;
import org.example.hexlet.dto.sessions.AuthorizationPage;
import org.example.hexlet.util.NamedRoutes;
import org.example.hexlet.repository.BaseRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class HelloWorld {
    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }

    private static Javalin getApp() {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:hexlet_project;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.setDataSource(dataSource);

        // Получаем путь до файла в src/main/resources
        var url = HelloWorld.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        // Получаем соединение, создаем стейтмент и выполняем запрос
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        addLoggingTimeRequest(app);
        addHandlers(app);
        clearAllCookie(app);

        return app;
    }

    private static Javalin addLoggingTimeRequest(Javalin app) {
        app.before(ctx -> System.out.println(LocalDateTime.now()));
        return app;
    }

    private static Javalin clearAllCookie(Javalin app) {
        app.after(NamedRoutes.deleteSessionsPath(), ctx -> ctx.removeCookie("visited", "/"));
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
        app.post(NamedRoutes.createSessionsPath(), SessionsController::create);
        app.post(NamedRoutes.deleteSessionsPath(), SessionsController::destroy);

        return app;
    }
}

