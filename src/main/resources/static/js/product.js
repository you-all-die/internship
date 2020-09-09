/* Самохвалов Юрий Алексеевич */

/*
    Начальное состояние окна при загрузке
    - Подключение обработчик событий к элементам фильтра
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
    onFilterChange(searchString ? { nameLike: searchString } : {});
}

/* Выбор категории  */
const onCategoryChange = function (categoryId) {
    onFilterChange(categoryId ? { categoryId: categoryId } : {});
}

/* Обработка изменений в диапазоне цен */
const onPriceSliderChange =  function (min, max) {
    onFilterChange({
        minimalPrice: min,
        maximalPrice: max
    });
}

/* Обработка изменений порядка сортировки */
const onSortOrderChange = function (descending) {
    onFilterChange({ descending: descending });
}

/* Обработка изменения номера страниц */
const onPageChange = function (pageNumber) {
    onFilterChange({ pageNumber: pageNumber });
}

/* Обработка изменений фильтра в целом */
const onFilterChange = function (filter) {
    let cookieFilter = filterFromCookies();
    let joinedFilter = Object.assign(cookieFilter, filter);
    let params = $.param(joinedFilter);
    let url = '/product/filter' + (params ? '?' + params : '');
    console.log('>>> onFilterChange URL: ' + url)
    $.ajax({
        url: url,
        method: 'GET'
    }).done(function (html) {
        $('#cards').html(html);
    }).fail(function (error) {
        console.log({ error });
    });
}

/* Формирует заготовку фильтра из кук */
const filterFromCookies = function () {
    let filter = {};
    let searchString = getCookie('productSearchString');
    if (searchString) {
        filter.searchString = searchString;
    }
    let categoryId = parseInt(getCookie('productCategoryId'));
    if (categoryId) {
        filter.categoryId = categoryId;
    }
    let minimalPrice = parseFloat(getCookie('productMinimalPrice'));
    if (minimalPrice) {
        filter.minimalPrice = minimalPrice;
    }
    let maximalPrice = parseFloat(getCookie('productMaximalPrice'));
    if (maximalPrice) {
        filter.maximalPrice = maximalPrice;
    }
    let pageNumber = parseInt(getCookie('productPageNumber'));
    if (pageNumber) {
        filter.pageNumber = pageNumber - 1; /* Страницы отсчитываются с нуля */
    }
    let pageSize = parseInt(getCookie('productPageSize'));
    if (pageSize) {
        filter.pageSize = pageSize;
    }
    let descending = getCookie('productDescendingOrder');
    if (descending) {
        filter.descending = descending == 'true';
    }

    console.log('>>> filterFromCookies() returns:');
    console.table({ filter });

    return filter;
}