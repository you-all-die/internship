/* Самохвалов Юрий Алексеевич */

/* При загрузке окна браузера имитируем событие селектора */
window.onload = function () {
    let selector = $('#categorySelector').get(0);
    onCategoryChange();
};

/* Добавить товар в корзину */
const addToCart = function (productId) {
    let url = '/product/cart?productId=' + unescape(productId);
    console.log('POST ' + url);
    $.ajax({
        url: url,
        method: 'POST'
    }).then(function () {
        console.log('Успешно добавлен в корзину товар id=' + productId);
    }).catch(function () {
        console.log('Ошибка добавления в корзину товара id=' + productId);
    });
}

/* При выборе из дерева категорий  */
const onCategoryChange = function (categoryId) {
    let url = '/product/cards';
    if (categoryId) {
        url = url + '?categoryId=' + categoryId;
    }
    console.log('GET ' + url)
    $.ajax({
        url: url,
        method: 'GET'
    }).then(function (html) {
        $('#cards').html(html);
    }).catch(function (error) {
        console.log('Ошибка обращения к серверу: ' + error.message)
    });
}

/* Диалог подтверждения добавления товара */
const confirm = function (modalId, productId) {
    console.log('confirm(' + modalId + ', ' + productId + ')');
    $(modalId).modal({
        blurring: true,
        onApprove: function () { addToCart(productId); }
    }).modal('show');
}