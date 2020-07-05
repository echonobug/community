$(function () {
    $("#username").focusout(function () {
        validate_username()
    })
    $("#password").focusout(function () {
        validate_password()
    })
    $("#validate_password").focusout(function () {
        validate_password_equal()
    })
    $("#avatarUrl").focusin(function () {
        $("#avatarImg").attr("src", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2085178611,3470768407&fm=26&gp=0.jpg")
    })
    $("#avatarUrl").focusout(function () {
        $("#avatarImg").attr("src", $("#avatarUrl").val())
    })
})

function validate_password_equal() {
    let input = $("#validate_password")
    if (input.val() != $("#password").val() || input.val().length == 0) {
        invalid(input)
        return false
    } else {
        valid(input)
        return true
    }
}

function validate_password() {
    let input = $("#password")
    if (input.val().length < 6) {
        invalid(input)
        return false
    } else {
        valid(input)
        return true
    }
}

function validate_username() {
    let input = $("#username")
    if (input.val().length == 0) {
        invalid(input)
        return false
    } else {
        valid(input)
        return true
    }
}

function valid(input) {
    input.removeClass("is-invalid")
    input.addClass("is-valid")
}

function invalid(input) {
    input.removeClass("is-valid")
    input.addClass("is-invalid")
}