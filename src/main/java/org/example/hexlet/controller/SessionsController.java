package org.example.hexlet.controller;

import io.javalin.http.Context;
import org.example.hexlet.dto.sessions.AuthorizationPage;

import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;

public class SessionsController {
    public static void built(Context ctx) {
        ctx.render("sessions/built.jte");
    }

    public static void createOrDestroy(Context ctx) {
        Boolean sessionMustBeDestroyed = ctx.formParamAsClass("_delete", Boolean.class).getOrDefault(false);
        if (sessionMustBeDestroyed) {
            ctx.removeCookie("JSESSIONID", "/");
        } else {
            var nickname = ctx.formParam("nickname");
            ctx.sessionAttribute("currentUser", String.valueOf(nickname));
        }

        ctx.redirect("/");
    }
}
