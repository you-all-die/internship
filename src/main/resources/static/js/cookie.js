/*
    Самохвалов Юрий Алексеевич
    Вспомогательные функции для работы с куками
*/

/* Получить значение куки */
const getCookie = function (name) {
    var results = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    if (results) {
        return unescape(results[2]);
    }
    return null;
}

/* Сохранить куку */
const setCookie = function (name, value) {
    document.cookie = name + '=' + value;
}

/* Удалить куку */
const deleteCookie = function (name) {
    let expirationDate = new Date();
    expirationDate.setTime(expirationDate.getTime() - 1);
    document.cookie = name += '=; expires=' + expirationDate.toGMTString();
}
