@import gg.jte.Content

@param Content index = null
@param Content user = null
@param Content users = null
@param Content userBuild = null
@param Content userEdit = null
@param Content course = null
@param Content courses = null
@param Content courseBuild = null
@param Content courseEdit = null
@param Content title = null
@param Content headerTemplate = null
@param Content logging = null

<!doctype html>
<html lang="ru">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Hexlet Javalin Example</title>
    </head>

    <body>
        <header>
            @if(headerTemplate != null)
                ${headerTemplate}
            @else
                <h1>Меню сайта</h1>
            @endif
        </header>

        @if (index != null)
            ${index}
        @endif

        @if (logging != null)
            ${logging}
        @endif

        @if (user != null)
            ${user}
        @endif

        @if (users != null)
            ${users}
        @endif

        @if (userBuild != null)
            ${userBuild}
        @endif

        @if (userEdit != null)
            ${userEdit}
        @endif

        @if (course != null)
            ${course}
        @endif

        @if (courses != null)
            ${courses}
        @endif

        @if (courseBuild != null)
            ${courseBuild}
        @endif

        @if (courseEdit != null)
            ${courseEdit}
        @endif

        <div class="footer">
            <p><a href="https://github.com/wasiliyterkin46">Ссылка </a>на профиль в GitHub</p>
        </div>

    </body>
</html>
