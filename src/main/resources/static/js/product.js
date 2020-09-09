/* Самохвалов Юрий Алексеевич */

/*
    Начальное состояние окна при загрузке
    - Подключение обработчиков событий к элементам фильтра
*/
window.onload = function () {
    let selector = $('#categorySelector').get(0);
    $('#globalSearch').change(function () {
        onSearchChange(this.value);
    });
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

    onFilterChange({});
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

/* Обработка изменений в строке поиска */
const onSearchChange = function (searchString) {
    setCookie('productSearchString', searchString || '');
    reloadCards();
}

/* Выбор категории  */
const onCategoryChange = function (categoryId) {
    let representation = parseInt(categoryId);
    if (Number.isInteger(representation)) {
        setCookie('productCategoryId', representation);
    } else {
        deleteCookie('productCategoryId');
    }
    reloadCards();
}

/* Обработка изменений в диапазоне цен */
const onPriceSliderChange =  function (min, max) {
    let minRepresentation = parseFloat(min);
    let maxRepresentation = parseFloat(max);
    if (Number.isFloat(minRepresentation) && Number.isFloat(maxRepresentation)) {
        setCookie('productMinimalPrice', minRepresentation);
        setCookie('productMaximalPrice', maxRepresentation);
    } else {
        deleteCookie('productMinimalPrice');
        deleteCookie('productMaximalPrice');
    }
    reloadCards();
}

/* Обработка изменений порядка сортировки */
const onSortOrderChange = function (descending) {
    switch (descending) {
        case 'true':
            setCookie('productDescendingOrder', descending);
            break;
        case 'false':
            setCookie('productDescendingOrder', descending);
            break;
        default:
            deleteCookie('productDescendingOrder');
    }
    reloadCards();
}

/* Обработка изменения номера страниц */
const onPageChange = function (pageNumber) {
    let representation = parseInt(pageNumber);
    if (Number.isInteger(representation)) {
        setCookie('productPageNumber', representation);
    } else {
        deleteCookie('productPageNumber');
    }
    reloadCards();
}

const reloadCards = function () {
    $.get({ url: '/product/filter'})
        .done(function (html) {
            $('#cards').html(html);
        })
        .fail(function (error) {
            console.log({ error });
        });
}
