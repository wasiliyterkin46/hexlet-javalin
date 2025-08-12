package org.example.hexlet.controller;

import io.javalin.http.Context;

public class SessionsController {
    public static void built(Context ctx) {
        ctx.render("sessions/built.jte");
    }

    public static void create(Context ctx) {
        var nickname = ctx.formParam("nickname");
        ctx.sessionAttribute("currentUser", String.valueOf(nickname));

        ctx.redirect("/");
    }

    public static void destroy(Context ctx) {
        Boolean sessionMustBeDestroyed = ctx.formParamAsClass("_delete", Boolean.class).getOrDefault(false);
        if (sessionMustBeDestroyed) {
            ctx.removeCookie("JSESSIONID", "/");
        }

        ctx.redirect("/");
    }
}
