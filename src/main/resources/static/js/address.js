/* Самохвалов Юрий Алексеевич */

/* Подключить при загрузке окна обработчик событий поля address */
window.onload = function () {
    $("#comment").suggestions({
            token: "6eae6552e64fd96bedfdd8903608c0deac102b4a",
            type: "ADDRESS",
            /* Вызывается, когда пользователь выбирает одну из подсказок */
            onSelect: function(suggestion) {
                console.log(suggestion);
            }
        });
}