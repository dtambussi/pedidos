/**
 * Created by sixsides on 27/7/17.
 */

/**
 * Initialize for each element with the "input-x" class the corresponding element.
 *
 * Includes:
 * 1) "input-bv-datepicker": calendar with datepicker element
 * 2) "input-bv-font": only adds font glyphicon > for text elements
 * 3) "input-bv-lock": only adds lock glyphicon >  for password elements
 * 4) "input-bv-phone": only adds phone glyphicon
 * 5) "input-bv-usd": adds usd glyphicon and currency validation/format
 * 6) "input-bv-number": adds number glyphicon and number validation/format
 * 7) "input-bv-picture": only adds picture glyphicon >  for picture elements
 * 8) "input-bv-envelope": only adds envelope glyphicon >  for mail elements
 */
function createFormFields(){
    createDatepickerFormField();
    createFormField('font');
    createFormField('lock');
    createFormField('phone');
    createFormField('usd', true, false, true);
    createFormField('number', false, true);
    createFormField('picture');
    createFormField('envelope');
}

/**
 * Initialize for each element with the "input-bv-datepicker" class the corresponding element.
 */
function createDatepickerFormField(){

    var input = $(".input-bv-datepicker");
    if(input){
        // Adds a surrounding div for appending the glyphicon calendar object
        input.wrap("<div class='input-group surround-bv-datepicker'></div>");

        // Appends the glyphicon calendar
        $(".surround-bv-datepicker").append('<div class="input-group-addon glyphicon glyphicon-calendar"></div>');

        input.datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy',
        });
    }

    var input18 = $(".input-bv-18datepicker");
    if(input18){
        // Adds a surrounding div for appending the glyphicon calendar object
        input18.wrap("<div class='input-group surround-bv-18datepicker'></div>");

        // Appends the glyphicon calendar
        $(".surround-bv-18datepicker").append('<div class="input-group-addon glyphicon glyphicon-calendar"></div>');

        input18.datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy',
            viewMode: "years",
            endDate: '-18y'
        });
    }
}

/**
 * Initialize for each element with the "input-bv-"+field class the corresponding element.
 * @param field the field class to search for
 * @param prepend boolean in order to define if the image should be prepended instead of appended
 * @param isNumber boolean in order to add the numeric validation and automatic edition on error
 * @param isCurrency boolean in order to add the numeric validation and automatic format edition on focusout
 */
function createFormField(field, prepend, isNumber, isCurrency){

    var input = $(".input-bv-"+field);
    if(input){
        // Adds a surrounding div for appending the glyphicon calendar object
        input.wrap('<div class="input-group surround-bv-'+field+'"></div>');

        if(prepend){
            // Prepends the glyphicon icon
            $(".surround-bv-"+field).prepend('<div class="input-group-addon glyphicon glyphicon-'+field+'"></div>');
        }else{
            if(isNumber){
                // Appends the % icon without glyphicon
                $(".surround-bv-"+field).append('<div class="input-group-addon">#</div>');
            }else{
                // Appends the glyphicon icon
                $(".surround-bv-"+field).append('<div class="input-group-addon glyphicon glyphicon-'+field+'"></div>');
            }
        }

        if(isNumber) {
            //Number formatter
            input.numeric({decimalPlaces: 0});
            input.on('keyup', function () {
                $(this).val(numeral($(this).val()).format('0,0'));
            });
        }

        if(isCurrency){
            //Currency formatter
            input.numeric({ decimalPlaces : 2 });
            input.focusout(function () {
                $(this).val(numeral($(this).val()).format('0,0'));
            });
        }
    }
}
