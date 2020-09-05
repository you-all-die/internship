/* Самохвалов Юрий Алексеевич */

window.onload = function () {
    console.log('window.onload()');
    let selector = $('#categorySelector').get(0);
    onCategoryChange(selector);
};

const addToCart = function (productId) {
    $.post({ url: '/product/cart?productId=' + unescape(productId) })
        .then(function () {
            console.log('Успешно добавлен в корзину товар id=' + productId);
        })
        .catch(function () {
            console.log('Ошибка добавления в корзину товара id=' + productId);
        });
}

const onCategoryChange = function (select) {
    let categoryId = select.options[select.selectedIndex].value;
    let url = '/product/cards';
    if (categoryId) {
        url = url + '?categoryId=' + categoryId;
    }
    $.ajax({ url: url, method: 'GET' })
        .then(function (html) {
            console.log('GET ' + url);
            console.log(html);
            $('#cards').html(html);
        })
        .catch(function (error) {
            console.log('Ошибка обращения к серверу: ' + error.message)
        });
    //$('#cards').load('/product/cards?categoryId=' + categoryId);
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