/* Самохвалов Юрий Алексеевич */

/* При загрузке окна браузера имитируем событие селектора */
window.onload = function () {
    let selector = $('#categorySelector').get(0);
    onCategoryChange(selector);
};

/* Добавить товар в корзину */
const addToCart = function (productId) {
    $.post({ url: '/product/cart?productId=' + unescape(productId) })
        .then(function () {
            console.log('Успешно добавлен в корзину товар id=' + productId);
        })
        .catch(function () {
            console.log('Ошибка добавления в корзину товара id=' + productId);
        });
}

/* При выборе из селектора категорий  */
const onCategoryChange = function (select) {
    let categoryId = select.options[select.selectedIndex].value;
    let url = '/product/cards';
    if (categoryId) {
        url = url + '?categoryId=' + categoryId;
    }
    $.ajax({ url: url, method: 'GET' })
        .then(function (html) {
            $('#cards').html(html);
        })
        .catch(function (error) {
            console.log('Ошибка обращения к серверу: ' + error.message)
        });
}

/* Диалог подтверждения добавления товара */
const confirm = function (modalId, productId) {
    console.log('confirm(' + modalId + ', ' + productId + ')');
    $(modalId)
        .modal({
            blurring: true,
            onApprove: function () { addToCart(productId); }
        })
        .modal('show');
}