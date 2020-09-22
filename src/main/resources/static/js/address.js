/* Самохвалов Юрий Алексеевич */

/* Подключить при загрузке окна обработчик событий поля address */
window.onload = function () {
    $('#address').change(function () {
        onAddressChange(this.value);
    });
}

const onAddressChange = function(value) {
    let url = '/dadata/address?' + $.param({ query : value });
    $.get({
        url
    }).then(function (suggestions) {
        $('#address').suggestions()
    }).catch(function (error) {});
}