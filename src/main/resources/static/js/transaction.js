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

$(function () {
    $("#prods-rela li").hover(function () {
        $(this).addClass("action");
    }, function () {
        $(this).removeClass("action");
    });
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
                window.location.reload();
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
                $.each(ret.data,function (index,element) {
                    /*<div class='col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-area'
                    th:each='comment:${commentdtos}'>
                        <div class='media'><div class='media-left'><img class='media-object img-rounded'th:src='[(${element.user.avatarUrl == null})]?'/images/default-user.jpg':[(${element.user.avatarUrl})]'></div>
                        <div class='media-body comment-body'><h4 class='media-heading comment-head'><span th:text='[(${element.user.name})]'></span> <span th:text='[(${#dates.format(element.gmtCreate,'yyyy-MM-dd')})]'></span></h4>
                        <div th:if='[(${element.content})] == null'><br></div><div th:text='[(${element.content})]'></div></div></div></div>*/
                    var htmlstr="<div class='col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-area'>"+
                        "<div class='media'><div class='media-left'>";
                    if(element.user.avatarUrl==null){
                        htmlstr+="<img class='media-object img-rounded' src='/images/default-user.jpg'></div>"
                    }else{
                        htmlstr+="<img class='media-object img-rounded' src="+element.user.avatarUrl+"></div>";
                    }
                    htmlstr+="<div class='media-body comment-body'><h4 class='media-heading comment-head'><span>"+element.user.name+"</span>"
                        +"<span>"+ moment(element.gmtCreate).format('YYYY-MM-DD') +"</span></h4>";
                    if(element.content==null){
                        htmlstr+="<div><br></div>"
                    }else{
                        htmlstr+="<div>"+element.content+"</div>";
                    }
                    htmlstr+="</div></div></div>";
                    console.log(htmlstr);
                    commentfuc.prepend(htmlstr);
                });
           },
           dataType: "json"
        });
        commentfuc.addClass("in");
    }

}
/*production.html*/

/*publish.html*/
var errorhtml=" <div  class='cover'>" +
    " <img  class='upload-img-cover upload-img' src='/images/download.png'/><div  class='cover-text'>最多三张图片</div></div>"
$(function () {


$("#upload").change(function () {
    var filePath = $(this)[0].files;// 获取input上传的文件
    var filesLength = $(this)[0].files.length;
    filesLength=filesLength>3?3:filesLength;
    var MaxSize = 2000;   /*最多一次上传2M （2*1024）*/
    if (filesLength == 0) {
        alert("未选择封面");
    } else {
        $("#container div").remove();
        var totalSize=0;
        for(var i=0;i<filesLength;i++){
            var thisSize = filePath[i].size; /*得到_B*/
            if(thisSize/1024>MaxSize){
                alert("超过单次最大传输2M");
                $("#container").append(errorhtml);
                return;
            }else{
                totalSize+=thisSize;
                if(totalSize/1024>MaxSize){
                    alert("总传输超过单次最大传输2M");
                    $("#container").append(errorhtml);
                    return;
                }
            }
            var url = window.URL.createObjectURL(filePath[i]); //将上传的文件转化为url
            var htmlStr="<div class='cover'>";
            htmlStr+= "<div class='img-trash' style='overflow: hidden; height: 0px;'><span class='icon-trash-o'></span></div>"
            htmlStr+="<img class='upload-img-cover upload-img' src=" +url+ "></div>";
            $("#container").append(htmlStr);
        }
        console.log(url);
    }
});

})