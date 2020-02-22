

/**
 * 提交对question回复
 */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent)

}

/**
 * 提交对comment的回复
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);

}

/**
 * 抽取出的函数
 */
function comment2target(target, type, content) {
    //内容为空
    if(!content || content.length == 0){
        alert("不能回复空内容！");
        return;
    }
    $.ajax({
        type:"post",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":target,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200) {
                window.location.reload();
                $("#comment_section").hide();
            } else{
                if (response.code == 2003) {
                    //提示登录
                    var isAccepted = window.confirm(response.message);
                    //确认登录
                    if (isAccepted) {
                        //打开地址
                        window.open("https://github.com/login/oauth/authorize?client_id=2f51337f46659460e903&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        localStorage.setItem("closable","true");
                    }
                } else{
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}
/**
 * 二级评论折叠 与展开
 */
function CollapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

/*    //展开二级评论,点击添加class in 再点击 移除 in
    var toggle = comments.toggle("in");*/

    //获取一下二级评论的展开评论
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.classList.remove("active");
        e.removeAttribute("data-collapse")
    }else{
        //获取父元素
        var subCommentContainer = $('#comment-'+id);
        //判断子元素 不为1时 直接展示 不用加载
        var length = subCommentContainer.children().length;
        if (length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else{
            //get请求->commentcontroller comment方法
            $.getJSON("/comment/" + id,function (data) {
                console.log(data);
                //遍历响应回来的 json数据中data 添加到父元素
                var level2data = data.data.reverse();
                $.each(level2data,function (index, commentUserDto) {
                    /*var c = $("<div>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        html:commentUserDto.content
                    });*/
                    var c='<div class="media">'+
                        '<div class="media-left">'+
                        '<a href="#">'+
                        '<img class="media-object img-rounded img-head" src="/images/head.jpg">'+
                        '</a>'+
                        '</div>'+
                        '<div class="media-body">'+
                        '<h4 class="media-heading">'+
                        level2data[index].user.name+
                        '</h4>'+
                        '<h5 class="name-middle-2">'+ level2data[index].content +'</h5>'+
                        '<div class="community-like">'+
                        '<span class="pull-right community-menu" th:text="${#dates.format(level2data[index].gmtCreate,\'yyyy-MM-dd\')}">' +moment(level2data[index].gmtCreate).format('YYYY-MM-DD')+ '</span>'+
                        '</div>'+
                        '</div>'+
                        '<hr class="col-lg-12 col-md-12 col-sm-12 col-xs-12">'+
                        '</div>';


                    //子元素添加到父元素最前面,最后一个变成第一个
                    subCommentContainer.prepend(c);
                });

                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");

            })

        }

    }

}