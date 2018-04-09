/**
 * Created by Dilwar on 30-06-2016.
 */
$(document).ready(function () {
});
jQuery(document).ready(function ($) {
    $(".page-loading").fadeOut("none");
    var pathName = window.location.pathname; // Returns full URL
    if (pathName.indexOf("weather") >= 0) {
        getWeatherUpdate();
    }
    $("select").each(function () {
        var refId = "0";
        if (this.id != "stateCodeId" && this.id != "cityCodeId") {
            getDropDownList(this.id, refId);
        }
    })
});
$(document).ajaxStart(function () {
    $("#pre-loader").show();
});
$(document).ajaxComplete(function () {
    $("#pre-loader").hide();
});

function getWeatherUpdate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            console.log(latitude);
            console.log(longitude);
            /** *********************************************************************************************** */
            $.get('getWeatherUpdate/' + latitude + "/" + longitude, function (response) {
                if (response.imageUrl != null) {
                    $('.cover-image').attr('src', response.imageUrl);
                }
                $("#cityId").text(
                    response.city + "," + response.state + ","
                    + response.country);
                $.each(response.forecastWeather, function (key, value) {
                    if (key == 0) {
                        $("#highTempId" + key).text(
                            response.conditionTemp + "\xB0"
                            + response.tempUnit.toLowerCase());
                        $("#lowTempId" + key).text(
                            value.low + "\xB0"
                            + response.tempUnit.toLowerCase());
                        $("#forcastDateId" + key).text(response.conditionDate);
                        $("#iconWeather" + key).addClass(
                            "wi wi-yahoo-" + response.conditionCode);
                    } else {
                        $("#highTempId" + key).text(
                            value.high + "\xB0"
                            + response.tempUnit.toLowerCase());
                        $("#forcastDateId" + key).text(value.day);
                        $("#iconWeather" + key).addClass(
                            "wi wi-yahoo-" + value.code);
                    }
                });
                console.log(response);
            })
        });
    } else {
        console.log("Browser not supported geolocation")
    }
}

/*On change of country fetch state list*/
$("#countryCodeId").on("change", function () {
    if ($("#countryCodeId").val() != 'select') {
        $("#stateCodeId").empty();
        getDropDownList("stateCodeId", $("#countryCodeId").val());
        $("#stateCodeId").prepend(
            "<option value=''>Select State</option>").val('');
    } else {
        alert('Select a country.');
    }
});
/*On change of state fetch city list*/
$("#stateCodeId").change(function () {
    if ($("#countryCodeId").val() != 'select' && $("#stateCodeId").val() != 'select') {
        $("#cityCodeId").empty();
        getDropDownList("cityCodeId", $("#stateCodeId").val());
        $("#cityCodeId").prepend(
            "<option value=''>Select city</option>").val('');
    } else {
        alert('Select a state.');
    }
});

function getDropDownList(codeId, refId) {
    $.get('/getDropDownList/' + codeId + '/' + refId, function (response) {
        var codeIdRef = "#".concat(codeId);
        var codeIdName = "Select " + $(codeIdRef).attr('name');
        if (response.ErrorHandler.errorCode == 0) {
            var data = response.data;
            for (key in response.data) {
                $(codeIdRef).append('<option value=' + key + '>' + data[key]
                    + '</option>');
            }
        } else {
            alert(response.ErrorHandler.errorDesc);
        }
    })
}

$("#registerForm").submit(function (e) {
    e.preventDefault();
    $.ajax({
        type: "POST",
        url: "/doRegister",
        data: $('#registerForm').serialize(),
        success: function (response) {
            if (response.errorCode == 0) {
                var seconds = 5;
                setInterval(function () {
                    seconds--;
                    $('#registerMsg').html('<div class="alert alert-success fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong>' + response.errorDesc + ' you are redirecting to login page...' + seconds + ' seconds</div>')
                    if (seconds == 0) {
                        $.get("loginAjax", function (data, textStatus) {
                            window.location.replace(data);
                        });
                    }
                }, 1000);
            } else {
                $('#registerMsg').html('<div class="alert alert-danger fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Error!</strong>' + response.errorDesc + '</div>')
            }
            console.log(response);
        },
        error: function (e) {
            alert('Error: ' + e);
        }
    });
});