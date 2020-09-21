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