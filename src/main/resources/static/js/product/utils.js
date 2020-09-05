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
    let category = select.options[select.selectedIndex].value;
    $.post({ url: '' })
        .done(function (products) {
            console.log('Сервер вернул: ' + products);
        })
        .fail(function (error) {
            console.log('Ошибка обращения к серверу: ' + error);
        })
}