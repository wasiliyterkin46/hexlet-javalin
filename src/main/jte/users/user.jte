@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
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
    `,
    headerTemplate = @`
        <h1>Данные пользователя</h1>
    `
)
