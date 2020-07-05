$(function () {
    $("#oldPassword").focusout(function () {
        validate_null()
    })
    $("#edit-profile").submit(function () {
        if (validate_username()) {
            $.post("/editProfile",
                $("#edit-profile").serialize(),
                function (res) {
                    alert(res.data)
                })
        } else {
            alert("表单填写不完整，请检查表单！")
        }
        return false;
    })
    $("#set-password").submit(function () {
        if (validate_null() &&
            validate_password() && validate_password_equal()
        ) {
            $.post("/setPassword",
                $("#set-password").serialize(),
                function (res) {
                    alert(res.data)
                })
        } else {
            alert("表单填写不完整，请检查表单！")
        }
        return false;
    })
})

function validate_null() {
    let input = $("#oldPassword")
    if (input.val().length == 0) {
        invalid(input)
        return false
    } else {
        valid(input)
        return true
    }
}