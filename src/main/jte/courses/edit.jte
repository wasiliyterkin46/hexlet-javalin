@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.courses.EditCoursePage
@param EditCoursePage page

@template.layout.page(
    content = @`
        @if(page.getErrors() != null)
              <h3>Ошибки обновления курса</h3>
              <ul>
                  @for(var validator : page.getErrors().values())
                      @for(var error : validator)
                          <li>${error.getMessage()}</li>
                      @endfor
                  @endfor
              </ul>
          @endif

          <form action="${NamedRoutes.coursePath(page.getCourse().getId())}" method="post">
            <div>
              <label>
                Название курса
                <input type="text" required name="name" value="${page.getCourse().getName()}" />
              </label>
            </div>
            <div>
              <label>
                Описание курса
                <textarea name="description">${page.getCourse().getDescription()}</textarea>
              </label>
            </div>
            <input type="submit" value="Обновить" />
          </form>
    `,
    headerTemplate = @`
        <h1>Изменение курса</h1>
    `
)
