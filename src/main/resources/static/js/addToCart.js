/* add to cart*/


function testOne(id) {
    let url = '/product/cart?productId=' + unescape(id);
    console.log('POST ' + url)
    $.ajax({
        url: url,
        method: 'POST'
    }).then(function () {
        showToast('Товар успешно добавлен в корзину')
    }).catch(function () {
        showToast('Error')
    })
}

function showToast(message) {
    $('body')
        .toast({
            message,
            showProgress: 'bottom'
        })
}