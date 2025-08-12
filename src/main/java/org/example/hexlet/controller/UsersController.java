package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.example.hexlet.util.NamedRoutes;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.EditUserPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.List;

public class UsersController {
    public static void index(Context ctx) {
        var term = ctx.queryParam("termName");

        List<User> users;
        if (term != null) {
            users = UserRepository.search(term);
        } else {
            users = UserRepository.getEntities();
            term = "";
        }

        var usersExist = !UserRepository.getEntities().isEmpty();
        String flash = ctx.consumeSessionAttribute("flash");

        var page = new UsersPage(users, term, usersExist, flash);

        ctx.render("users/users.jte", model("page", page));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParam("id");
        var user = UserRepository.find(id) // Ищем пользователя в базе по id
                .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
        var page = new UserPage(user);

        ctx.render("users/user.jte", model("page", page));
    }

    public static void build(Context ctx) {
        var page = new BuildUserPage();
        ctx.render("users/userBuild.jte", model("page", page));
    }

    public static void create(Context ctx) {
        String toDeleteUser = ctx.formParam("_delete");
        if (toDeleteUser == null) {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                        .check(value -> value.length() > 6, "У пароля недостаточная длина")
                        .get();
                var user = new User(name, email, password);
                UserRepository.save(user);
                ctx.sessionAttribute("flash", "User has been created!");
                ctx.redirect(NamedRoutes.usersPath());
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.status(422);
                ctx.render("users/userBuild.jte", model("page", page));
            }
        } else {
            var id = ctx.formParam("_delete");
            UserRepository.delete(id);
            ctx.redirect(NamedRoutes.usersPath());
        }
    }

    public static void edit(Context ctx) {
        var id = ctx.pathParam("id");
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new EditUserPage(user, null);
        ctx.render("users/edit.jte", model("page", page));
    }

    public static void update(Context ctx) {
        var id = ctx.pathParam("id");
        var name = ctx.formParam("name");
        var email = ctx.formParam("email");
        var password = ctx.formParam("password");
        var passwordConfirmation = ctx.formParam("passwordConfirmation");

        try {
            ctx.formParamAsClass("password", String.class)
                    .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                    .check(value -> value.length() > 6, "У пароля недостаточная длина")
                    .get();

            var user = UserRepository.find(id)
                    .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            ctx.redirect(NamedRoutes.userPath(id));
        } catch (ValidationException e) {
            User user = new User(name, email, password);
            user.setId(Long.parseLong(id));
            var page = new EditUserPage(user, e.getErrors());
            ctx.status(422);
            ctx.render("users/edit.jte", model("page", page));
        }
    }

}
