/* ***********************************************
* JS document for all non-vue JS codes
*********************************************** */

// home map tab functions
$(document).on("click", ".btn-map-selection", function(){
    $(".btn-map-selection").removeClass("active")
    $(this).addClass("active");
    const showContainer = "#map-selec-" + $(this).attr("id");
    $(".home-map-container").removeClass("active");
    if (showContainer == "#map-selec-accuracy") {
        $("#map-selec-migration").hide("slide", { direction: "left"  }, 400);
        $(showContainer).show("slide", { direction: "right"  }, 400);
    } else {
        $("#map-selec-accuracy").hide("slide", { direction: "left"  }, 400);
        $(showContainer).show("slide", { direction: "right"  }, 400);
    }
})

// mobile expand button function
$(document).on("click", ".btn_mobile_expand", function(){
    const mobileContainer = $(this).parent().parent().find(".mobile-content");
    if ($(mobileContainer).is(":visible")) {
        $(this).find("i").removeClass("fa-chevron-up");
        $(this).find("i").addClass("fa-chevron-down");
        $(mobileContainer).slideUp();
    } else {
        $(mobileContainer).slideDown();
        $(this).find("i").removeClass("fa-chevron-down");
        $(this).find("i").addClass("fa-chevron-up");
    }
}) 

// mobile menu button
$(document).on("click", "#btn-mobile-menu", function() {
    $(".mobile-menu-list").addClass("active");
});
$(document).on("click", "#btn-mobile-menu-close", function() {
    $(".mobile-menu-list").removeClass("active");
});

// text input multiple value focus function ---------------------------------
$(document).on("focus", ".input-text-multi input", function () {
    $(".input-text-multi").addClass("active");
});
$(document).on("blur", ".input-text-multi input", function () {
    $(".input-text-multi").removeClass("active");
});

// keyup function for multi-value text input
$(document).on("keyup", "#share-emails-temp", delay(function(e){
// KeyCode For comma is 188, enter is 13, semicolon is 186
if (e.keyCode == 188 || e.keyCode == 186 || e.keyCode == 13) { 
    let value = $(this).val();
    if(value) {
        value = value.trim().replace(" ", "").replace(",", "").replace(";", "");
        if(value != "" && value != "," && value != ";") {
            if(validateEmail(value)) {
                // append item
                doAppendMultiValue(value, "#input-text-multi-display", "#share-emails");
                // reset current
                $(this).val("");
            } else {
                alert("Please enter a valid address");
                $(this).val("");
            }
        }
    }
}
}, 100));

// remove button to remove item from multi-value input
$(document).on("click", ".btn-remove-multi-item", function() {
    const value = $(this).parent().text();
    $(this).parent().remove();
    doUpdateMultiValueInput(value, "#share-emails");
});

function doAppendMultiValue(value, container, input) {
    if(value != null && value !== null) {
        if(checkExistingMultiValueInput(value, "#share-emails")) {
            // do nothing, already exists
        } else {
            $(container).append(buildMultiValueData(value));
            if($(input).val() == "") {
            $(input).val(value.toLowerCase());
            } else {
            $(input).val($(input).val() + "," + value.toLowerCase());
            }
        }
    }
}

// function to check existing input
function checkExistingMultiValueInput(value, input) {
    let found = false;
    const arr = $(input).val().split(",");
    for(let i = 0; i < arr.length; i++) {
        if (arr[i].toLowerCase() === value.toLowerCase()) { 
            found = true;
            break;
        } 
    }
    return found;
}

//function to update input value
function doUpdateMultiValueInput(value, input) {
    let oldVal = $(input).val();
    let temp = "";
    let arr = oldVal.split(",");
    for(let i = 0; i < arr.length; i++) {
        if (arr[i].toLowerCase() === value.toLowerCase()) { 
            arr.splice(i, 1);
        }
    }
    for(let i = 0; i < arr.length; i++) {
        if(i < arr.length - 1) {
            temp += arr[i] + ",";
        } else {
            temp += arr[i];
        }
    }
    $(input).val(temp);
}

// function to build multi value item in HTML
function buildMultiValueData(value) {
    let html = "<span class='input-multi-item'>" + value + "<i class='fas fa-times btn-remove-multi-item'></i></span>";
    return html;
}

// end of - text input multiple value focus function ---------------------------------

//function for function delay
function delay(callback, ms) {
    var timer = 0;
    return function() {
    var context = this, args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
        callback.apply(context, args);
        }, ms || 0);
    };
}

//function to validate email
function validateEmail(mail) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
    return true;
    } else {
    return false;
    }
}

/* ==================================
NEW UI Functions
=================================== */