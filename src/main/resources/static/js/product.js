/* Самохвалов Юрий Алексеевич */

/*
    Начальное состояние окна при загрузке
    - Подключение обработчик событий к элементам фильтра
*/
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

/* Выбор категории  */
const onCategoryChange = function (categoryId) {
    console.log('>>> onCategoryChange(' + categoryId + ')');
    onFilterChange({
        categoryId: categoryId
    });
}

/* Обработка изменений в диапазоне цен */
const onPriceSliderChange =  function (min, max) {
    console.log('>>> onPriceSliderChange(' + min + ', ' + max + ')');
    onFilterChange({
        minimalPrice: min,
        maximalPrice: max
    });
}

/* Обработка изменений порядка сортировки */
const onSortOrderChange = function (descending) {
    console.log('>>> onSortOrderChange(' + descending + ')');
    onFilterChange({
        descending: descending
    });
}

/* Обработка изменения номера страниц */
const onPageChange = function (pageNumber) {
    console.log('>>> onPageChange(' + pageNumber + ')');
    onFilterChange({
        page: pageNumber
    });
}

/* Обработка изменений фильтра в целом */
const onFilterChange = function (filter) {
    let params = $.param(filter);
    console.log('>>> onFilterChange(' + filter.toString() + ')');
    console.log('>>> params = ' + params);
}