/* Самохвалов Юрий Алексеевич */

/*
    Начальное состояние окна при загрузке
    - Подключение обработчиков событий к элементам фильтра
*/
window.onload = function () {
    $('#globalSearch')
        .val(getCookie('productSearchString') || '')
        .change(function () {
            onSearchChange(this.value);
        });
    /* Настройка ограничителя цен */
    let minimalPrice = $('input[name="minimalPrice"]').val();
    let maximalPrice = $('input[name="maximalPrice"]').val();
    let lowerPriceLimit = $('input[name="lowerPriceLimit"]').val();
    let upperPriceLimit = $('input[name="upperPriceLimit"]').val();
    let delta = (maximalPrice - minimalPrice) / 20;
    let start = Math.round(lowerPriceLimit / delta);
    let end = Math.round(upperPriceLimit / delta);
    console.log('Настройки ограничителя цен');
    console.table({ start, end });
    $('#priceLimiter')
        .slider({
            min: 0,
            max: 20,
            start: start,
            end: end,
            step: 1,
            onChange: function (delta, lower) {
                let upper = lower + delta;
                onPriceLimitChange(lower, upper);
            }
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
        showToast('Товар успешно добавлен в корзину');
    }).catch(function () {
        showToast('Ошибка добавления в корзину товара');
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

const onCategoryChange = function (categoryId) {
    reload({
        categoryId: categoryId,
        pageNumber: 0
    });
}

const onDescendingSortChange = function (descendingOrder) {
    reload ({
        descendingOrder : descendingOrder,
    });
}

const onPageChange = function (select) {
    let page = select.selectedIndex;
    reload({ pageNumber: page });
}

const onPageSizeChange = function (select) {
    let pageSize = select.options[select.selectedIndex].value
    reload({
        pageSize: pageSize,
        pageNumber: 0,
     })
}

const onPriceLimitChange = function (lower, upper) {
    let minimalPrice = $('input[name="minimalPrice"]').val()
    let maximalPrice = $('input[name="maximalPrice"]').val()
    let step = (maximalPrice - minimalPrice) / 20;
    let lowerPriceLimit = lower * step;
    let upperPriceLimit = upper * step;
    console.table({ minimalPrice, maximalPrice, step, lowerPriceLimit, upperPriceLimit });

    reload({
        lowerPriceLimit: lowerPriceLimit,
        upperPriceLimit: upperPriceLimit,
        pageNumber: 0,
    });
}

const reload =  function (condition) {
    let filter = Object.assign(generateFilter(), condition);
    let url = '/product?' + $.param(filter);
    console.log(url);
    location.href = url;
}

const generateFilter = function () {
    return {
        categoryId: $('#categoryId').val(),
        descendingOrder: $('#descendingOrder').prop('checked'),
        pageNumber: $('#pageSelector').prop('selectedIndex'),
        pageSize: $('#sizeSelector').val(),
        lowerPriceLimit: $('input[name="lowerPriceLimit"]').val(),
        upperPriceLimit: $('input[name="upperPriceLimit"]').val(),
    };
}

const showToast = function (message) {
    $('body')
        .toast({
            message,
            showProgress: 'bottom'
        });
}