window.onload = function () {
    selectDelivery();
}

const selectDelivery = function () {
    $('#delivery').addClass('active');
    $('#pickup').removeClass('active');
    $('#deliveryTab').show();
    $('#pickupTab').hide();
}

const selectPickup = function () {
    $('#delivery').removeClass('active');
    $('#pickup').addClass('active');
    $('#deliveryTab').hide();
    $('#pickupTab').show();
}