package org.example.hexlet;

import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        addHelloName(app);
        addDynamicRoute(app);

        app.start(7070); // Стартуем веб-сервер
    }

    private static void addHelloName(Javalin app) {
        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result(String.format("Hello, %s!", name));
        });
    }

    private static void addDynamicRoute(Javalin app) {
        app.get("/users/{id}/lessons/{postId}", ctx -> {
            var id = ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("User ID: " + id + " Post ID: " + postId);
        });
    }
}

