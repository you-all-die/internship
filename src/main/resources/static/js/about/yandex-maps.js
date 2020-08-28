var map;

ymaps.ready(function () {
    map = new ymaps.Map('map', {
        center: [54.314192, 48.403123],
        zoom: 10
    });
});

ymaps.markAndGo = function(outlet) {
    var outletMark = new ymaps.Placemark([outlet.longitude, outlet.latitude]);
    map.panTo([outlet.longitude, outlet.latitude], {duration: 2000});
}