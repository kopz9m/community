//回复主贴
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(content);
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                //用户未登陆
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        $('#myModal').modal({});
                        // window.open("https://github.com/login/oauth/authorize?client_id=7f316909bf70d1eaa2b2&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        // window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if (previous) {
        var index = 0;
        var appear = false; //记录value是否已经作为一个独立的标签出现过
        while (true) {
            index = previous.indexOf(value, index); //value字符串在previous中出现的位置
            if (index == -1) break;
            //判断previous中出现的value是否是另一个标签的一部分
            //即value的前一个和后一个字符都是逗号","或者没有字符时，才说明value是一个独立的标签
            if ((index == 0 || previous.charAt(index - 1) == ",")
                && (index + value.length == previous.length || previous.charAt(index + value.length) == ",")
            ) {
                appear = true;
                break;
            }
            index++; //用于搜索下一个出现位置
        }
        if (!appear) {
            //若value没有作为一个独立的标签出现过
            $("#tag").val(previous + ',' + value);
        }
    } else {
        $("#tag").val(value);
    }
}

// 邮箱密码登录
function checkPass() {
    var email = $("#inputEmail").val();
    var password = $("#inputPassword").val();
    console.log(email);

    //login
    $.ajax({
        type: "POST",
        url: "/login",
        contentType: 'application/json',
        data: JSON.stringify({
            "email": email,
            "password": password,
        }),
        success: function (response) {
            if (response.code == 200) {
                var isAccepted = confirm(response.message + "即将回到主页。");
                if (isAccepted) {
                    window.location.href = "/";
                }
            } else {
                //密码错误
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

function sendCode() {
    var email = $("#inputEmail").val();
    debugger;
    if (!email) {
        alert("邮箱地址不得为空");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/sendCode",
        contentType: 'application/json',
        data: JSON.stringify({"email": email}),
        success: function (response) {
            if (response.code == 200) {
                alert(response.message + "请查看您的邮箱。")
            } else {
                alert(response.message)
            }
        },
        dataType: "json",
    });
}

function registerCheck() {
    var email = $("#inputEmail").val().trim();
    var verificationCode = $("#verificationCode").val().trim();
    var name = $("#username").val().trim();
    var password = $("#inputPassword").val();
    if (!email) {
        alert("邮箱地址不得为空");
        return;
    }
    if (!verificationCode || verificationCode.length != 6) {
        alert("请输入六位验证码");
        return;
    }
    if (!name) {
        alert("用户名不得为空");
        return;
    }
    if (!password || password.length < 4) {
        alert("请输入四位以上密码");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/register",
        contentType: 'application/json',
        data: JSON.stringify({
            "email": email,
            "verificationCode": verificationCode,
            "name": name,
            "password": password
        }),
        success: function (response) {
            if (response.code == 200) {
                var isAccepted = confirm(response.message + "将自动登录并返回主页。");
                if (isAccepted) {
                    window.location.href = "/";
                }
            } else {
                //注册失败
                alert(response.message);
            }
        },
        dataType: "json",
    });
}