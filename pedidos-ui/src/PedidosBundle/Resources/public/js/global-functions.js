function executeModalAjax(url, type, modalElement, submitBtn, successElement, errorElement, callback) {
    $.ajax({
        type: type,
        url: url,
        beforeSend: function() {
            submitBtn.button('loading');
        }
    }).then(function (data, textStatus, jqXHR) {
        successElement.slideDown();
        setTimeout(function() {
            modalElement.modal('hide');
            if (callback !== undefined) {
                var x = eval(callback);
                if (typeof x === 'function') {
                    x()
                }
            }
        }, 1000);
    }).fail(function (data, textStatus, jqXHR) {
        errorElement.html(data.responseText);
        errorElement.slideDown();
        submitBtn.button('reset');
    }).done(function () {
        submitBtn.button('reset');
    });
}

function saveBtnComplete(buttonEl, text) {
    buttonEl.html(text);
}
