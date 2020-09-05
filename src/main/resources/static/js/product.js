/* Самохвалов Юрий Алексеевич */

const addToCart = function (productId) {
    $.post({ url: '/product/cart?productId=' + unescape(productId) })
        .done(function () {
            console.log('Успешно добавлен в корзину товар id=' + productId);
        })
        .fail(function () {
            console.log('Ошибка добавления в корзину товара id=' + productId);
        })
        .always(function () {});
}

const onCategoryChange = function (select) {
    let categoryId = select.options[select.selectedIndex].value;
    $.post({ url: '/product/filter?categoryId=' + categoryId })
        .done(function (products) {
            console.log('Сервер вернул: ' + products);
        })
        .fail(function (error) {
            console.log('Ошибка обращения к серверу: ' + error);
        })
}

const confirmAddToCart = function (productId) {
    $('#modal' + productId)
        .modal({
            blurring: true,
            onApprove: function () {
                addToCart(productId);
            }
        })
        .show('modal');
}