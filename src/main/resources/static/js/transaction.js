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
})
