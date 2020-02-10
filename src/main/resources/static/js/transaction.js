/*product.html*/
$(function () {
    $("#inputNum").keypress(function (input) {
        var keyCode = input.keyCode ? input.keyCode : input.charCode;
        if(keyCode !=0 && (keyCode <48 || keyCode>57) && keyCode !=8 && keyCode != 37 &&keyCode!=39){
            /*保证除了 正常输入数字和左右箭头（3739），退格（8），回车（0） ，其他按键无效*/
            return false;
        }else{
            return true;
        }
    }).keyup(function (e) {
        var keyCode = input.keyCode ? input.keyCode : input.charCode;
        console.log(keyCode);
        if(keyCode != 8){
            var numVal = parseInt($("#inputNum").val()) || 0;
            numVal = numVal<1?1:numVal;
            $("#inputNum").val(numVal);
        }
    }).blur(function () {
        var numVal = parseInt($("#inputNum").val()) || 0;
        numVal = numVal<1?1:numVal;
        $("#inputNum").val(numVal);
    });
    $("#add").click(function () {
        var numVal = parseInt($("#inputNum").val()) || 0;
        $("#inputNum").val(numVal+1);
    });
    $("#sub").click(function () {
        var numVal = parseInt($("#inputNum").val()) || 0;
        numVal = numVal<2?2:numVal;
        $("#inputNum").val(numVal-1);
    });
});

$("#prods-rela li").hover(function () {
    $(this).addClass("action");
}, function () {
    $(this).removeClass("action");
});

function commitComment(targetId, content, type) {
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (ret) {
            console.log(ret);

            if(ret.code == 2120){
                //刷新页面
            }else{
                if(ret.code == 5000){
                    var login = confirm(ret.message);
                    if(login){
                        //确定登录
                        window.open("http://localhost:8222/login");
                        //来记录关闭该open打开的窗口
                        window.localStorage.setItem("toclose",true);
                    }
                }else{
                    alert(ret.code+ret.message);
                }
            }
        },
        dataType:"json"
    });
}

function post(){
    //点击评论
    var prodId = $("#production_id").val();
    var commentContent = $("#comment_content").val();
    commitComment(prodId, commentContent, 1);
}
function postsecond(e){
    var commentId = e.getAttribute("data-id");
    var content = $("#comment-second-"+commentId).val();
    commitComment(commentId,content,2);
}
function secondComment(e) {
    var id = e.getAttribute("data-id");
    var commentfuc = $("#comment-"+id);
    if(commentfuc.hasClass("in")){
        commentfuc.removeClass("in");

    }else{
        //获取该评论的回复+展开二级评论区
        /*$.getJSON('/comment/'+id,function(data){
            console.log(data);
        });*/
        $.ajax({
           type:"GET",
           url:"/comment/"+id,
           contentType: 'application/json',
           success:function (ret) {
               console.log(ret);
           },
           dataType: "json"
        });
        commentfuc.addClass("in");
    }

}