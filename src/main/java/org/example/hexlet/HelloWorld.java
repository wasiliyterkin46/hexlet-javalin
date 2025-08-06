package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import org.example.hexlet.dto.courses.BuildCoursePage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.CourseRepository;
import org.example.hexlet.repository.UserRepository;

import java.util.List;

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

        addHandlerMain(app);
        addHandlerCourses(app);
        addHandlerCoursesAdd(app);
        addHandlerCourse(app);
        addHandlerUsers(app);
        addHandlerUsersAdd(app);
        addHandlerUser(app);

        return app;
    }

    private static void addHandlerMain(Javalin app) {
        app.get("/", ctx -> ctx.render("layout/page.jte"));
    }

    private static void addHandlerCourses(Javalin app) {
        app.get("/courses", ctx -> {
            var term = ctx.queryParam("term");

            List<Course> courses;
            String termToPage;
            if (term != null) {
                courses = CourseRepository.search(term);
                termToPage = term;
            } else {
                courses = CourseRepository.getEntities();
                termToPage = "";
            }

            var coursesExist = !CourseRepository.getEntities().isEmpty();
            var page = new CoursesPage(courses, termToPage, coursesExist);

            ctx.render("courses/courses.jte", model("page", page));
        });
    }

    private static void addHandlerCoursesAdd(Javalin app) {
        app.get("/courses/build", ctx -> {
            var page = new BuildCoursePage();
            ctx.render("courses/courseBuild.jte", model("page", page));
        });

        app.post("/courses", ctx -> {
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
                ctx.redirect("/courses");
            } catch (ValidationException e) {
                var page = new BuildCoursePage(name, description, e.getErrors());
                ctx.render("courses/courseBuild.jte", model("page", page));
            }
        });

    }

    private static void addHandlerCourse(Javalin app) {
        app.get("/courses/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var course = CourseRepository.find(id) // Ищем пользователя в базе по id
                    .orElseThrow(() -> new NotFoundResponse("Course with id = " + id + " not found"));
            var page = new CoursePage(course);

            ctx.render("courses/course.jte", model("page", page));
        });
    }

    private static void addHandlerUsers(Javalin app) {
        app.get("/users", ctx -> {
            var term = ctx.queryParam("termName");

            List<User> users;
            String termToPage;
            if (term != null) {
                users = UserRepository.search(term);
                termToPage = term;
            } else {
                users = UserRepository.getEntities();
                termToPage = "";
            }

            var usersExist = !UserRepository.getEntities().isEmpty();
            var page = new UsersPage(users, termToPage, usersExist);

            ctx.render("users/users.jte", model("page", page));
        });
    }

    private static void addHandlerUsersAdd(Javalin app) {
        app.get("/users/build", ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/userBuild.jte", model("page", page));
        });

        app.post("/users", ctx -> {
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
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/userBuild.jte", model("page", page));
            }
        });

    }

    private static void addHandlerUser(Javalin app) {
        app.get("/users/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var user = UserRepository.find(id) // Ищем пользователя в базе по id
                    .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
            var page = new UserPage(user);

            ctx.render("users/user.jte", model("page", page));
        });
    }
}

