@template.layout.page(
    courseBuild = @`
        <form action="/courses" method="post">
          <div>
            <label>
              Название курса
              <input type="text" required name="name" />
            </label>
          </div>
          <div>
            <label>
              Описание курса
              <textarea name="description"></textarea>
            </label>
          </div>
          <input type="submit" value="Создать курс" />
        </form>
    `,
    headerTemplate = @`
        <h1>Создание курса</h1>
    `
)
