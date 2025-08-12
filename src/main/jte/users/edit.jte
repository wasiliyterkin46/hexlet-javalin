@import org.example.hexlet.util.NamedRoutes
@import org.example.hexlet.dto.users.EditUserPage
@param EditUserPage page

@template.layout.page(
    content = @`
        @if(page.getErrors() != null)
              <h3>Ошибки обновления пользователя</h3>
              <ul>
                  @for(var validator : page.getErrors().values())
                      @for(var error : validator)
                          <li>${error.getMessage()}</li>
                      @endfor
                  @endfor
              </ul>
          @endif

          <form action="${NamedRoutes.userPath(page.getUser().getId())}" method="post">
            <div>
              <label>
                Имя
                <input type="text" name="name" value="${page.getUser().getName()}" />
              </label>
            </div>
            <div>
              <label>
                Email
                <input type="email" required name="email" value="${page.getUser().getEmail()}" />
              </label>
            </div>
            <div>
              <label>
                Пароль
                <input type="password" required name="password" value="${page.getUser().getPassword()}" />
              </label>
            </div>
            <div>
              <label>
                Подтверждение пароля
                <input type="password" required name="passwordConfirmation" value="${page.getUser().getPassword()}" />
              </label>
            </div>
            <input type="submit" value="Обновить" />
          </form>
    `,
    headerTemplate = @`
        <h1>Изменение пользователя</h1>
    `
)
