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
    $("#avatarUrl").focusout(function () {
        $("#avatarImg").attr("src",$("#avatarUrl").val())
    })

    $("#register_form").submit(function () {
        if (validate_username() && validate_password() && validate_password_equal()) {
            $.post("/doRegister",
                $("#register_form").serialize(),
                function (res) {
                    if (res.code == 0) {
                        location.href = "/registerSuccess?id=" + res.data
                    }else {
                        alert("注册失败！")
                    }
                })
        } else {
            alert("表单填写不完整，请检查表单！")
        }
        return false;
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