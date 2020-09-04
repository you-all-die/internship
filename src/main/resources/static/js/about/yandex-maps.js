/* Самохвалов Юрий Алексеевич */

var map;
var geolocation;

/*
Первоначальная загрузка:
- инициализация карты с установкой последней позиции;
- фильтрация списка магазинов по последнему выбранному городу;
- нанесение меток магазинов на карту;
- определение местоположения пользователя по IP.
*/
ymaps.ready(function () {

    let longitude = parseFloat(getCookie('longitudeCookie'));
    let latitude = parseFloat(getCookie('latitudeCookie'));

    map = new ymaps.Map('map', {
        center: [longitude, latitude],
        zoom: 16,
        controls: ['geolocationControl']
    });
    geolocation = ymaps.geolocation;

    /* Имитировать событие onChange селектора городов для принудительной фильтрации */
    onCityChange($('#citySelector').get(0));

    /* Положение пользователя по IP */
    geolocation.get({
        provider: 'yandex'
    }).then(function (result) {
        result.geoObjects.options.set('preset', 'islands#redCircleIcon');
        result.geoObjects.get(0).properties.set({
            balloonContentBody: 'Вы здесь!'
        });
        map.geoObjects.add(result.geoObjects);
    });

    /* Добавить метки для магазинов */
    $.ajax({
        url: '/rest/about/coordinates',
        method: 'POST'
    }).then(function (outlets) {
        outlets.map(outlet => {
            let longitude = outlet.longitude;
            let latitude = outlet.latitude;
            let placemark = new ymaps.Placemark([longitude, latitude], {}, {
                preset: 'islands#redDotIcon'
            });
            map.geoObjects.add(placemark);
        });
        $('.dimmer').removeClass('active');
    }).catch(function (error) {
        console.log(error);
    });
});

/* Показать магазины, выбранные в фильтре */
var onCityChange = function (select) {
    let city = select.options[select.selectedIndex].value;
    $.ajax({
        url: '/rest/about?city=' + unescape(city),
        method: 'POST'
    }).then(function (data) {
        /* Сначала все магазине в списке прячем */
        $(".outlet-item").hide();
        /* Теперь показываем магазины, полученные в ответе сервера */
        data.map(outlet => {
            var id = 'outlet' + outlet.id;
            $('#' + id).show();
        });
        /* Сохранить город в куки фильтра */
        setCookie('cityFilterCookie', city);
        $('#outletList').show();
    }).catch(function (error) {
        console.log(error);
    });
}

/*
Сместить карту по координатам.
Записать новые координаты в куки.
*/
var centerMap = function (longitude, latitude) {
    map.panTo([longitude, latitude], { flying: true, duration: 3000 });
    setCookie('longitudeCookie', longitude);
    setCookie('latitudeCookie', latitude);
}

/* Получить значение куки */
var getCookie = function (name) {
    var results = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    if (results) {
        return unescape(results[2]);
    }
    return null;
}

/* Сохранить куку */
var setCookie = function (name, value) {
    document.cookie = name + '=' + value;
}