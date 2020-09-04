const addToCart = function (productId) {
    $.post({ url: '/product/cart?productId=' + productId })
        .done(function () {
            console.log('Успешно добавлен в корзину товар id=' + productId);
        })
        .fail(function () {
            console.log('Ошибка добавления в корзину товара id=' + productId);
        })
        .always(function () {});
}