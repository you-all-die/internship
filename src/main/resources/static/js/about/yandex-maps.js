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

function setPlacemark (outlet) {
    var outletPlacemark = new ymaps.Placemark([outlet.longitude, outlet.latitude]);
    map.getObjects.add(outletPlacemark);
}

function addPlacemarks(outlets) {
    console.log('Вызвана функция addPlacemarks с аргументом ' + outlets);
}

function centerOutlet (outlet) {
    map.panTo([outlet.longitude, outlet.latitude], {duration: 2000});
}

function onCityChange (select) {
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
    });
}
