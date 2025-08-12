@import org.example.hexlet.dto.main.VisitPage
@import org.example.hexlet.dto.sessions.AuthorizationPage
@import org.example.hexlet.util.NamedRoutes

@param VisitPage pageVisited
@param AuthorizationPage authorizationPage

@template.layout.page(
    index = @`
        <h3><a href="/courses">Курсы</a></h3>
        <h3><a href="/users">Пользователи</a></h3>

        @if(!pageVisited.isVisited())
            Это сообщение показывается только один раз. Если вы хотите увидеть его снова, сотрите куки
        @endif

        @if(authorizationPage.getCurrentUser() != null)
            <p>
                Добро пожаловать, ${authorizationPage.getCurrentUser()}.
                Чтобы разлогиниться, удалите куку JSESSIONID из браузера
            </p>
            <div>
                <form action="/sessions" method="post">
                    <input type="hidden" name="_delete" value="true"/>
                    <input type="submit" value="Разлогиниться" />
                </form>
            </div>
        @else
            <form action="/sessions/built" method="get">
                <input type="submit" value="Залогиниться" />
            </form>
        @endif
    `
)
