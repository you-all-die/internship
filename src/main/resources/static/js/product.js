/* Самохвалов Юрий Алексеевич */

/* При загрузке окна браузера имитируем событие селектора */
window.onload = function () {
    let selector = $('#categorySelector').get(0);
    $('#priceSlider').slider({
        min: 0,
        max: 20,
        step: 1,
        onChange: function (delta, min) {
            onPriceSliderChange(min, min + delta);
        }
    });
    $('#sorting').change(function () {
        onSortOrderChange(this.checked);
    });
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


/* Диалог подтверждения добавления товара */
const confirm = function (modalId, productId) {
    console.log('confirm(' + modalId + ', ' + productId + ')');
    $(modalId).modal({
        blurring: true,
        onApprove: function () { addToCart(productId); }
    }).modal('show');
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

/* Обработка изменений в диапазоне цен */
const onPriceSliderChange =  function (min, max) {
    console.log('>>> onPriceSliderChange(' + min + ', ' + max + ')');
}

/* Обработка изменений порядка сортировки */
const onSortOrderChange = function (descending) {
    console.log('>>> onSortOrderChange(' + descending + ')');
}

/* Обработка изменения номера страниц */
const onPageChange = function (pageNumber) {
    console.log('>>> onPageChange(' + pageNumber + ')');
}