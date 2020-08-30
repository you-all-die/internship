/* Самохвалов Юрий Алексеевич */
var map;
var geolocation;

/* Инициализация карты, определение местоположения пользователя */
ymaps.ready(function () {
    let longitude = parseFloat(getCookie('longitudeCookie'));
    let latitude = parseFloat(getCookie('latitudeCookie'));
    map = new ymaps.Map('map', {
        center: [longitude, latitude],
        zoom: 16,
        controls: ['geolocationControl']
    });
    geolocation = ymaps.geolocation;

    /* Добавить метки для магазинов */
    $.ajax({
        url: 'http://localhost:8080/about/coordinates',
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
    }).catch(function (error) {
        console.log(error);
    });

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
});

/* Сместить карту по координатам магазина.
   Записать новые координаты в куки */
var centerOutlet = function (outlet) {
    map.panTo([outlet.longitude, outlet.latitude], {
        flying: true,
        duration: 2000
    });
    setCookie('longitudeCookie', outlet.longitude);
    setCookie('latitudeCookie', outlet.latitude);
}

/* Показать магазины, выбранные в фильтре */
var onCityChange = function (select) {
    var url = select.options[select.selectedIndex].value;
    $.ajax({
        url: url,
        method: 'POST'
    }).then(function (data) {
        /* Сначала все магазине в списке прячем */
        $(".outlet-item").hide();
        /* Теперь показываем магазины, полученные в ответе сервера */
        data.map(outlet => {
            var id = 'outlet' + outlet.id;
            $('#' + id).show();
        });
    }).catch(function (error) {
        console.log(error);
    });
}

var getCookie = function (cookieName) {
    var results = document.cookie.match('(^|;) ?' + cookieName + '=([^;]*)(;|$)');
    if (results) {
        return unescape(results[2]);
    }
    return null;
}

var setCookie = function (name, value) {
    document.cookie = name + '=' + value;
}