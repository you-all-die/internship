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

const onCategoryChange = function (categoryId) {
    reload({ categoryId: categoryId });
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
    };
}