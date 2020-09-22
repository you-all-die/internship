/* Самохвалов Юрий Алексеевич */

/* Подключить при загрузке окна обработчик событий поля address */
window.onload = function () {
    $('#address').suggestions({
        token: $('#token').val(),
        type: 'ADDRESS',
        onSelect: function (suggestion, changed) {
            console.log("Dadata onSelect:");
            console.table({ suggestion });
        },
        onSelectNothing: function (query) {
            console.log("Dadata onSelectNothing:");
            console.table({ query });
        }
    });
}