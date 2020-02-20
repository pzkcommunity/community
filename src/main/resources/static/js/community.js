function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    //内容为空
    if(!commentContent){
        alert("不能回复空内容！");
        return;
    }
    $.ajax({
        type:"post",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
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