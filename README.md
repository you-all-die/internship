### CheckStyle плагин для maven и idea

>Checkstyle - это инструмент разработки, который помогает программистам писать код Java,соответствующий стандарту
 кодирования. Он автоматизирует процесс проверки кода Java, чтобы избавить людей от этой скучной (но важной) задачи. 
 Это делает его идеальным для проектов, которые хотят обеспечить соблюдение стандарта кодирования.

Проверка стиля будет производиться в соответсвии
<br/>
с требованиями Sun Java Style Guide:
<br/>
https://www.oracle.com/java/technologies/javase/codeconventions-contents.html


**Проект не будет компилироваться, если присутствуют ошибки по checkstyle!!!**
<br/>
Для компиляции проекта, без проверок по checkstyle, закомментируйте в pom.xml у плагина maven-checkstyle-plugin секцию:
<br/>
`<executions> ... </executions>`

##### Maven:

генерируем и запускаем сайт с отчетом по проверки стиля:
<br/>
`mvn site:site ` - генерация сайта с документацией проекта
<br/>
`mvn site:run` - запуск сайта локально, будет доступен по адресу
<br/>
http://localhost:8888/ - вся документация
<br/>
http://localhost:8888/checkstyle.html - отчет по проверки стиля
<br/>
Что-бы избежать бага (не отображается исходный код, при нажатии на номер строки),
при первом запуске (после clean) необходимо перейти по ссылке(для генерации jxr файлов):
<br/>
http://localhost:8888/xref/index.html

##### Idea:

Скачиваем и устанавливаем CheckStyle плагин:
<br>
https://plugins.jetbrains.com/plugin/1065-checkstyle-idea
<br>
Настраиваем плагин:
<br>
`Settings -> Tools -> Checkstyle `
<br>
Устанавливаем `Configuration File`
<br>
выбираем файл из папки проекта:
`checkstyle.xml`

- Intellij IDEA
- maven
- git
- postgresql


Магазин должен предоставлять функционал:
1) Витрины товаров
2) Корзины
3) Оформление заказа
5) Админка: управление товаром,  управление категориями товаров, управление заказами, управление рассылками


Полезные ссылки

Java 11 https://www.azul.com/downloads/zulu-community/?architecture=x86-64-bit&package=jdk

Хорошая статья по старту проекта на Spring: https://habr.com/ru/post/333756/

О работе с Git: https://githowto.com/ru/setup
Обязательно настройте имя пользователя из пункта 1, чтобы по коммитам вас можно было отличить

### Как посмотреть карты по URL `/about`
- зарегистрироваться как разработчик Yandex и получить ключ для доступа к API Yandex Maps
- создать локальную копию файла `application.properties`

### Ключи для настройки карты
- `yandex.maps.version` версия используемого API (по умолчанию `2.1`)
- `yandex.maps.apikey` ваш ключ доступа к API *(обязательный параметр!)*
- `yandex.maps.release` загружаемый релиз (`release|debug`, по умолчанию `release`)
- `yandex.maps.defaults.longitude` начальная долгота (по умолчанию московская `54.314192`)
- `yandex.maps.defaults.latitude` начальная широта (по умолчанию московская `48.403123`)

Если вы хотите, чтобы для новых покупателей показывалась определённая точка на карте,
то установите желаемую долготу и широту (например, координаты главной торговой точки или офиса).

Страница "О нас" сохраняет последний выбранный магазин на карте и значение фильтра городов в куках.


### Универсальный пагинатор

Можно подключить к любой странице, использующей пагинацию. 
Шаблон подключается следующим образом:

`<div th:replace="/common/paginator :: widget (currentPage, totalPages)></div>`

параметры:
- `currentPage` - отображаемая страница (отсчёт ведется от 0)
- `totalPages` - общее количество страниц (если 0, то пагинатор не показывается)

При нажатии кнопки пагинатора происходит вызов страницы с дополнительным параметром

`?pageNumber={№ страницы}`

который можно обработать в вашем контроллере.