@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Hello Hexlet!</title>
    </head>
    <body>
        <main>
            <h1>${page.getCourse().getName()}</h1>
            <p>${page.getCourse().getDescription()}</p>
        </main>

        <div>
            <form action="${NamedRoutes.courseEdit(page.getCourse().getId())}" method="get">
                <input type="submit" value="Изменить данные курса" />
            </form>
        </div>

        <div>
            <form action="${NamedRoutes.coursesPath()}" method="post">
                <input type="hidden" name="_delete" value="${page.getCourse().getId()}" />
                <input type="submit" value="Удалить курс" />
            </form>
        </div>

    </body>
</html>
