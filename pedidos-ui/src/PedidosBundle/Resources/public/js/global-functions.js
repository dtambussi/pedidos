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

/**
 *
 * @param url The url at which form will be submitted
 * @param formId The form id
 * @param targetDivId The div ID on which response will be rendered.
 * @returns {*}
 * @param onSuccessCallback Function to be called on post success
 */
function submitForm2(url, formId, targetDivId, onSuccessCallback){
    return submitData2(url, new FormData($("#" + formId).get(0)), "#" + targetDivId, onSuccessCallback);
}

function submitData2(url, data, targetDivId, onSuccessCallback){
    $('.disableable').attr("disabled", true);
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        processData: false,
        contentType: false,
        dataType: "html"
    }).then(function (view) {
        $(targetDivId).html(view);
        // $('.ip-loading').addClass('hide');
        onSuccessCallback();
        return true;
    }).fail(function(jqXHR, textStatus, errorThrown) {
        $(targetDivId).html(jqXHR.responseText);
        // $('.ip-loading').addClass('hide');
        return false;
    }).always(function() {

        // Every element with disableable class will be disabled
        $('.disableable').attr("disabled", false);
    });
}

/**
 *
 * @param url The url at which get content to be loaded
 * @param $elementTarget This must be the element an not only his id!!.
 * E.g: loadHTMLContentOn("...", $("#my-div"));
 * @param onSuccessCallback Optional. Function to call in case of success.
 */
function loadHTMLContentOn(url, $elementTarget, onSuccessCallback) {
    // $('.ip-loading').removeClass('hide');
    $.ajax({
        type: "GET",
        url: url,
        dataType: "html"
    }).then(function (data, textStatus, jqXHR) {
        $elementTarget.html(data);
        return true;
    }).always(function () {
        // $('.ip-loading').addClass('hide');
        if (onSuccessCallback !== undefined) {
            onSuccessCallback();
        }
    });
}
