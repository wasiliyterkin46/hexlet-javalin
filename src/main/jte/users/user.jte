@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.UserPage
@param UserPage page

<!doctype html>
<html lang="ru">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Данные пользователя</title>
    </head>
    <body>
        <main>
            <h1>${page.getUser().getName()}</h1>
            <p>${page.getUser().getEmail()}</p>
        </main>

        <div>
            <form action="${NamedRoutes.userEdit(page.getUser().getId())}" method="get">
                <input type="submit" value="Изменить данные пользователя" />
            </form>
        </div>

        <div>
            <form action="${NamedRoutes.usersPath()}" method="post">
                <input type="hidden" name="_delete" value="${page.getUser().getId()}" />
                <input type="submit" value="Удалить пользователя" />
            </form>
        </div>

    </body>
</html>
