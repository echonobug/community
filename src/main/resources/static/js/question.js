$(function () {
    $("#btn-comment").click(function () {
        var comment = $("#txt-comment").val();
        if (comment.length == 0) {
            $("#div-alert").html("评论内容不能为空！！！").addClass("p-2")
        } else {
            postComment(1, $("#parentId").val(), comment, "#div-comment-list","/comment/comment")
            $("#txt-comment").val("")
            this.blur()
        }
    });
    $("#btn-comment").focusout(function () {
        $("#div-alert").html("").removeClass("p-2")
    });
})

function postComment(type, parentId, comment, divId, divContentUrl) {
    var sendData = {
        "type": type,
        "parentId": parentId,
        "content": comment
    };
    $.ajax({
        url: "/comment",
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(sendData),
        success: function () {
            $(divId).load(divContentUrl, {"id": parentId});
        }
    })
}

function like(btn, id) {
    $.post("/like", {"id": id}, function (data) {
        $(btn).children("span").text(data.data);
    })
    btn.blur();
}

function getReply(btn, id) {
    if ($("#comment-" + id).text() == "") {
        $("#comment-" + id).load("/comment/secondary", {"id": id});
    } else {
        $("#comment-" + id).text("")
    }
    btn.blur();
}

function changeReply(id, userName, parentId) {
    let pre = "回复 " + userName + ":";
    $("#reply-pre-" + id).val(pre);
    $("#input-" + id).attr("placeholder", pre);
    $("#input-" + id).focus();
}

function replyCancal(btn, id) {
    $("#reply-pre-" + id).val("");
    $("#input-" + id).val("");
    $("#input-" + id).attr("placeholder", "评论一下...");
    btn.blur();
}

function reply(btn, id) {
    let content = $("#input-" + id).val();
    if (content.length == 0) {
        alert("回复内容不能为空！");
        btn.blur();
    } else {
        content = $("#reply-pre-" + id).val() + content;
        postComment(2, id, content, "#comment-" + id,"/comment/secondary");
        replyCancal(btn, id)
    }
}