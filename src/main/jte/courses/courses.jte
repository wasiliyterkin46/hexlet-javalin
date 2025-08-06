@import org.example.hexlet.dto.courses.CoursesPage
@param CoursesPage page

@template.layout.page(
    courses = @`
        @if(!page.getCoursesExist())
            <p>Пока не добавлено ни одного курса</p>
        @endif

        @if(page.getCourses().isEmpty() && page.getCoursesExist())
            <p>Нет курсов, начинающихся на "${page.getTerm()}"</p>
        @endif

        <form action="/courses/build" method="get">
            <input type="submit" value="New curse" />
        </form>

        @if(!page.getCourses().isEmpty())
            <form action="/courses" method="get">
               <input type="search" name="term" value="${page.getTerm()}" />
               <input type="submit" value="Search" />
           </form>

            <div>
                <table>
                    <tr>
                        <th>НАЗВАНИЕ КУРСА</th>
                        <th>ОПИСАНИЕ КУРСА</th>
                    </tr>
                    @for(var course : page.getCourses())
                        <tr>
                            <td><b><a href="/courses/${course.getId()}">${course.getName()}</a></b></td>
                            <td><p>${course.getDescription()}</p></td>
                        </tr>
                    @endfor
                </table>
            </div>
        @endif
    `,
    headerTemplate = @`
        <h1>Список курсов</h1>
    `
)
