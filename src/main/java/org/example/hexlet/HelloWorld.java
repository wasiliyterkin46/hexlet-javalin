package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.http.NotFoundResponse;

import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.UsersPage;
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
            ctx.render("courses/courseBuild.jte");
        });

        app.post("/courses", ctx -> {
            var name = ctx.formParam("name");
            var description = ctx.formParam("description");

            var course = new Course(name, description);
            CourseRepository.save(course);
            ctx.redirect("/courses");
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
            var user = new User("", "", "");
            var page = new UserPage(user);
            ctx.render("users/userBuild.jte", model("page", page));
        });

        app.post("/users", ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();
            var pass = ctx.formParam("password");
            var passConfirm = ctx.formParam("passwordConfirmation");

            var user = new User(name, email, pass);

            if (isValidUserData(pass, passConfirm)) {
                UserRepository.save(user);
                ctx.redirect("/users");
            } else {
                var page = new UserPage(user);
                String message = getMessageOnErrorSaveUser(pass, passConfirm);
                message = message + "\n" + "Исправьте данные и повторите регистрацию.";
                page.setMessageOnErrorSaveUser(message);
                page.setIsErrorDataRegistration(true);
                ctx.render("users/userBuild.jte", model("page", page));
            }
        });

    }

    private static boolean isValidUserData(String pass, String passConfirm) {
        if (!pass.equals(passConfirm)) {
            return false;
        }
        if (pass.equals("")) {
            return false;
        }

        return true;
    }

    private static String getMessageOnErrorSaveUser(String pass, String passConfirm) {
        if (!pass.equals(passConfirm)) {
            return "Подтверждение пароля и пароль не совпадают!";
        }
        if (pass.equals("")) {
            return "Пароль не может быть пустой строкой";
        }

        return "Неизвестная ошибка";
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

