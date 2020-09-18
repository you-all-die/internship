/* Самохвалов Юрий Алексеевич */

/*
    После загрузки страницы
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
    $(modalId).modal({
        blurring: true,
        onApprove: function () { addToCart(productId); }
    }).modal('show');
}

/* При вводе в строке поиска */
const onSearchChange = function(searchString) {
    reload({
        searchString,
        pageNumber: 0,
    });
}

/* При выборе категории */
const onCategoryChange = function (categoryId) {
    reload({
        categoryId: categoryId,
        pageNumber: 0
    });
}

/* При изменении порядка сортировка */
const onDescendingSortChange = function (descendingOrder) {
    reload ({
        descendingOrder : descendingOrder,
    });
}

/* При изменении номера страницы */
const onPageChange = function (select) {
    reload({ pageNumber: select.selectedIndex });
}

/* При изменении размера страницы */
const onPageSizeChange = function (select) {
    let pageSize = select.options[select.selectedIndex].value
    reload({
        pageSize: pageSize,
        pageNumber: 0,
     })
}

/* При изменении границ цен */
const onPriceLimitChange = function () {
    let lowerPriceLimit = $('input[name="lowerPriceLimit"]').val();
    let upperPriceLimit = $('input[name="upperPriceLimit"]').val();

    reload({
        lowerPriceLimit: lowerPriceLimit,
        upperPriceLimit: upperPriceLimit,
        pageNumber: 0,
    });
}

/* Перезагрузка страницы с новыми условиями */
const reload =  function (condition) {
    let filter = Object.assign(generateFilter(), condition);
    let url = '/product?' + $.param(filter);
    location.href = url;
}

/* Собрать общие параметры запроса из полей */
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

/* Показать тост с сообщением */
const showToast = function (message) {
    $('body')
        .toast({
            message,
            showProgress: 'bottom'
        });
}