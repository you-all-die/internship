var map;

ymaps.ready(function () {
    map = new ymaps.Map('map', {
        center: [54.314192, 48.403123],
        zoom: 16
    });
});

ymaps.setPlacemark = function (outlet) {
    var outletPlacemark = new ymaps.Placemark([outlet.longitude, outlet.latitude]);
    map.getObjects.add(outletPlacemark);
}

ymaps.showOutlet = function(outlet) {
    map.panTo([outlet.longitude, outlet.latitude], {duration: 2000});
}

function onCityChange (select) {
    var url = select.options[select.selectedIndex].value;
    console.log('Вызов по URL ' + url);
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

