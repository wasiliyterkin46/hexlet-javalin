@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.BuildCoursePage
@param BuildCoursePage page

@template.layout.page(
    courseBuild = @`
        @if(page.getErrors() != null)
              <h3>Ошибки создания курса</h3>
              <ul>
                  @for(var validator : page.getErrors().values())
                      @for(var error : validator)
                          <li>${error.getMessage()}</li>
                      @endfor
                  @endfor
              </ul>
        @endif

        <form action="${NamedRoutes.coursesPath()}" method="post">
          <div>
            <label>
              Название курса
              <input type="text" required name="name" value="${page.getName()}" />
            </label>
          </div>
          <div>
            <label>
              Описание курса
              <textarea name="description">${page.getDescription()}</textarea>
            </label>
          </div>
          <input type="submit" value="Создать курс" />
        </form>
    `,
    headerTemplate = @`
        <h1>Создание курса</h1>
    `
)
