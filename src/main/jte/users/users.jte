@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    users = @`
        @if(!page.getUsersExist())
            <p>Пока не добавлено ни одного пользователя</p>
        @endif

        @if(page.getUsers().isEmpty() && page.getUsersExist())
            <p>Нет пользователя с именем, начинающимся на "${page.getTermName()}"</p>
        @endif

        <form action="/users/build" method="get">
            <input type="submit" value="New user" />
        </form>

        @if(!page.getUsers().isEmpty())
            <form action="/users" method="get">
                <label>
                    Введите начало имени для поиска
                    <input type="search" name="termName" value="${page.getTermName()}" />
                </label>
                <input type="submit" value="Search" />
           </form>

            <div>
                <table>
                    <tr>
                        <th>ИМЯ</th>
                        <th>EMAIL</th>
                    </tr>
                    @for(var user : page.getUsers())
                        <tr>
                            <td>${user.getName()}</td>
                            <td><b><a href="/users/${user.getId()}">${user.getEmail()}</a></b></td>
                        </tr>
                    @endfor
                </table>
            </div>
        @endif
    `,
    headerTemplate = @`
        <h1>Список пользователей</h1>
    `
)
