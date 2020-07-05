$(function () {
    $("#register_form").submit(function () {
        if (validate_username() && validate_password() && validate_password_equal()) {
            $.post("/doRegister",
                $("#register_form").serialize(),
                function (res) {
                    if (res.code == 0) {
                        location.href = "/registerSuccess?id=" + res.data
                    } else {
                        alert("注册失败！")
                    }
                })
        } else {
            alert("表单填写不完整，请检查表单！")
        }
        return false;
    })

})
