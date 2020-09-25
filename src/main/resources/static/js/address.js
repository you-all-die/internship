/* Самохвалов Юрий Алексеевич */

/* Подключить при загрузке окна обработчик событий поля address */
window.onload = function () {
    $('#address').keyup(function () {
        onAddressChange(this.value)
    });
}

const onAddressChange = function (query) {
    let url = '/address/suggest?' + $.param({ query });
    console.log({ url });
    $.post({
        url
    }).then(function (html) {
        console.log(html);
        $('#suggestions').html(html);
    }).catch(function (error) {
        console.log("Error: " + { error });
    });
}