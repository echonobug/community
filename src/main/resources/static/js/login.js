$(function () {
    draw_bg();
    $("#vcodeimg").click(function () {
        updateVcode()
    });
    $("#login-btn").click(function () {
        login();
    });
    $('input').each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

})

function login() {
    var input = $('.input');
    if (verify_all(input)) {
        $.post("/login",
            {
                "username": $(input[0]).val().trim(),
                "password": $(input[1]).val().trim(),
                "vcode": $(input[2]).val().trim(),
                "remember-me": $("#remember-me").is(":checked")
            },
            function (res) {
                if (res.code == 1000) {
                    location.href = res.data
                }else {
                    alert(res.data)
                    updateVcode()
                }
            }
        )
    }
}

function verify_all(input) {
    var check = true;
    for (var i = 0; i < input.length; i++) {
        if (validate(input[i]) == false) {
            showValidate(input[i]);
            check = false;
        }
    }
    return check;
}

function validate(input) {
    if ($(input).val().trim() == '') {
        return false;
    }
}

function showValidate(input) {
    var thisAlert = $(input).parent();
    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();
    $(thisAlert).removeClass('alert-validate');
}

function draw_bg() {
    var stars = 800;  /*星星的密集程度，数字越大越多*/
    var $stars = $(".stars");
    var r = 800;   /*星星的看起来的距离,值越大越远,可自行调制到自己满意的样子*/
    for (var i = 0; i < stars; i++) {
        var $star = $("<div/>").addClass("star");
        $stars.append($star);
    }
    $(".star").each(function () {
        var cur = $(this);
        var s = 0.2 + (Math.random() * 1);
        var curR = r + (Math.random() * 300);
        cur.css({
            transformOrigin: "0 0 " + curR + "px",
            transform: " translate3d(0,0,-" + curR + "px) rotateY(" + (Math.random() * 360) + "deg) rotateX(" + (Math.random() * -50) + "deg) scale(" + s + "," + s + ")"
        })
    })
}

function updateVcode() {
    var timestamp = (new Date()).valueOf();
    $("#vcodeimg").attr("src", "vcode?t=" + timestamp);
}

function developing() {
    alert("该功能正在开发中！试试其他的吧٩(๑❛ᴗ❛๑)۶")
}