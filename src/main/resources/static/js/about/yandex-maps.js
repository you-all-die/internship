/* Самохвалов Юрий Алексеевич */
var map;
var geolocation;

/* Инициализация карты, определение местоположения пользователя */
ymaps.ready(function () {
    map = new ymaps.Map('map', {
        center: [54.314192, 48.403123],
        zoom: 16,
        controls: ['geolocationControl']
    });
    geolocation = ymaps.geolocation;

    /* Добавить метки для магазинов */
    $.ajax({
        url: 'http://localhost:8080/about',
        method: 'POST'
    }).then(function (outlets) {
        console.log('outlets = ' + outlets);
        outlets.map(outlet => {
            let longitude = outlet.longitude;
            let latitude = outlet.latitude;
            console.log('Магазин ' + outlet.name + ' в ' + outlet.city + ' [' + outlet.longitude + ',' + outlet.latitude + ']')
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
        provider: 'yandex',
        mapStateAutoApply: true
    }).then(function (result) {
        result.geoObjects.options.set('preset', 'islands#redCircleIcon');
        result.geoObjects.get(0).properties.set({
            balloonContentBody: 'Вы здесь!'
        });
        map.geoObjects.add(result.geoObjects);
    });

    /*  Положение пользователя по данным браузера.
        Если браузер не поддерживает эту функцию, синяя метка не появится. */
    geolocation.get({
        provider: 'browser',
        mapStateAutoApply: true
    }).then(function (result) {
        result.geoObjects.options.set('preset', 'islands#blueCircleIcon');
        map.geoObjects.add(result.geoObjects);
    });
});

/* Сместить карту по координатам магазина */
var centerOutlet = function (outlet) {
    console.log('Переход на магазин ' + outlet.name);
    map.panTo([outlet.longitude, outlet.latitude], {duration: 2000});
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
