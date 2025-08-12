@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
    content = @`
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
    `,
    headerTemplate = @`
        <h1>Данные курса</h1>
    `
)
