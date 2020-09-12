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
    let lowerPriceLimit = $('input[name="lowerPriceLimit"]').val();
    let upperPriceLimit = $('input[name="upperPriceLimit"]').val();

    reload({
        lowerPriceLimit: lowerPriceLimit,
        upperPriceLimit: upperPriceLimit,
        pageNumber: 0,
    });
}

const reload =  function (condition) {
    let filter = Object.assign(generateFilter(), condition);
    let url = '/product?' + $.param(filter);
    console.table({ url });
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