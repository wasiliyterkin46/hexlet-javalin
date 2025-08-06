<!-- src/main/jte/courses/show.jte -->

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
    </body>
</html>
