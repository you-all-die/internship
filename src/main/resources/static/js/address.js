/* Самохвалов Юрий Алексеевич */

/* Подключить при загрузке окна обработчик событий поля address */
window.onload = function () {
    $('#address').suggestions({
        token: $('#token').val(),
        type: 'ADDRESS',
    });
}